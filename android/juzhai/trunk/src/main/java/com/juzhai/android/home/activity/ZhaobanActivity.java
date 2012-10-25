package com.juzhai.android.home.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.juzhai.android.R;
import com.juzhai.android.core.activity.ActivityCode;
import com.juzhai.android.core.widget.button.SegmentedButton;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.juzhai.android.home.adapter.UserPostAdapter;
import com.juzhai.android.home.bean.ZhaobanOrder;
import com.juzhai.android.home.service.impl.HomeService;
import com.juzhai.android.home.task.UserPostListGetDataTask;
import com.juzhai.android.main.activity.TabItemActivity;
import com.juzhai.android.passport.model.User;
import com.juzhai.android.post.activity.SendPostActivity;
import com.umeng.update.UmengUpdateAgent;

public class ZhaobanActivity extends TabItemActivity {
	private ZhaobanOrder order = ZhaobanOrder.ONLINE;
	private Integer gender = null;
	private JuzhaiRefreshListView postListView;
	private Location currentLocation;
	private LocationManager locationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		setNavContentView(R.layout.page_user_post_list);
		Button sendJzButton = (Button) getLayoutInflater().inflate(
				R.layout.button_send_jz, null);
		sendJzButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pushIntent(new Intent(ZhaobanActivity.this,
						SendPostActivity.class));
			}
		});
		getNavigationBar().setLeftView(sendJzButton);
		SegmentedButton segmentedButton = new SegmentedButton(this,
				getResources().getStringArray(R.array.post_head_tab_item), 80,
				32);
		segmentedButton
				.setOnClickListener(new SegmentedButton.OnClickListener() {
					@Override
					public void onClick(Button button, int which) {
						ZhaobanOrder selectOrder = null;
						switch (which) {
						case 0:
							selectOrder = ZhaobanOrder.ONLINE;
							break;
						case 1:
							selectOrder = ZhaobanOrder.NEW;
							break;
						}
						if (!selectOrder.getName().equals(order.getName())) {
							order = selectOrder;
							postListView.manualRefresh();
						}

					}
				});
		getNavigationBar().setBarTitleView(segmentedButton);
		final Button genderBtn = (Button) getLayoutInflater().inflate(
				R.layout.button_gender, null);
		genderBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(ZhaobanActivity.this)
						.setTitle(
								getResources()
										.getString(R.string.select_gender))
						.setItems(R.array.select_gender_item,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();
										Integer selectGender = null;
										switch (which) {
										case 0:
											selectGender = null;
											genderBtn
													.setBackgroundResource(R.drawable.gender_selector_button);
											break;
										case 1:
											selectGender = 1;
											genderBtn
													.setBackgroundResource(R.drawable.boy_selector_button);
											break;
										case 2:
											selectGender = 0;
											genderBtn
													.setBackgroundResource(R.drawable.girl_selector_button);
											break;
										}
										if (selectGender == null
												&& gender == null) {
											return;
										}
										if (selectGender != null
												&& gender != null
												&& gender.equals(selectGender)) {
											return;
										}
										gender = selectGender;
										postListView.manualRefresh();
									}
								}).show();
			}
		});
		getNavigationBar().setRightView(genderBtn);

		postListView = (JuzhaiRefreshListView) findViewById(R.id.user_post_list);
		postListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullDownToRefresh(refreshView);
				new UserPostListGetDataTask(ZhaobanActivity.this, postListView)
						.execute(gender, order, 1);
				// 开启定位
				updateLocation();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullUpToRefresh(refreshView);
				new UserPostListGetDataTask(ZhaobanActivity.this, postListView)
						.execute(gender, order, postListView.getPageAdapter()
								.getPager().getCurrentPage() + 1);
			}
		});
		postListView.setAdapter(new UserPostAdapter(ZhaobanActivity.this));
		postListView.manualRefresh();
		UmengUpdateAgent.update(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ActivityCode.RequestCode.ZHAOBAN_LIST_REQUEST_CODE
				&& resultCode == ActivityCode.ResultCode.ZHAOBAN_LIST_RESULT_CODE) {
			User user = (User) data.getSerializableExtra("user");
			int respCnt = user.getPostView().getRespCnt();
			user.getPostView().setRespCnt(respCnt + 1);
			int position = data.getIntExtra("position", -1);
			if (position >= 0 && user != null) {
				postListView.getPageAdapter().replaceData(position, user);
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private LocationListener locationListener = new LocationListener() {
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Log.d("onStatusChanged", provider);
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
			if (provider.equals(LocationManager.GPS_PROVIDER)) {
				locationManager.requestSingleUpdate(
						LocationManager.NETWORK_PROVIDER, locationListener,
						null);
			}
		}

		@Override
		public void onLocationChanged(Location location) {
			if (currentLocation == null
					|| isBetterLocation(location, currentLocation)) {
				currentLocation = location;
				// 请求服务端
				new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						new HomeService().updateLocation(ZhaobanActivity.this,
								currentLocation.getLongitude(),
								currentLocation.getLatitude());
						return null;
					}
				}.execute();
			}
		}
	};

	private void updateLocation() {
		// Criteria c = new Criteria();
		// c.setAccuracy(Criteria.ACCURACY_FINE);
		// c.setCostAllowed(false);
		// c.setAltitudeRequired(false);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria, true);
		locationManager.requestSingleUpdate(provider, locationListener, null);
	}

	private static final int CHECK_INTERVAL = 1000 * 30;

	private boolean isBetterLocation(Location location,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > CHECK_INTERVAL;
		boolean isSignificantlyOlder = timeDelta < -CHECK_INTERVAL;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location,
		// use the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must
			// be worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}
}
