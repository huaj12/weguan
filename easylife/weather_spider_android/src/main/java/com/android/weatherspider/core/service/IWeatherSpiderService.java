package com.android.weatherspider.core.service;

import java.util.Set;

import android.content.Context;

public interface IWeatherSpiderService {
	/**
	 * 抓取数据并上传到服务器
	 * 
	 * @param context
	 */
	void spider(Context context);

	/**
	 * 获取需要爬取的城市列表
	 * 
	 * @param context
	 * @return
	 */
	Set<Integer> getSpiderCitys(Context context);
}
