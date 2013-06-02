package com.easylife.weather.main.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.util.StringUtils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.easylife.weather.R;
import com.easylife.weather.common.service.IShareService;
import com.easylife.weather.common.service.impl.ShareService;
import com.easylife.weather.core.activity.ActivityCode;
import com.easylife.weather.core.exception.WeatherException;
import com.easylife.weather.core.task.ProgressTask;
import com.easylife.weather.core.task.TaskCallback;
import com.easylife.weather.core.utils.DateUtil;
import com.easylife.weather.core.utils.WeatherUtils;
import com.easylife.weather.core.widget.menu.SlidingMenu;
import com.easylife.weather.core.widget.menu.app.SlidingFragmentActivity;
import com.easylife.weather.main.adapter.MainListAdapter;
import com.easylife.weather.main.data.WeatherDataManager;
import com.easylife.weather.main.fragment.LeftFragment;
import com.easylife.weather.main.fragment.RightFragment;
import com.easylife.weather.main.model.WeatherInfo;
import com.easylife.weather.main.service.IWeatherDataService;
import com.easylife.weather.main.service.impl.WeatherDataService;
import com.easylife.weather.passport.data.UserConfigManager;
import com.easylife.weather.passport.model.UserConfig;
import com.easylife.weather.passport.service.IPassportService;
import com.easylife.weather.passport.service.impl.PassPortService;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

public class MainActivity extends SlidingFragmentActivity {
	private IWeatherDataService weatherDataService = new WeatherDataService();
	private IPassportService passportService = new PassPortService();
	private SlidingMenu slidingMenu;
	private LeftFragment leftFragment;
	private RightFragment rightFragment;
	private String cityName;
	private Button showLeft;
	private Button showRight;
	private Button refresh_view;
	private TextView weekView;
	private TextView cityView;
	private WeatherInfo weatherInfo;
	private ImageSwitcher mSwitcher;
	private int index = 0;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mSwitcher.setImageResource(msg.what);
		}
	};

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.page_center);
		LinearLayout layout = (LinearLayout) findViewById(R.id.page_color_view);
		layout.setBackgroundColor(WeatherDataManager
				.getBackgroundColor(MainActivity.this));
		slidingMenu = getSlidingMenu();
		slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
		slidingMenu.setShadowWidth(getWindowManager().getDefaultDisplay()
				.getWidth() / 40);
		slidingMenu.setShadowDrawable(R.drawable.menu_shadow);
		slidingMenu.setSecondaryShadowDrawable(R.drawable.menu_shadow_r);
		slidingMenu.setBehindOffset(getWindowManager().getDefaultDisplay()
				.getWidth() / 7);
		slidingMenu.setFadeEnabled(true);
		slidingMenu.setFadeDegree(0.4f);
		slidingMenu.setBehindScrollScale(0);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		setBehindContentView(R.layout.fragment_left);
		slidingMenu.setSecondaryMenu(R.layout.fragment_right);
		weatherInfo = WeatherDataManager.getWeatherInfos(DateUtil.getToday(),
				MainActivity.this);
		cityName = UserConfigManager.getCityName(MainActivity.this);
		if (!StringUtils.hasText(cityName)) {
			// 没有城市则去选择城市页面
			startActivity(new Intent(MainActivity.this, CityActivity.class));
			finish();
		}
		if (getIntent().getBooleanExtra("update", false)) {
			UmengUpdateAgent.setUpdateAutoPopup(true);
			UmengUpdateAgent.update(this);
		}
		init();

	}

	public void init() {
		new ProgressTask(MainActivity.this, new TaskCallback() {
			@Override
			public void successCallback() {
				if (weatherInfo != null) {
					showCenter();
					FragmentTransaction t = MainActivity.this
							.getSupportFragmentManager().beginTransaction();
					leftFragment = new LeftFragment();
					leftFragment.setContext(MainActivity.this);
					t.replace(R.id.left_frame, leftFragment);

					rightFragment = new RightFragment();
					rightFragment.setContext(MainActivity.this);
					slidingMenu.setSecondaryMenu(R.layout.fragment_right);
					t.replace(R.id.right_frame, rightFragment);
					t.commit();

				} else {
					AlertDialog.Builder builder = new Builder(MainActivity.this);
					builder.setPositiveButton(R.string.retry,
							new OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									init();
								}
							}).setNegativeButton(
							getResources().getString(R.string.cancel), null);
					builder.setIcon(android.R.drawable.ic_dialog_info);
					builder.setMessage(R.string.no_network);
					builder.show();
				}
			}

			@Override
			public String doInBackground() {
				if (weatherInfo == null) {
					// 从服务端获取url
					String url = null;
					try {
						url = weatherDataService.getUrl(cityName,
								MainActivity.this);
					} catch (WeatherException e) {
					}
					weatherDataService.getWeatherInfo(url, MainActivity.this);
					weatherInfo = WeatherDataManager.getWeatherInfos(
							DateUtil.getToday(), MainActivity.this);
				}
				return null;
			}
		}, false).execute();
	}

	protected void showCenter() {
		showLeft = (Button) findViewById(R.id.showLeft);
		showRight = (Button) findViewById(R.id.showRight);
		refresh_view = (Button) findViewById(R.id.refresh_view);
		ListView listView = (ListView) findViewById(R.id.today_weather_info_list_view);
		showRight.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				slidingMenu.showSecondaryMenu();
			}
		});
		showLeft.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				slidingMenu.showMenu();
			}
		});
		cityView = (TextView) findViewById(R.id.city_view);
		cityView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// slidingMenu.showSecondaryMenu();
			}
		});
		refresh_view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				weatherInfo = null;
				init();
			}

		});
		weekView = (TextView) findViewById(R.id.week_view);
		weekView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				slidingMenu.showMenu();
			}
		});
		Calendar cal = Calendar.getInstance();
		weekView.setText(getResources().getString(R.string.week,
				DateUtil.WEEK[cal.get(Calendar.DAY_OF_WEEK) - 1]));
		cityView.setText(UserConfigManager.getCityName(MainActivity.this));
		Animation inAni = AnimationUtils.loadAnimation(MainActivity.this,
				R.anim.image_view_in);
		Animation outAni = AnimationUtils.loadAnimation(MainActivity.this,
				R.anim.image_view_out);
		RelativeLayout hearView = (RelativeLayout) ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.fragment__imageswitcher, null);
		ImageView shareView = (ImageView) hearView
				.findViewById(R.id.share_image_view);
		mSwitcher = (ImageSwitcher) hearView.findViewById(R.id.switcher);
		mSwitcher.setInAnimation(inAni);
		mSwitcher.setOutAnimation(outAni);
		mSwitcher.setFactory(new ViewFactory() {

			@Override
			public View makeView() {
				ImageView i = new ImageView(MainActivity.this);
				i.setScaleType(ImageView.ScaleType.FIT_CENTER);
				i.setLayoutParams(new ImageSwitcher.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				return i;
			}
		});
		final Integer[] res = getTravelTips();
		if (res.length == 1) {
			mSwitcher.setImageResource(res[0]);
		} else {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					if (index == res.length) {
						index = 0;
					}
					handler.sendEmptyMessage(res[index]);
					index++;
				}
			}, 0, 4000);
		}
		if (0 == listView.getHeaderViewsCount()) {
			listView.addHeaderView(hearView);
		}
		listView.setAdapter(new MainListAdapter(weatherInfo, MainActivity.this));
		if (res[0] == R.drawable.wycx_normal) {
			shareView.setVisibility(View.GONE);
		} else {
			shareView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					IShareService shareService = new ShareService();
					shareService.openSharePop(
							WeatherUtils.getShareText(res, MainActivity.this),
							MainActivity.this);
				}
			});
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ActivityCode.RequestCode.CLEAR_REQUEST_CODE
				&& resultCode == ActivityCode.ResultCode.CLEAR_RESULT_CODE) {
			setResult(ActivityCode.ResultCode.CLEAR_RESULT_CODE);
			finish();
		} else if (requestCode == ActivityCode.RequestCode.HOUR_REQUEST_CODE
				&& resultCode == ActivityCode.ResultCode.HOUR_RESULT_CODE) {
			String hour = data.getStringExtra("hour");
			if (StringUtils.hasText(hour)) {
				final UserConfig user = UserConfigManager
						.getUserConfig(MainActivity.this);
				user.setHourStr(hour);
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {

						try {
							passportService.updateUserConfig(user,
									MainActivity.this);
						} catch (WeatherException e) {
						}
						return null;
					}

				}.execute();
				// 先保存本地的为了更新数据
				rightFragment.settingAdapter.notifyDataSetChanged();
			}
		}
	}

	private Integer[] getTravelTips() {
		List<Integer> resources = new ArrayList<Integer>();
		int rain = WeatherUtils.getRainResource(weatherInfo, MainActivity.this);
		if (rain != 0) {
			resources.add(rain);
		}
		int wind = WeatherUtils.getWindResource(weatherInfo);
		if (wind != 0) {
			resources.add(wind);
		}
		int pm25 = WeatherUtils.getPM25Resource(weatherInfo);
		if (pm25 != 0) {
			resources.add(pm25);
		}
		int cooling = WeatherUtils.getCoolingResource(WeatherDataManager
				.getWeatherInfos(DateUtil.getYesterday(), MainActivity.this),
				weatherInfo);
		if (cooling != 0) {
			resources.add(cooling);
		}
		if (resources.size() == 0) {
			resources.add(R.drawable.wycx_normal);
		}
		return resources.toArray(new Integer[0]);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
