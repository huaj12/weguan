/**
 * 
 */
package com.juzhai.android.home.activity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.juzhai.android.R;
import com.juzhai.android.common.model.City;
import com.juzhai.android.common.model.Province;
import com.juzhai.android.common.service.CommonData;
import com.juzhai.android.core.activity.ActivityCode;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.widget.wheelview.ArrayWheelAdapter;
import com.juzhai.android.core.widget.wheelview.OnWheelScrollListener;
import com.juzhai.android.core.widget.wheelview.WheelView;

/**
 * @author kooks
 * 
 */
public class SetAddressAcitvity extends Activity {
	private List<City> cityList;
	List<Province> provinceList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.page_setting_address);
		long cityId = getIntent().getLongExtra("cityId", 0);
		long provinceId = getIntent().getLongExtra("provinceId", 1);
		provinceList = CommonData.getProvinces(SetAddressAcitvity.this);
		cityList = getSelectCity(provinceId,
				CommonData.getCitys(SetAddressAcitvity.this));
		// 网速原因数据没加载完
		if (CollectionUtils.isEmpty(provinceList)
				|| CollectionUtils.isEmpty(cityList)) {
			DialogUtils.showToastText(SetAddressAcitvity.this,
					R.string.system_internet_erorr);
			this.finish();
		}
		final WheelView provinceView = (WheelView) findViewById(R.id.province);
		final WheelView cityView = (WheelView) findViewById(R.id.city);
		provinceView.TEXT_SIZE = 40;
		cityView.TEXT_SIZE = 40;
		ArrayWheelAdapter<Province> provinceWheelAdapter = new ArrayWheelAdapter<Province>(
				provinceList, 6);
		provinceView.setAdapter(provinceWheelAdapter);
		provinceView
				.setCurrentItem(getProvinceIndxex(provinceId, provinceList));
		provinceView.setScrollingListener(new OnWheelScrollListener() {
			@Override
			public void onScrollingStarted(WheelView wheel) {
			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				cityList = getSelectCity(
						provinceList.get(wheel.getCurrentItem())
								.getProvinceId(), CommonData
								.getCitys(SetAddressAcitvity.this));
				ArrayWheelAdapter<City> cityWheelAdapter = new ArrayWheelAdapter<City>(
						cityList, 10);
				cityView.setAdapter(cityWheelAdapter);
				cityView.setCurrentItem(0);
			}
		});
		ArrayWheelAdapter<City> cityWheelAdapter = new ArrayWheelAdapter<City>(
				cityList, 20);
		cityView.setAdapter(cityWheelAdapter);
		cityView.setCurrentItem(getCityIndxex(cityId, cityList));

		Button cancelBtn = (Button) findViewById(R.id.btn_cancel);
		Button okBtn = (Button) findViewById(R.id.btn_ok);
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SetAddressAcitvity.this.finish();
			}
		});
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = getIntent();
				Province province = provinceList.get(provinceView
						.getCurrentItem());
				City city = cityList.get(cityView.getCurrentItem());
				intent.putExtra("provinceId", province.getProvinceId());
				intent.putExtra("cityId", city.getCityId());
				intent.putExtra("provinceName", province.getProvinceName());
				intent.putExtra("cityName", city.getCityName());
				setResult(ActivityCode.ResultCode.SETTING_ADDRESS_RESULT_CODE,
						intent);
				SetAddressAcitvity.this.finish();
			}
		});
	}

	private int getCityIndxex(long cityId, List<City> citys) {
		for (int i = 0; i < citys.size(); i++) {
			City city = citys.get(i);
			if (city.getCityId() == cityId) {
				return i;
			}
		}
		return 0;
	}

	private int getProvinceIndxex(long provinceId, List<Province> provinces) {
		for (int i = 0; i < provinces.size(); i++) {
			Province province = provinces.get(i);
			if (province.getProvinceId() == provinceId) {
				return i;
			}
		}
		return 0;
	}

	private List<City> getSelectCity(long provinceId, List<City> allCitys) {
		List<City> ciyts = new ArrayList<City>();
		for (City city : allCitys) {
			if (provinceId == city.getProvinceId()) {
				ciyts.add(city);
			}
		}
		return ciyts;
	}
}
