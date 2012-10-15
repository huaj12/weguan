/**
 * 
 */
package com.juzhai.android.home.activity;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.common.model.Profession;
import com.juzhai.android.common.service.CommonData;
import com.juzhai.android.core.activity.ActivityCode;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.StringUtil;
import com.juzhai.android.core.utils.Validation;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.core.widget.wheelview.ArrayWheelAdapter;
import com.juzhai.android.core.widget.wheelview.OnWheelScrollListener;
import com.juzhai.android.core.widget.wheelview.WheelView;

/**
 * @author kooks
 * 
 */
public class SetProfessionActivity extends NavigationActivity {
	private List<Profession> professionList;
	private EditText editText;
	private TextView tipView;
	private Button finish;
	private WheelView professionView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		finish = (Button) (Button) getLayoutInflater().inflate(
				R.layout.button_finish, null);
		getNavigationBar().setRightView(finish);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.profession));
		setNavContentView(R.layout.page_setting_profession);
		String profession = getIntent().getStringExtra("profession");
		long professionId = getIntent().getLongExtra("professionId", -1);
		professionList = CommonData
				.getProfessionList(SetProfessionActivity.this);
		// 网速原因数据没加载完
		if (CollectionUtils.isEmpty(professionList)) {
			DialogUtils.showToastText(SetProfessionActivity.this,
					R.string.system_internet_erorr);
			this.finish();
		}
		editText = (EditText) findViewById(R.id.profession_input);
		tipView = (TextView) findViewById(R.id.profession_tip);
		if (professionId == 0) {
			tipView.setVisibility(View.VISIBLE);
			editText.setVisibility(View.VISIBLE);
		}
		editText.setText(profession);
		professionView = (WheelView) findViewById(R.id.profession);
		professionView.TEXT_SIZE = 35;
		ArrayWheelAdapter<Profession> professionWheelAdapter = new ArrayWheelAdapter<Profession>(
				professionList, 20);
		professionView.setAdapter(professionWheelAdapter);
		professionView.setCurrentItem(getProfessionIndxex(professionId,
				professionList));
		professionView.setScrollingListener(new OnWheelScrollListener() {
			@Override
			public void onScrollingStarted(WheelView wheel) {
			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				if (wheel.getCurrentItem() == professionList.size() - 1) {
					tipView.setVisibility(View.VISIBLE);
					editText.setVisibility(View.VISIBLE);
				} else {
					tipView.setVisibility(View.GONE);
					editText.setVisibility(View.GONE);
					editText.setText(null);
				}
			}

		});

		finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = getIntent();
				Profession profession = professionList.get(professionView
						.getCurrentItem());
				if (profession.getId() == 0) {
					String professionName = editText.getText().toString();
					if (StringUtils.isEmpty(professionName)) {
						DialogUtils.showToastText(SetProfessionActivity.this,
								R.string.profession_name_is_null);
						return;
					}
					int professionNameLength = StringUtil
							.chineseLength(professionName);
					if (professionNameLength > Validation.PROFESSION_LENGTH_MAX) {
						DialogUtils.showToastText(SetProfessionActivity.this,
								R.string.profession_name_too_long);
						return;
					}
					intent.putExtra("profession", professionName);
				} else {
					intent.putExtra("profession", profession.getName());
				}
				intent.putExtra("professionId", profession.getId());
				setResult(
						ActivityCode.ResultCode.SETTING_PROFESSION_RESULT_CODE,
						intent);
				SetProfessionActivity.this.finish();
			}
		});
	}

	// TODO (review) Indexex是什么意思
	private int getProfessionIndxex(long professionId,
			List<Profession> professions) {
		for (int i = 0; i < professions.size(); i++) {
			Profession profession = professions.get(i);
			if (profession.getId() == professionId) {
				return i;
			}
		}
		return 0;
	}

}
