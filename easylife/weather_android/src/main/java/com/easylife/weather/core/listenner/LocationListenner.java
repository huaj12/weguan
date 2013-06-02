package com.easylife.weather.core.listenner;

import android.content.Context;

import com.easylife.weather.core.ApplicationContext;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

/**
 * 监听函数，又新位置的时候
 */
public class LocationListenner implements BDLocationListener {
	private LocationClient mLocationClient;
	private Context context;

	public LocationListenner(Context context, LocationClient mLocationClient) {
		this.mLocationClient = mLocationClient;
		this.context = context;
	}

	@Override
	public void onReceiveLocation(final BDLocation location) {
		if (location == null) {
			return;
		}
		// 没有结果会返回double min_value
		if (Double.MIN_VALUE != location.getLatitude()
				|| Double.MIN_VALUE != location.getLongitude()) {
			ApplicationContext applicationContext = (ApplicationContext) context;
			applicationContext.setLatitude(location.getLatitude());
			applicationContext.setLongitude(location.getLongitude());
			// 获取到坐标停止服务
			mLocationClient.stop();
		}

	}

	public void onReceivePoi(BDLocation poiLocation) {
		// 接受poi信息
	}
}
