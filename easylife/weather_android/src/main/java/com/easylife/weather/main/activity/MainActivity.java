package com.easylife.weather.main.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.util.StringUtils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
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

import com.easy.DianJinPlatform;
import com.easylife.weather.R;
import com.easylife.weather.common.service.IShareService;
import com.easylife.weather.common.service.impl.ShareService;
import com.easylife.weather.core.Constants;
import com.easylife.weather.core.data.SharedPreferencesManager;
import com.easylife.weather.core.exception.WeatherException;
import com.easylife.weather.core.task.ProgressTask;
import com.easylife.weather.core.task.TaskCallback;
import com.easylife.weather.core.utils.DateUtil;
import com.easylife.weather.core.utils.WeatherUtils;
import com.easylife.weather.core.widget.date.DateSlider;
import com.easylife.weather.core.widget.date.TimeSlider;
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

public class MainActivity extends SlidingFragmentActivity {
	public final int TIMESELECTOR_ID = 4;
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
	private ListView listView;
	private RelativeLayout hearView;
	private Timer timer;
	private int index = 1;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (mSwitcher != null) {
				mSwitcher.setImageResource(msg.what);
			}
		}
	};
	private DateSlider.OnDateSetListener mTimeSetListener = new DateSlider.OnDateSetListener() {

		@Override
		public void onDateSet(DateSlider view, Calendar selectedDate) {
			UserConfig user = UserConfigManager
					.getUserConfig(MainActivity.this);
			user.setTimeStr(String.format("%tR", selectedDate));
			user.setTime(selectedDate.getTimeInMillis());
			WeatherUtils.setRepeating(MainActivity.this);
			try {
				passportService.updateUserConfig(user, MainActivity.this);
			} catch (WeatherException e) {
			}
			rightFragment.settingAdapter.notifyDataSetChanged();
		}
	};

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.page_center);
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
		SharedPreferencesManager manager = new SharedPreferencesManager(this);
		long lastUpdateTime = manager
				.getLong(SharedPreferencesManager.LAST_UPDATE_TIME);
		if (System.currentTimeMillis() - lastUpdateTime < Constants.WEATHER_UPDATE_INTERVAL) {
			weatherInfo = WeatherDataManager.getWeatherInfos(
					DateUtil.getToday(), MainActivity.this);
		}
		cityName = UserConfigManager.getCityName(MainActivity.this);
		if (!StringUtils.hasText(cityName)) {
			// 没有城市则去选择城市页面
			startActivity(new Intent(MainActivity.this, CityActivity.class));
		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		weatherInfo = null;
		cityName = UserConfigManager.getCityName(MainActivity.this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		LinearLayout layout = (LinearLayout) findViewById(R.id.page_color_view);
		layout.setBackgroundColor(WeatherDataManager
				.getBackgroundColor(MainActivity.this));
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
		listView = (ListView) findViewById(R.id.today_weather_info_list_view);
		if (hearView != null) {
			listView.removeHeaderView(hearView);
		}
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
				slidingMenu.showSecondaryMenu();
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
		hearView = (RelativeLayout) ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.fragment__imageswitcher, null);
		ImageView shareView = (ImageView) hearView
				.findViewById(R.id.share_image_view);
		mSwitcher = (ImageSwitcher) hearView.findViewById(R.id.switcher);
		// 必须放到动画前面
		mSwitcher.setAnimateFirstView(false);
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
		if (timer != null) {
			timer.cancel();
		}
		mSwitcher.setImageResource(res[0]);
		if (res.length > 1) {
			timer = new Timer();
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
		// if (0 == listView.getHeaderViewsCount()) {
		listView.setAdapter(null);
		listView.addHeaderView(hearView);
		// }
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		DianJinPlatform.destroy();
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		if (id == TIMESELECTOR_ID) {
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(WeatherUtils.userRemindTime(MainActivity.this));
			return new TimeSlider(this, mTimeSetListener, c, 5);
		}
		return null;
	}

	public void closeRight() {
		slidingMenu.showSecondaryMenu();
	}
}
