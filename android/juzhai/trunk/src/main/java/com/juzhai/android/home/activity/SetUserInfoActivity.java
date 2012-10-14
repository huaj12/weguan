package com.juzhai.android.home.activity;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.juzhai.android.R;
import com.juzhai.android.core.activity.ActivityCode;
import com.juzhai.android.core.activity.UploadImageActivity;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.JzUtils;
import com.juzhai.android.core.utils.UIUtil;
import com.juzhai.android.core.widget.list.table.model.BasicItem.ItemType;
import com.juzhai.android.core.widget.list.table.model.ViewItem;
import com.juzhai.android.core.widget.list.table.widget.UITableView;
import com.juzhai.android.core.widget.list.table.widget.UITableView.ClickListener;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.home.helper.IUserViewHelper;
import com.juzhai.android.home.helper.impl.UserViewHelper;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.model.User;

public class SetUserInfoActivity extends NavigationActivity {
	protected IUserViewHelper userViewHelper = new UserViewHelper();
	protected UITableView logoTableView;
	protected UITableView infoTableView;
	protected UITableView otherTableView;
	protected ImageView userLogoView;
	protected RelativeLayout logoLayout;
	protected Context mContext;
	protected Button finish;
	protected User user = UserCache.getUserInfo();
	protected boolean isGuide = false;

	protected void init() {
		logoLayout = (RelativeLayout) getLayoutInflater().inflate(
				R.layout.fragment_user_upload_logo, null);
		logoTableView = (UITableView) findViewById(R.id.setting_logo_table_view);
		infoTableView = (UITableView) findViewById(R.id.setting_info_table_view);
		otherTableView = (UITableView) findViewById(R.id.setting_other_table_view);

		logoList();
		logoTableView.commit();

		infoList();
		infoTableView.commit();

		otherList();
		otherTableView.commit();

	}

	protected void logoList() {
		userLogoView = (ImageView) logoLayout.findViewById(R.id.user_logo);
		userLogoView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, UploadImageActivity.class);
				startActivityForResult(intent,
						ActivityCode.RequestCode.PIC_REQUEST_CODE);
			}
		});
		userViewHelper.showUserLogo(mContext, user, userLogoView, 35, 35);
		logoTableView.addViewItem(new ViewItem(logoLayout));
	}

	protected void infoList() {
		infoTableView.setClickListener(new ClickListener() {

			@Override
			public void onClick(int index) {
				if (index == 0) {
					Intent intent = new Intent(mContext,
							SetNicknameActivity.class);
					intent.putExtra("nickname", user.getNickname());
					pushIntentForResult(
							intent,
							ActivityCode.RequestCode.SETTING_NICKNAME_REQUEST_CODE);
				} else if (index == 1) {
					new AlertDialog.Builder(mContext)
							.setTitle(
									getResources().getString(
											R.string.select_gender))
							.setItems(R.array.gender_item,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
											switch (which) {
											case 0:
												user.setGender(1);
												break;
											case 1:
												user.setGender(0);
												break;
											}
											finish.setEnabled(validateFinish());
											reloadInfoTableView();
										}

									}).show();
				} else if (index == 2) {
					int year = user.getBirthYear();
					int month = user.getBirthMonth() - 1;
					int day = user.getBirthDay();
					if (year <= 0 || month <= 0 || day <= 0) {
						Calendar cal = Calendar.getInstance();
						year = cal.get(Calendar.YEAR);
						month = cal.get(Calendar.MONTH);
						day = cal.get(Calendar.DAY_OF_MONTH);
					}
					DatePickerDialog dlg = new DatePickerDialog(mContext,
							new OnDateSetListener() {
								@Override
								public void onDateSet(DatePicker view,
										int year, int monthOfYear,
										int dayOfMonth) {
									user.setBirthYear(year);
									user.setBirthMonth(monthOfYear + 1);
									user.setBirthDay(dayOfMonth);
									finish.setEnabled(validateFinish());
									reloadInfoTableView();
								}
							}, year, month, day);
					dlg.show();
				}
			}
		});

		infoTableView.addBasicItem(getResources().getString(R.string.nickname),
				user.getNickname(), ItemType.HORIZONTAL);
		infoTableView.addBasicItem(getResources().getString(R.string.gender),
				JzUtils.getGender(user.getGender(), mContext),
				ItemType.HORIZONTAL);
		if (user.getBirthYear() <= 0) {
			infoTableView.addBasicItem(
					getResources().getString(R.string.birthday), null,
					ItemType.HORIZONTAL);
		} else {
			infoTableView.addBasicItem(
					getResources().getString(R.string.birthday),
					user.getBirthYear() + "-" + user.getBirthMonth() + "-"
							+ user.getBirthDay(), ItemType.HORIZONTAL);
		}
	}

	protected void otherList() {
		otherTableView.setClickListener(new ClickListener() {

			@Override
			public void onClick(int index) {
				if (index == 0) {
					Intent intent = new Intent(mContext,
							SetAddressAcitvity.class);
					intent.putExtra("provinceId", user.getProvinceId());
					intent.putExtra("cityId", user.getCityId());
					pushIntentForResult(
							intent,
							ActivityCode.RequestCode.SETTING_ADDRESS_REQUEST_CODE);
				} else if (index == 1) {
					Intent intent = new Intent(mContext,
							SetProfessionActivity.class);
					intent.putExtra("professionId", user.getProfessionId());
					intent.putExtra("profession", user.getProfession());
					pushIntentForResult(
							intent,
							ActivityCode.RequestCode.SETTING_PROFESSION_REQUEST_CODE);
				} else if (index == 2 && !isGuide) {
					Intent intent = new Intent(mContext,
							SetFeatureActivity.class);
					intent.putExtra("feature", user.getFeature());
					pushIntentForResult(
							intent,
							ActivityCode.RequestCode.SETTING_FEATURE_REQUEST_CODE);
				}

			}
		});

		otherTableView.addBasicItem(getResources().getString(R.string.address),
				user.getCityName(), ItemType.HORIZONTAL);
		otherTableView.addBasicItem(
				getResources().getString(R.string.profession),
				user.getProfession(), ItemType.HORIZONTAL);
		if (!isGuide) {
			otherTableView.addBasicItem(
					getResources().getString(R.string.feature),
					user.getFeature() == null ? getResources().getString(
							R.string.please_enter_feature) : user.getFeature(),
					ItemType.VERTICAL);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ActivityCode.RequestCode.PIC_REQUEST_CODE
				&& ActivityCode.ResultCode.PIC_RESULT_CODE == resultCode) {
			Bitmap logo = data.getParcelableExtra("pic");
			if (logo != null) {
				user.setLogoImage(logo);
				userLogoView.setImageBitmap(ImageUtils.zoomBitmap(logo,
						UIUtil.dip2px(mContext, 35),
						UIUtil.dip2px(mContext, 35)));
				finish.setEnabled(validateFinish());
			} else {
				DialogUtils.showToastText(mContext, R.string.select_pic_error);
			}
		} else if (requestCode == ActivityCode.RequestCode.SETTING_NICKNAME_REQUEST_CODE
				&& ActivityCode.ResultCode.SETTING_NICKNAME_RESULT_CODE == resultCode) {
			String nickname = data.getStringExtra("nickname");
			user.setNickname(nickname);
			finish.setEnabled(validateFinish());
			reloadInfoTableView();
		} else if (requestCode == ActivityCode.RequestCode.SETTING_ADDRESS_REQUEST_CODE
				&& ActivityCode.ResultCode.SETTING_ADDRESS_RESULT_CODE == resultCode) {
			long provinceId = data.getLongExtra("provinceId", -1);
			long cityId = data.getLongExtra("cityId", -1);
			String provinceName = data.getStringExtra("provinceName");
			String cityName = data.getStringExtra("cityName");
			if (provinceId != -1 && cityId != -1) {
				user.setProvinceId(provinceId);
				user.setCityId(cityId);
				user.setProvinceName(provinceName);
				user.setCityName(cityName);
				finish.setEnabled(validateFinish());
				reloadOtherTableView();
			}

		} else if (requestCode == ActivityCode.RequestCode.SETTING_FEATURE_REQUEST_CODE
				&& ActivityCode.ResultCode.SETTING_FEATURE_RESULT_CODE == resultCode) {
			String feature = data.getStringExtra("feature");
			user.setFeature(feature);
			finish.setEnabled(validateFinish());
			reloadOtherTableView();
		} else if (requestCode == ActivityCode.RequestCode.SETTING_PROFESSION_REQUEST_CODE
				&& ActivityCode.ResultCode.SETTING_PROFESSION_RESULT_CODE == resultCode) {
			String profession = data.getStringExtra("profession");
			long professionId = data.getLongExtra("professionId", -1);
			user.setProfession(profession);
			user.setProfessionId(professionId);
			finish.setEnabled(validateFinish());
			reloadOtherTableView();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	private void reloadInfoTableView() {
		infoTableView.clear();
		infoList();
		infoTableView.commit();
	}

	private void reloadOtherTableView() {
		otherTableView.clear();
		otherList();
		otherTableView.commit();
	}

	public boolean validateFinish() {
		if (isGuide) {
			if (user.getLogoImage() != null
					&& StringUtils.isNotEmpty(user.getNickname())
					&& user.getBirthYear() > 0
					&& user.getBirthMonth() > 0
					&& user.getBirthDay() > 0
					&& user.getCityId() > 0
					&& (user.getProfessionId() > 0 || (user.getProfessionId() == 0 && StringUtils
							.isNotEmpty(user.getProfession())))) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	public void setGuide(boolean isGuide) {
		this.isGuide = isGuide;
	}

}
