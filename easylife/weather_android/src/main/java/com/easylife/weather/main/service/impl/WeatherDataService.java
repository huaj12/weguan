package com.easylife.weather.main.service.impl;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Xml;

import com.easylife.weather.R;
import com.easylife.weather.core.Constants;
import com.easylife.weather.core.data.SharedPreferencesManager;
import com.easylife.weather.core.exception.WeatherException;
import com.easylife.weather.core.model.Result.StringResult;
import com.easylife.weather.core.model.Result.WeatherInfoResult;
import com.easylife.weather.core.utils.HttpUtils;
import com.easylife.weather.main.data.WeatherDataManager;
import com.easylife.weather.main.model.Air;
import com.easylife.weather.main.model.ForecastHour;
import com.easylife.weather.main.model.WeatherInfo;
import com.easylife.weather.main.service.IWeatherDataService;

public class WeatherDataService implements IWeatherDataService {
	private final static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy/MM/dd");
	private final static String autolocationUri = "data/autoLocation";
	private final static String getUrlUri = "data/getHttpUrl";

	@Override
	public WeatherInfoResult getWeatherInfo(String url, Context context) {
		WeatherInfoResult result = new WeatherInfoResult();
		Calendar cal = Calendar.getInstance();
		String today = sdf.format(cal.getTime());
		Map<String, WeatherInfo> weathers = null;
		try {
			Log.d("weather", url);
			String content = HttpUtils.getContent(url);
			if (!StringUtils.hasText(content)) {
				return null;
			}
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					weathers = new HashMap<String, WeatherInfo>();
					for (int i = 0; i < 5; i++) {
						if (i == 0) {
							cal.add(Calendar.DAY_OF_WEEK, 0);
						} else {
							cal.add(Calendar.DAY_OF_WEEK, 1);
						}
						WeatherInfo wInfo = new WeatherInfo();
						wInfo.setDate(sdf.format(cal.getTime()));
						weathers.put(wInfo.getDate(), wInfo);
					}
					break;
				case XmlPullParser.START_TAG:
					WeatherInfo todayinfo = null;
					if ("ct".equals(parser.getName())) {
						todayinfo = weathers.get(today);
						todayinfo.setCityName(parser
								.getAttributeValue("", "nm"));
						weathers.put(today, todayinfo);
					}
					if ("hour".equals(parser.getName())) {
						todayinfo = weathers.get(today);
						List<ForecastHour> forecastHourList = todayinfo
								.getForecastHours();
						if (CollectionUtils.isEmpty(forecastHourList)) {
							forecastHourList = new ArrayList<ForecastHour>();
						}
						ForecastHour forecastHour = new ForecastHour();
						forecastHour.setTimeInterval(parser.getAttributeValue(
								"", "timeinterval"));
						forecastHour.setSky(parser.getAttributeValue("", "wd"));
						forecastHour.setMinTmp(parser.getAttributeValue("",
								"ltmp"));
						forecastHour.setMaxTmp(parser.getAttributeValue("",
								"htmp"));
						forecastHour.setWindPower(parser.getAttributeValue("",
								"wl"));
						forecastHour.setWindDirection(parser.getAttributeValue(
								"", "wdir"));
						forecastHour.setIcon(parser
								.getAttributeValue("", "wid"));
						forecastHour.setId(parser.getAttributeValue("", "id"));
						forecastHourList.add(forecastHour);
						todayinfo.setForecastHours(forecastHourList);
						weathers.put(today, todayinfo);
					}
					if ("dt".equals(parser.getName())) {
						String date = parser.getAttributeValue("", "date");
						WeatherInfo info = weathers.get(date);
						if (info != null) {
							info.setDescription(parser.getAttributeValue("",
									"kn"));
							info.setWindPower(parser
									.getAttributeValue("", "wl"));
							info.setWindDirection(parser.getAttributeValue("",
									"wdir"));

							info.setLastFestival(parser.getAttributeValue("",
									"ftv"));
							info.setMinTmp(parser.getAttributeValue("", "ltmp"));
							info.setMaxTmp(parser.getAttributeValue("", "htmp"));
							info.setSunrise(parser.getAttributeValue("", "sr"));
							info.setSunset(parser.getAttributeValue("", "ss"));
							weathers.put(date, info);
						}
					}
					if ("cc".equals(parser.getName())) {
						todayinfo = weathers.get(today);
						todayinfo.setLunarCalendar(parser.getAttributeValue("",
								"ldt"));
						todayinfo.setUpdateTime(parser.getAttributeValue("",
								"upt"));
						todayinfo
								.setNowTmp(parser.getAttributeValue("", "tmp"));
						todayinfo.setHum(parser.getAttributeValue("", "hum"));
						todayinfo.setSky(parser.getAttributeValue("", "wd"));
						todayinfo.setIcon(parser.getAttributeValue("", "wid"));
						weathers.put(today, todayinfo);
					}
					if ("day".equals(parser.getName())) {
						String date = parser.getAttributeValue("", "date");
						WeatherInfo info = weathers.get(date);
						if (info == null) {
							info = new WeatherInfo();
						}
						info.setDaytimeWindPower(parser.getAttributeValue("",
								"hwl"));
						info.setNightWindPower(parser.getAttributeValue("",
								"lwl"));
						info.setDaytimeWindDirection(parser.getAttributeValue(
								"", "hwdir"));
						info.setNightWindDirection(parser.getAttributeValue("",
								"lwdir"));
						info.setDaytimeSky(parser.getAttributeValue("", "hwd"));
						info.setNightSky(parser.getAttributeValue("", "lwd"));
						info.setDaytimeIcon(parser
								.getAttributeValue("", "hwid"));
						weathers.put(date, info);

					}
					if ("air".equals(parser.getName())) {
						todayinfo = weathers.get(today);
						Air air = new Air();
						air.setAqigrade(parser
								.getAttributeValue("", "aqigrade"));
						air.setCityaveragename(parser.getAttributeValue("",
								"cityaveragename"));
						air.setCo(parser.getAttributeValue("", "co"));
						air.setUpdateTime(parser.getAttributeValue("", "ptime"));
						air.setDesc(parser.getAttributeValue("", "desc"));
						air.setHiaqi(parser.getAttributeValue("", "hiaqi"));
						air.setHname(parser.getAttributeValue("", "hname"));
						air.setLevel(parser.getAttributeValue("", "lv"));
						air.setLiaqi(parser.getAttributeValue("", "liaqi"));
						air.setLname(parser.getAttributeValue("", "lname"));
						air.setNo2(parser.getAttributeValue("", "no2"));
						air.setO3(parser.getAttributeValue("", "o3"));
						air.setPm10(parser.getAttributeValue("", "pmtenaqi"));
						air.setPm25(parser.getAttributeValue("", "pmtwoaqi"));
						air.setSo2(parser.getAttributeValue("", "so2"));
						air.setTitle(parser.getAttributeValue("", "title"));
						todayinfo.setAir(air);
						weathers.put(today, todayinfo);
					}
					if ("idx".equals(parser.getName())) {
						if ("12".equals(parser.getAttributeValue("", "type"))) {
							todayinfo = weathers.get(today);
							int lv = 0;
							try {
								lv = Integer.parseInt(parser.getAttributeValue(
										"", "lv"));
							} catch (Exception e) {

							}
							todayinfo.setUvidxLv(lv);
							todayinfo.setUvidx(parser.getAttributeValue("",
									"desc"));
							weathers.put(today, todayinfo);
						}
					}
					break;
				case XmlPullParser.END_TAG:// 判断当前事件是否是标签元素结束事件
					break;
				case XmlPullParser.END_DOCUMENT:
					break;
				}
				event = parser.next();
			}
		} catch (Exception e) {

		}
		result.setResult(weathers);
		WeatherDataManager.delWeatherDate(context);
		WeatherDataManager.saveWeatherInfo(context, result);
		SharedPreferencesManager manager = new SharedPreferencesManager(context);
		manager.commit(SharedPreferencesManager.LAST_UPDATE_TIME,
				System.currentTimeMillis());
		//通知桌面插件更新ui
		Intent intent = new Intent();
		intent.setAction(Constants.WIDGET_UPDATE_INTENT);
		context.sendBroadcast(intent);
		return result;
	}

	@Override
	public String getUrl(double lat, double lng, Context context)
			throws WeatherException {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("lat", lat);
		values.put("lng", lng);
		ResponseEntity<StringResult> entity = null;
		try {
			entity = HttpUtils.get(context, autolocationUri, values,
					StringResult.class);
			if (entity.getBody().getSuccess()) {
				return entity.getBody().getResult();
			}
		} catch (RestClientException e) {
			throw new WeatherException(context, R.string.no_network);
		}

		return null;
	}

	@Override
	public String getUrl(String citiName, Context context)
			throws WeatherException {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("cityName", citiName);
		try {
			ResponseEntity<StringResult> entity = HttpUtils.get(context,
					getUrlUri, values, StringResult.class);
			if (entity.getBody().getSuccess()) {
				return entity.getBody().getResult();
			}
		} catch (RestClientException e) {
			throw new WeatherException(context, R.string.no_network);
		}

		return null;
	}

	@Override
	public void updateWeatherDate(String cityName, Context context) {
		if (StringUtils.hasText(cityName)) {
			String url = null;
			try {
				url = getUrl(cityName, context);
			} catch (WeatherException e) {
			}
			if (StringUtils.hasText(url)) {
				getWeatherInfo(url, context);
			}
		}

	}
}
