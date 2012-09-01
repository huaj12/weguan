package com.juzhai.post.service.impl;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.juzhai.common.InitData;
import com.juzhai.core.util.HttpUtil;
import com.juzhai.passport.model.City;
import com.juzhai.post.bean.Point;
import com.juzhai.post.service.IMapService;

@Service
public class MapService implements IMapService {
	private final Log log = LogFactory.getLog(getClass());
	private final String KEY = "5f548fe71d4f8b932a2154dc4fe266d1";

	@Override
	public Point geocode(long cityId, String place) {
		City city = InitData.CITY_MAP.get(cityId);
		if (city == null) {
			return null;
		}
		if (StringUtils.isEmpty(place)) {
			return null;
		}
		String url = "http://api.map.baidu.com/place/search?&query=" + place
				+ "&region=" + encode(city.getName()) + "&output=json&key="
				+ KEY;
		Point poitn = null;
		try {
			poitn = conversion(HttpUtil.getUrl(url));
		} catch (Exception e) {
			log.error("google map geocode is error", e);
		}
		return poitn;
	}

	private Point conversion(String content) {
		JSONObject json = JSONObject.fromObject(content);
		Point point = null;
		try {
			String status = json.getString("status");
			if (status != null && status.equalsIgnoreCase("ok")) {
				JSONArray resultsArray = json.getJSONArray("results");
				if (resultsArray != null && resultsArray.size() > 0) {
					JSONObject results = resultsArray.getJSONObject(0);
					JSONObject location = results.getJSONObject("location");
					point = new Point();
					point.setLat(location.getDouble("lat"));
					point.setLng(location.getDouble("lng"));
				}
			}
		} catch (JSONException e) {
			log.error("google map  conversion point is error", e);
		}
		return point;
	}

	private String encode(String value) {
		if (value == null)
			return null;
		try {
			return java.net.URLEncoder.encode(value, "UTF-8");
		} catch (IOException ex) {
			return value;
		}
	}
}
