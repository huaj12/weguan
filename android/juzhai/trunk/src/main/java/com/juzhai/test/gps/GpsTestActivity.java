/**
 * 
 */
package com.juzhai.test.gps;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.juzhai.android.R;

/**
 * @author kooks
 * 
 */
public class GpsTestActivity extends Activity {
	private static String TAG = "dubug";
	LocationManager lm;
	EditText show;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gpstest);
		show = (EditText) findViewById(R.id.show);
		// 获取系统的LocationManager对象
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 获取系统所有的LocationProvider的名称
		List<String> providerNames = lm.getAllProviders();
		for (String name : providerNames) {
			Log.i(TAG, name);
		}

		// 不同模块试用不同的Criteria惊醒筛选LocationProvider
		// gps 精确度高耗电
		// network 精度低低消耗速度快
		// Criteria criteria = new Criteria();
		// criteria.setAccuracy(Criteria.ACCURACY_FINE);// 设置为最大精度
		// criteria.setAltitudeRequired(false);// 不要求海拔信息
		// criteria.setBearingRequired(false);// 不要求方位信息
		// criteria.setCostAllowed(true);// 是否允许付费
		// criteria.setPowerRequirement(Criteria.POWER_LOW);// 对电量的要求
		// // 这里可能会返回null
		// String providerName = lm.getBestProvider(criteria, true);
		// Log.i(TAG, providerName);
		// 获取组建的相关信息
		LocationProvider provider = lm
				.getProvider(LocationManager.GPS_PROVIDER);
		Log.i(TAG, "当前provider:" + provider.getName());
		Location location = lm.getLastKnownLocation(provider.getName());
		updateView(location);
		// 设置每1秒获取一次GPS的定位信息
		// 小于8米位置不更新
		// 还有一个参数loop 如果为null 用主线程回调
		lm.requestLocationUpdates(provider.getName(), 1000, 0,
				new LocationListener() {
					@Override
					public void onLocationChanged(Location location) {
						// Provider 位置改变
						Log.i(TAG, "有执行1");
						Log.i(TAG, String.valueOf(location.getLatitude()));
						updateView(location);
					}

					@Override
					public void onProviderDisabled(String provider) {
						Log.i(TAG, "有执行2");
						updateView(null);
					}

					@Override
					public void onProviderEnabled(String provider) {
						// Provider 可用
						Log.i(TAG, "有执行3");
						Log.i(TAG, String.valueOf(lm.getLastKnownLocation(
								provider).getLatitude()));
						updateView(lm.getLastKnownLocation(provider));
						Log.i(TAG, "move");
					}

					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
						// 此方法在Provider的状态在可用、暂时不可用和无服务三个状态直接切换时被调用。
						Log.i(TAG, "有执行4");
					}
				});
	}

	// 更新EditText中显示的内容
	public void updateView(Location newLocation) {
		if (newLocation != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("实时的位置信息：\n");
			sb.append("经度：");
			sb.append(newLocation.getLongitude());
			sb.append("\n纬度：");
			sb.append(newLocation.getLatitude());
			sb.append("\n高度：");
			sb.append(newLocation.getAltitude());
			sb.append("\n速度：");
			sb.append(newLocation.getSpeed());
			sb.append("\n方向：");
			sb.append(newLocation.getBearing());
			show.setText(sb.toString());
		} else {
			// 如果传入的Location对象为空则清空EditText
			Log.i(TAG, "有执行");
			show.setText(System.currentTimeMillis() + "获取不到位置啊啊");
		}
	}
}
