package com.android.weatherspider.core.task;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import android.content.Context;

import com.android.weatherspider.core.model.Result.StringResult;
import com.android.weatherspider.core.utils.HttpUtils;
import com.android.weatherspider.model.WeatherInfo;

public class SpiderWeatherTask implements Callable<Boolean> {
	// 默认uid
	private long userId = 18918395;
	private long city;
	private Context context;
	private String domain = "http://cdn.moji001.com/";
	private CountDownLatch down;
	private String uri = "/data/receiverSpider";
	private int count;

	public SpiderWeatherTask(long city, Context context, int count,
			CountDownLatch down) {
		this.city = city;
		this.context = context;
		this.down = down;
		this.count = count;
	}

	@Override
	public Boolean call() throws Exception {
		try {
			WeatherInfo info = HttpUtils.parse(domain, city, userId, context);
			HashMap<String, Object> values = new HashMap<String, Object>();
			values.put("hWeather", info.getDaytimeSky());
			values.put("lWeather", info.getNightSky());
			values.put("hWindPower", info.getDaytimeWindPower());
			values.put("lWindPower", info.getNightWindPower());
			values.put("hTmp", info.getMaxTmp());
			values.put("lTmp", info.getMinTmp());
			values.put("city", city);
			values.put("count", count);
			HttpUtils.get(context, uri, values, StringResult.class);
		} finally {
			down.countDown();
		}
		return null;
	}
}
