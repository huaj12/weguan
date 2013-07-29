package com.easylife.weather.main.activity;

import org.springframework.util.StringUtils;

import android.content.ComponentName;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.Toast;

import com.easy.DianJinPlatform;
import com.easylife.weather.R;
import com.easylife.weather.core.Constants;
import com.easylife.weather.core.activity.BaseActivity;
import com.easylife.weather.core.data.SharedPreferencesManager;
import com.easylife.weather.core.exception.WeatherException;
import com.easylife.weather.core.location.BDLocation;
import com.easylife.weather.core.location.BDLocation.BDLocationCallback;
import com.easylife.weather.core.utils.DateUtil;
import com.easylife.weather.core.utils.WeatherUtils;
import com.easylife.weather.main.data.WeatherDataManager;
import com.easylife.weather.main.model.WeatherInfo;
import com.easylife.weather.main.service.IWeatherDataService;
import com.easylife.weather.main.service.impl.WeatherDataService;
import com.easylife.weather.passport.data.UserConfigManager;
import com.easylife.weather.passport.model.UserConfig;
import com.easylife.weather.passport.service.IPassportService;
import com.easylife.weather.passport.service.impl.PassPortService;
import com.umeng.analytics.MobclickAgent;

public class LaunchActivity extends BaseActivity {
	private IPassportService passPortService = new PassPortService();
	private BDLocation location;
	private SharedPreferencesManager manager = null;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Toast.makeText(LaunchActivity.this, msg.what, Toast.LENGTH_SHORT)
					.show();

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DianJinPlatform.initialize(this, Constants.DIANJIN_ID,
				Constants.SECRET_KEY);
		setContentView(R.layout.page_launch);
		MobclickAgent.onError(this);
		jump();
	}

	private void jump() {
		manager = new SharedPreferencesManager(this);
		if (!manager.getBoolean(SharedPreferencesManager.CREATE_SHORTCUT)) {
			addShortcut();
		}
		UserConfig userConfig = UserConfigManager
				.getUserConfig(LaunchActivity.this);
		if (userConfig == null
				|| !StringUtils.hasText(userConfig.getCityName())) {
			// 开始自动定位如果自动定位失败则跳转到选择城市页面
			regLastDialogTime();
			location = new BDLocation(LaunchActivity.this);
			location.start(new BDLocationCallback() {

				@Override
				public void successCallback(Double lat, Double lng) {
					handler.sendEmptyMessage(R.string.locate_success);
					// 从服务端获取url
					IWeatherDataService weatherDataService = new WeatherDataService();
					String cityUrl = null;
					try {
						cityUrl = weatherDataService.getUrl(lat, lng,
								LaunchActivity.this);
					} catch (WeatherException e) {
						handler.sendEmptyMessage(R.string.no_network);
					}
					if (cityUrl != null) {
						// 获取天气数据和城市
						weatherDataService.getWeatherInfo(cityUrl,
								LaunchActivity.this);
						// 保存天气和城市
						// 更新用户所在城市
						WeatherInfo info = WeatherDataManager.getWeatherInfos(
								DateUtil.getToday(), LaunchActivity.this);
						if (info == null) {
							// 注册新用户
							try {
								passPortService.register(LaunchActivity.this);
							} catch (WeatherException e) {
							}
							// 加载数据失败。手动选择城市
							startActivity(new Intent(LaunchActivity.this,
									CityActivity.class));

						} else {
							try {
								passPortService.register(info.getCityName(),
										LaunchActivity.this);
							} catch (WeatherException e) {
							}
							startActivity(new Intent(LaunchActivity.this,
									MainActivity.class));

						}
						finish();
					} else {
						handler.sendEmptyMessage(R.string.no_network);
					}
				}

				@Override
				public void errorCallback() {
					// 定位失败用户手动选择城市
					handler.sendEmptyMessage(R.string.locate_error);
					// 注册新用户
					try {
						passPortService.register(LaunchActivity.this);
					} catch (WeatherException e) {
					}
					startActivity(new Intent(LaunchActivity.this,
							CityActivity.class));
					finish();

				}
			});

		} else {
			Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		WeatherUtils.setRepeating(this);
	}

	private void regLastDialogTime() {
		// 第一次进入
		SharedPreferencesManager manager = new SharedPreferencesManager(
				LaunchActivity.this);
		manager.commit(SharedPreferencesManager.SPREAD_APP_DAILOG_TIME,
				System.currentTimeMillis());
	}

	@Override
	protected void onDestroy() {
		if (location != null) {
			location.stop();
		}
		super.onDestroy();
	}

	/**
	 * 为程序创建桌面快捷方式
	 */
	private void addShortcut() {

		Intent shortcut = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");

		// 快捷方式的名称
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
				getString(R.string.app_name));
		shortcut.putExtra("duplicate", false); // 不允许重复创建
		// 指定当前的Activity为快捷方式启动的对象: 如 com.everest.video.VideoPlayer
		// 注意: ComponentName的第二个参数必须加上点号(.)，否则快捷方式无法启动相应程序
		ComponentName comp = new ComponentName(this.getPackageName(), "."
				+ this.getLocalClassName());
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(
				Intent.ACTION_MAIN).setComponent(comp));

		// 快捷方式的图标
		ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(
				this, R.drawable.logo);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
		sendBroadcast(shortcut);
		manager.commit(SharedPreferencesManager.CREATE_SHORTCUT, true);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
