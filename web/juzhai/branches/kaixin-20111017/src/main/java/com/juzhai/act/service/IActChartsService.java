package com.juzhai.act.service;

import java.util.List;
import java.util.Map;

import com.juzhai.act.model.Act;

public interface IActChartsService {

	/**
	 * 兴趣排行榜
	 * 
	 * @return 0.女 1.男女 2.男
	 */
	Map<Integer, List<Act>> showActCharts();
}
