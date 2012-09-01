package com.juzhai.post.bean;

public class Point {
	//TODO (review) 应该用Double来定义，lat＝0是有意义的，所以只有当为null的时候才是无效数据，页面判断是否显示地图也应该要判断这个field
	private double lat;
	//TODO (review) 应该用Double来定义，lng＝0是有意义的，所以只有当为null的时候才是无效数据，页面判断是否显示地图也应该要判断这个field
	private double lng;

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

}
