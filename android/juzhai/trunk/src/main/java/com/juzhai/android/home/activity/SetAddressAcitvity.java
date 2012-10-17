/**
 * 
 */
package com.juzhai.android.home.activity;

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
import com.juzhai.android.core.utils.UIUtil;
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
		long provinceId = getIntent().getLongExtra("provinceId", -1);
		provinceList = CommonData.getProvinces(SetAddressAcitvity.this);
		cityList = CommonData.getSelectCity(provinceId,
				CommonData.getCitys(SetAddressAcitvity.this));
		// 网速原因数据没加载完
		if (CollectionUtils.isEmpty(provinceList)
				|| CollectionUtils.isEmpty(cityList)) {
			DialogUtils.showToastText(SetAddressAcitvity.this,
					R.string.system_internet_erorr);
			this.finish();
		}
		if (provinceId <= 0) {
			provinceId = provinceList.get(0).getProvinceId();
		}
		final WheelView provinceView = (WheelView) findViewById(R.id.province);
		final WheelView cityView = (WheelView) findViewById(R.id.city);
		provinceView.TEXT_SIZE = UIUtil.dip2px(SetAddressAcitvity.this, 27);
		cityView.TEXT_SIZE = UIUtil.dip2px(SetAddressAcitvity.this, 27);
		ArrayWheelAdapter<Province> provinceWheelAdapter = new ArrayWheelAdapter<Province>(
				provinceList, 6);
		provinceView.setAdapter(provinceWheelAdapter);
		provinceView.setCurrentItem(CommonData.getDataIndxex(provinceId,
				provinceList));
		provinceView.setScrollingListener(new OnWheelScrollListener() {
			@Override
			public void onScrollingStarted(WheelView wheel) {
			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				cityList = CommonData.getSelectCity(
						provinceList.get(wheel.getCurrentItem())
								.getProvinceId(), CommonData
								.getCitys(SetAddressAcitvity.this));
				ArrayWheelAdapter<City> cityWheelAdapter = new ArrayWheelAdapter<City>(
						cityList, 10);
				cityView.setAdapter(cityWheelAdapter);
				cityView.setCurrentItem(0);
			}
		});
		// TODO (review) 两个问题。a.为什么上面是10,下面是20。b.代码不觉得重复吗？
		ArrayWheelAdapter<City> cityWheelAdapter = new ArrayWheelAdapter<City>(
				cityList, 20);
		cityView.setAdapter(cityWheelAdapter);
		cityView.setCurrentItem(CommonData.getDataIndxex(cityId, cityList));

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
}
