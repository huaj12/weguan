package com.juzhai.post.bean;

public class Point {
	// TODO (done)
	// 应该用Double来定义，lat＝0是有意义的，所以只有当为null的时候才是无效数据，页面判断是否显示地图也应该要判断这个field
	private Double lat;
	// TODO (done)
	// 应该用Double来定义，lng＝0是有意义的，所以只有当为null的时候才是无效数据，页面判断是否显示地图也应该要判断这个field
	private Double lng;

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public boolean isHasValid() {
		if (lng == null || lat == null) {
			return false;
		}
		return true;
	}

}
