package com.easylife.weather.main.activity;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easylife.weather.R;
import com.easylife.weather.core.activity.BaseActivity;
import com.easylife.weather.core.data.SharedPreferencesManager;
import com.easylife.weather.core.exception.WeatherException;
import com.easylife.weather.core.location.BDLocation;
import com.easylife.weather.core.location.BDLocation.BDLocationCallback;
import com.easylife.weather.core.utils.CitySqlite;
import com.easylife.weather.core.utils.DateUtil;
import com.easylife.weather.core.utils.JacksonSerializer;
import com.easylife.weather.main.adapter.CityListAdapter;
import com.easylife.weather.main.data.WeatherDataManager;
import com.easylife.weather.main.model.WeatherInfo;
import com.easylife.weather.main.service.IWeatherDataService;
import com.easylife.weather.main.service.impl.WeatherDataService;
import com.easylife.weather.passport.data.UserConfigManager;
import com.easylife.weather.passport.model.UserConfig;
import com.easylife.weather.passport.service.IPassportService;
import com.easylife.weather.passport.service.impl.PassPortService;

public class CityActivity extends BaseActivity {
	private IPassportService passPortService = new PassPortService();
	private ListView listView = null;
	private EditText searchEditTextView;
	private List<String> citys = new ArrayList<String>();
	private SharedPreferencesManager manager = null;
	private BDLocation location;
	private UserConfig user;
	private CitySqlite sqlLite = null;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						try {
							passPortService.updateUserConfig(user,
									CityActivity.this);
						} catch (WeatherException e) {
						}
						return null;
					}
				}.execute();
				;
				break;
			default:
				Toast.makeText(CityActivity.this, msg.what, Toast.LENGTH_SHORT)
						.show();
				break;
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_city);
		sqlLite = new CitySqlite(CityActivity.this);
		searchEditTextView = (EditText) findViewById(R.id.search_edit_text);
		Button calcelBtn = (Button) findViewById(R.id.cancel_btn);
		manager = new SharedPreferencesManager(CityActivity.this);
		calcelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CityActivity.this.finish();
			}
		});
		listView = (ListView) findViewById(R.id.list_view);
		String historys = manager.getString(SharedPreferencesManager.HISTORY);
		if (StringUtils.hasText(historys)) {
			try {
				citys = new ObjectMapper().readValue(historys,
						new TypeReference<List<String>>() {
						});
			} catch (Exception e) {
			}
		}
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View convertView = inflater.inflate(R.layout.item_auto_loction, null);
		listView.addHeaderView(convertView);
		listView.setAdapter(new CityListAdapter(citys, CityActivity.this));
		searchEditTextView.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String content = s.toString();
				if (!StringUtils.hasText(content)) {
					return;
				}
				Cursor cursor = null;
				try {
					cursor = sqlLite.getChooseCity(content);
					citys = new ArrayList<String>();
					while (cursor.moveToNext()) {
						String name = cursor.getString(0);
						citys.add(name);
					}
				} finally {
					if (cursor != null) {
						cursor.close();
					}
				}
				BaseAdapter adapter = new CityListAdapter(citys,
						CityActivity.this);
				adapter.notifyDataSetChanged();
				listView.setAdapter(adapter);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {
				if (index <= 0) {
					BDLocation location = new BDLocation(CityActivity.this);
					location.start(new BDLocationCallback() {

						@Override
						public void successCallback(Double lat, Double lng) {
							handler.sendEmptyMessage(R.string.locate_success);
							// 从服务端获取url
							IWeatherDataService weatherDataService = new WeatherDataService();
							String cityUrl = null;
							try {
								cityUrl = weatherDataService.getUrl(lat, lng,
										CityActivity.this);
							} catch (WeatherException e) {
							}
							if (cityUrl != null) {
								WeatherDataManager
										.delWeatherDate(CityActivity.this);
								// 获取天气数据和城市
								weatherDataService.getWeatherInfo(cityUrl,
										CityActivity.this);
								// 保存天气和城市
								// 更新用户所在城市
								WeatherInfo info = WeatherDataManager
										.getWeatherInfos(DateUtil.getToday(),
												CityActivity.this);
								if (info != null) {
									user = UserConfigManager
											.getUserConfig(CityActivity.this);
									user.setCityName(info.getCityName());
									handler.sendEmptyMessage(1);
									startActivity(new Intent(CityActivity.this,
											MainActivity.class));
									finish();
								} else {
									handler.sendEmptyMessage(R.string.no_network);
								}

							} else {
								handler.sendEmptyMessage(R.string.no_network);
							}

						}

						@Override
						public void errorCallback() {
							handler.sendEmptyMessage(R.string.locate_error);
						}
					});
				} else {
					String content = citys.get(index - 1);
					if (!StringUtils.hasText(content)) {
						return;
					}
					try {
						String str = manager
								.getString(SharedPreferencesManager.HISTORY);
						List<String> historys = new ArrayList<String>();
						if (StringUtils.hasText(str)) {
							historys = new ObjectMapper().readValue(str,
									new TypeReference<List<String>>() {
									});
						}
						historys.remove(content);
						historys.add(content);
						String jsonStr = JacksonSerializer.toString(historys);
						manager.commit(SharedPreferencesManager.HISTORY,
								jsonStr);
					} catch (Exception e) {
						return;
					}
					user = UserConfigManager.getUserConfig(CityActivity.this);
					user.setCityName(content);
					handler.sendEmptyMessage(1);
					WeatherDataManager.delWeatherDate(CityActivity.this);
					startActivity(new Intent(CityActivity.this,
							MainActivity.class));
					finish();
				}
			}
		});
		// 设置焦点
		TextView textView = (TextView) findViewById(R.id.bottom_gone_text_view);
		textView.setFocusable(true);
		textView.setFocusableInTouchMode(true);
		textView.requestFocus();
	}

	@Override
	protected void onDestroy() {
		if (location != null) {
			location.stop();
		}
		if (sqlLite != null) {
			sqlLite.close();
		}
		super.onDestroy();
	}
}
