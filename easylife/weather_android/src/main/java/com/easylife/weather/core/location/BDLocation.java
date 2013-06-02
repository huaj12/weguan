package com.easylife.weather.core.location;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.location.LocationManager;
import android.widget.Toast;

import com.easylife.weather.R;
import com.easylife.weather.common.bean.BaiduLocationConfig;
import com.easylife.weather.core.ApplicationContext;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class BDLocation {
	private LocationClient mLocClient;
	private int i;
	private Timer timer;
	private Context context;

	public BDLocation(Context context) {
		this.context = context;
	}

	public void start(final BDLocationCallback callback) {
		final ApplicationContext config = (ApplicationContext) context
				.getApplicationContext();
		mLocClient = config.mLocationClient;
		setLocationOption(mLocClient, context);
		mLocClient.start();
		mLocClient.requestLocation();
		Toast.makeText(context, R.string.locate_loading, Toast.LENGTH_SHORT)
				.show();
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Double lat = config.getLatitude();
				Double lng = config.getLongitude();
				if (lng != null && lat != null) {
					if (callback != null) {
						callback.successCallback(lat, lng);
					}
					timer.cancel();
				}
				i++;
				if (i == 10) {
					if (callback != null) {
						callback.errorCallback();
					}
					timer.cancel();
				}
			}
		}, 0, 1000);
	}

	public void stop() {
		if (mLocClient != null) {
			mLocClient.stop();
		}
		if (timer != null) {
			timer.cancel();
		}
	}

	public interface BDLocationCallback {
		void successCallback(Double lat, Double lng);

		void errorCallback();
	}

	// 设置相关参数
	private void setLocationOption(LocationClient mLocClient, Context context) {
		LocationManager lManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		boolean hasGPS = lManager
				.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
		LocationClientOption option = new LocationClientOption();
		option.setCoorType(BaiduLocationConfig.COOR_TYPE); // 设置坐标类型
		option.setServiceName(BaiduLocationConfig.SERVICE_NAME);
		option.setScanSpan(BaiduLocationConfig.SCAN_SPAN);// 设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
															// 毫秒为单位
		option.setTimeOut(BaiduLocationConfig.TIME_OUT);// 超时时间
		if (hasGPS) {
			option.setPriority(LocationClientOption.GpsFirst);
		} else {
			option.setPriority(LocationClientOption.NetWorkFirst); // 设置网络优先
		}

		option.disableCache(BaiduLocationConfig.DISABLE_CACHE);// 静用缓存
		mLocClient.setLocOption(option);
	}

}
