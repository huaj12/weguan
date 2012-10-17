package com.juzhai.android.home.activity;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import com.juzhai.android.core.utils.StringUtil;
import com.juzhai.android.core.utils.Validation;
import com.juzhai.android.core.widget.list.table.model.BasicItem.ItemType;
import com.juzhai.android.core.widget.list.table.model.ViewItem;
import com.juzhai.android.core.widget.list.table.widget.UITableView;
import com.juzhai.android.core.widget.list.table.widget.UITableView.ClickListener;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.home.helper.IUserViewHelper;
import com.juzhai.android.home.helper.impl.UserViewHelper;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.model.User;

public abstract class SetUserInfoActivity extends NavigationActivity {
	protected IUserViewHelper userViewHelper = new UserViewHelper();
	protected UITableView logoTableView;
	protected UITableView infoTableView;
	protected UITableView otherTableView;
	protected ImageView userLogoView;
	protected RelativeLayout logoLayout;
	protected Button finish;
	protected User user = UserCache.getCopyUserInfo();
	protected boolean isGuide = false;
	private boolean isUpdateGender = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setNavContentView(getNavContentViewLayout());
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

	private void logoList() {
		userLogoView = (ImageView) logoLayout.findViewById(R.id.user_logo);
		userViewHelper.showUserLogo(SetUserInfoActivity.this, user,
				userLogoView, 35, 35);
		logoTableView.setClickListener(new ClickListener() {
			@Override
			public void onClick(int index) {
				if (index == 0) {
					Intent intent = new Intent(SetUserInfoActivity.this,
							UploadImageActivity.class);
					startActivityForResult(intent,
							ActivityCode.RequestCode.PIC_REQUEST_CODE);
				}
			}
		});
		logoTableView.addViewItem(new ViewItem(logoLayout));
	}

	private void infoList() {
		infoTableView.setClickListener(new ClickListener() {
			@Override
			public void onClick(int index) {
				if (index == 0) {
					Intent intent = new Intent(SetUserInfoActivity.this,
							SetNicknameActivity.class);
					intent.putExtra("nickname", user.getNickname());
					pushIntentForResult(
							intent,
							ActivityCode.RequestCode.SETTING_NICKNAME_REQUEST_CODE);
				} else if (index == 1) {
					new AlertDialog.Builder(SetUserInfoActivity.this)
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
												isUpdateGender = true;
												break;
											case 1:
												user.setGender(0);
												isUpdateGender = true;
												break;
											}
											finish.setEnabled(true);
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
					DatePickerDialog dlg = new DatePickerDialog(
							SetUserInfoActivity.this, new OnDateSetListener() {
								@Override
								public void onDateSet(DatePicker view,
										int year, int monthOfYear,
										int dayOfMonth) {
									user.setBirthYear(year);
									user.setBirthMonth(monthOfYear + 1);
									user.setBirthDay(dayOfMonth);
									finish.setEnabled(true);
									reloadInfoTableView();
								}
							}, year, month, day);
					dlg.show();
				}
			}
		});

		infoTableView.addBasicItem(getResources().getString(R.string.nickname),
				user.getNickname(), ItemType.HORIZONTAL);
		if (isGuide) {
			infoTableView.addBasicItem(getResources()
					.getString(R.string.gender),
					getResources().getString(R.string.select_gender),
					ItemType.HORIZONTAL);
		} else {
			infoTableView.addBasicItem(getResources()
					.getString(R.string.gender), JzUtils.getGender(
					user.getGender(), SetUserInfoActivity.this),
					ItemType.HORIZONTAL);
		}
		infoTableView.addBasicItem(getResources().getString(R.string.birthday),
				user.getBirthYear() <= 0 ? null : user.getBirthYear() + "-"
						+ user.getBirthMonth() + "-" + user.getBirthDay(),
				ItemType.HORIZONTAL);
	}

	private void otherList() {
		otherTableView.setClickListener(new ClickListener() {
			@Override
			public void onClick(int index) {
				if (index == 0) {
					Intent intent = new Intent(SetUserInfoActivity.this,
							SetAddressAcitvity.class);
					intent.putExtra("provinceId", user.getProvinceId());
					intent.putExtra("cityId", user.getCityId());
					pushIntentForResult(
							intent,
							ActivityCode.RequestCode.SETTING_ADDRESS_REQUEST_CODE);
				} else if (index == 1) {
					Intent intent = new Intent(SetUserInfoActivity.this,
							SetProfessionActivity.class);
					intent.putExtra("professionId", user.getProfessionId());
					intent.putExtra("profession", user.getProfession());
					pushIntentForResult(
							intent,
							ActivityCode.RequestCode.SETTING_PROFESSION_REQUEST_CODE);
				} else if (index == 2 && !isGuide) {
					Intent intent = new Intent(SetUserInfoActivity.this,
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ActivityCode.RequestCode.PIC_REQUEST_CODE
				&& ActivityCode.ResultCode.PIC_RESULT_CODE == resultCode) {
			Bitmap logo = data.getParcelableExtra("pic");
			if (logo != null) {
				user.setLogoImage(logo);
				userLogoView.setImageBitmap(ImageUtils.zoomBitmap(logo, 35, 35,
						SetUserInfoActivity.this));
				finish.setEnabled(true);
			} else {
				DialogUtils.showToastText(SetUserInfoActivity.this,
						R.string.select_pic_error);
			}
		} else if (requestCode == ActivityCode.RequestCode.SETTING_NICKNAME_REQUEST_CODE
				&& ActivityCode.ResultCode.SETTING_NICKNAME_RESULT_CODE == resultCode) {
			String nickname = data.getStringExtra("nickname");
			user.setNickname(nickname);
			finish.setEnabled(true);
			reloadInfoTableView();
		} else if (requestCode == ActivityCode.RequestCode.SETTING_ADDRESS_REQUEST_CODE
				&& ActivityCode.ResultCode.SETTING_ADDRESS_RESULT_CODE == resultCode) {
			long provinceId = data.getLongExtra("provinceId", -1);
			long cityId = data.getLongExtra("cityId", -1);
			String provinceName = data.getStringExtra("provinceName");
			String cityName = data.getStringExtra("cityName");
			if (provinceId > 0 && cityId > 0) {
				user.setProvinceId(provinceId);
				user.setCityId(cityId);
				user.setProvinceName(provinceName);
				user.setCityName(cityName);
				finish.setEnabled(true);
				reloadOtherTableView();
			}

		} else if (requestCode == ActivityCode.RequestCode.SETTING_FEATURE_REQUEST_CODE
				&& ActivityCode.ResultCode.SETTING_FEATURE_RESULT_CODE == resultCode) {
			String feature = data.getStringExtra("feature");
			user.setFeature(feature);
			finish.setEnabled(true);
			reloadOtherTableView();
		} else if (requestCode == ActivityCode.RequestCode.SETTING_PROFESSION_REQUEST_CODE
				&& ActivityCode.ResultCode.SETTING_PROFESSION_RESULT_CODE == resultCode) {
			String profession = data.getStringExtra("profession");
			long professionId = data.getLongExtra("professionId", -1);
			user.setProfession(profession);
			user.setProfessionId(professionId);
			finish.setEnabled(true);
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

	public void setGuide(boolean isGuide) {
		this.isGuide = isGuide;
	}

	protected boolean validation() {
		// TODO (done) 个人简介的验证呢？性别验证呢？
		if (StringUtils.isEmpty(user.getNickname())) {
			DialogUtils.showToastText(SetUserInfoActivity.this,
					R.string.nickname_is_null);
			return false;
		}
		if (isGuide) {
			if (!isUpdateGender) {
				DialogUtils.showToastText(SetUserInfoActivity.this,
						R.string.user_gender_is_null);
				return false;
			}
		}
		if (user.getBirthYear() <= 0) {
			DialogUtils.showToastText(SetUserInfoActivity.this,
					R.string.user_birth_day_is_null);
			return false;
		}
		if (user.getCityId() <= 0) {
			DialogUtils.showToastText(SetUserInfoActivity.this,
					R.string.user_address_is_null);
			return false;
		}
		if (user.getProfessionId() <= 0
				&& StringUtils.isNotEmpty(user.getProfession())) {
			DialogUtils.showToastText(SetUserInfoActivity.this,
					R.string.profession_name_is_null);
			return false;
		}
		if (!isGuide) {
			String feature = user.getFeature();
			if (StringUtils.isEmpty(feature)) {
				DialogUtils.showToastText(SetUserInfoActivity.this,
						R.string.feature_is_null);
				return false;
			}
			int featureLength = StringUtil.chineseLength(feature);
			if (featureLength > Validation.USER_FEATURE_LENGTH_MAX) {
				DialogUtils.showToastText(SetUserInfoActivity.this,
						R.string.feature_too_long);
				return false;
			}
		}
		return true;
	}

	abstract protected int getNavContentViewLayout();

}
