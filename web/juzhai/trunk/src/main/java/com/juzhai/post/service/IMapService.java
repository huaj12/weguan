package com.juzhai.post.service;

import com.juzhai.post.bean.Point;

public interface IMapService {
	/**
	 * 通过地址获取经纬度
	 * 
	 * @param place
	 */
	Point geocode(long cityId, String place);
}
