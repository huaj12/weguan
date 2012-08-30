package com.juzhai.map.service;

import com.juzhai.map.bean.Point;

public interface IMapService {
	/**
	 * 通过地址获取经纬度
	 * 
	 * @param place
	 */
	Point geocode(long cityId, String place);
}
