package com.easylife.weather.common.bean;


public class BaiduLocationConfig {
	public static final String SERVICE_NAME = "com.baidu.location.service_v2.9";
	// 返回国测局经纬度坐标系 coor=gcj02
	// 返回百度墨卡托坐标系 coor=bd09
	// 返回百度经纬度坐标系 coor=bd09ll
	public static final String COOR_TYPE = "gcj02";
	public static final int TIME_OUT = 15000;
	public static final int SCAN_SPAN = 0;// 设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
											// 毫秒为单位
	public static final boolean DISABLE_CACHE = true;// 禁止缓存

}