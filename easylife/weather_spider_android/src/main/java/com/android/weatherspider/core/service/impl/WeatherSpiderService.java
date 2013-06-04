package com.android.weatherspider.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.http.ResponseEntity;

import android.content.Context;

import com.android.weatherspider.R;
import com.android.weatherspider.core.data.SharedPreferencesManager;
import com.android.weatherspider.core.model.Result.SetResult;
import com.android.weatherspider.core.service.IWeatherSpiderService;
import com.android.weatherspider.core.task.SpiderWeatherTask;
import com.android.weatherspider.core.utils.HttpUtils;
import com.android.weatherspider.core.utils.WeatherSpiderUtils;

public class WeatherSpiderService implements IWeatherSpiderService {
	private ExecutorService executor = Executors.newFixedThreadPool(5);
	private String uri = "data/needSpiderCity";

	@Override
	public void spider(Context context) {
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
		SharedPreferencesManager dataManager = new SharedPreferencesManager(
				context);
		dataManager.commit("hasRunning", true);
		dataManager.commit(
				SharedPreferencesManager.HAS_WIFI_TEXT,
				context.getResources().getString(R.string.has_wifi_text,
						sdf.format(new Date()),
						WeatherSpiderUtils.hasWifi(context)));
		// if (WeatherSpiderUtils.hasWifi(context)) {
		// 先从服务端获取需要爬取的citys列表
		Set<Integer> citys = getSpiderCitys(context);
		if (citys != null) {
			CountDownLatch down = new CountDownLatch(citys.size());
			for (long city : citys) {
				executor.submit(new SpiderWeatherTask(city, context, citys
						.size(), down));
			}
			try {
				down.await();
			} catch (InterruptedException e) {
			}
		}
		dataManager.commit(
				SharedPreferencesManager.HAS_SPIDER_TEXT,
				context.getResources().getString(R.string.has_spider_text,
						sdf.format(new Date())));
		// }
		dataManager.commit("hasRunning", false);

	}

	@Override
	public Set<Integer> getSpiderCitys(Context context) {
		try {
			ResponseEntity<SetResult> entity = HttpUtils.get(context, uri,
					null, SetResult.class);
			if (entity.getBody().getSuccess()) {
				return entity.getBody().getResult();
			}
		} catch (Exception e) {
		}

		return null;
	}

}
