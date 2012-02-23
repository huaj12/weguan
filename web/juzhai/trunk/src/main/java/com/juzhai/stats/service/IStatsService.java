package com.juzhai.stats.service;

import java.util.Map;

public interface IStatsService {
	/**
	 * 获取所有统计数值
	 * 
	 * @return
	 */
	Map<String, Long> getStatsCunter(String beginDate, String endDate);
}
