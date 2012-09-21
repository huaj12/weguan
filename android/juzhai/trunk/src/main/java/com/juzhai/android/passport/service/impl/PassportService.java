package com.juzhai.android.passport.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.StringResult;
import com.juzhai.android.core.model.Result.UserResult;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.core.utils.StringUtil;
import com.juzhai.android.core.utils.Validation;
import com.juzhai.android.passport.data.UserCacheManager;
import com.juzhai.android.passport.exception.PassportException;
import com.juzhai.android.passport.service.IPassportService;

public class PassportService implements IPassportService {

	private static final String LOGIN_URI = "passport/login";
	private static final String REGISTER_URI = "passport/register";
	private static final String GETBACK_PWD_URI = "passport/getbackpwd";
	private static final String ACCESS_URI = "passport/tpAccess";

	@Override
	public boolean checkLogin(Context context) {
		String pToken = UserCacheManager.getPersistToken(context);
		if (StringUtils.isEmpty(pToken)) {
			return false;
		} else {
			// 有记录登录状态直接登录
			Map<String, String> cookies = new HashMap<String, String>();
			cookies.put("p_token", pToken);
			ResponseEntity<UserResult> responseEntity = null;
			try {
				responseEntity = HttpUtils.post(LOGIN_URI, null, cookies,
						UserResult.class);
			} catch (Exception e) {
				// 登录失败跳转到登录页面
				return false;
			}
			if (responseEntity.getBody() == null
					|| !responseEntity.getBody().getSuccess()) {
				return false;
			}
			UserCacheManager.cache(context, responseEntity);
			return true;
		}
	}

	@Override
	public void login(Context context, String account, String password)
			throws PassportException {
		if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
			throw new PassportException(R.string.login_defalut_error);
		}
		Map<String, String> values = new HashMap<String, String>();
		values.put("account", account);
		values.put("password", password);
		ResponseEntity<UserResult> responseEntity = null;
		try {
			responseEntity = HttpUtils
					.post(LOGIN_URI, values, UserResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(), "login error", e);
			}
			throw new PassportException(R.string.system_internet_erorr, e);
		}
		UserResult results = responseEntity.getBody();
		if (!results.getSuccess()) {
			throw new PassportException(results.getErrorInfo(), 0);
		} else {
			loginSuccess(context, responseEntity);
		}
	}

	private void loginSuccess(Context context,
			ResponseEntity<UserResult> responseEntity) {
		// 保存登录信息
		UserCacheManager.cache(context, responseEntity);
		UserCacheManager.persistToken(context, responseEntity);
	}

	@Override
	public void register(Context context, String account, String nickname,
			String pwd, String confirmPwd) throws PassportException {
		int errorId = VerifyData(nickname, account, pwd, confirmPwd);
		if (errorId > 0) {
			throw new PassportException(errorId);
		}
		Map<String, String> values = new HashMap<String, String>();
		values.put("nickname", nickname);
		values.put("account", account);
		values.put("pwd", pwd);
		values.put("confirmPwd", confirmPwd);
		ResponseEntity<UserResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.post(REGISTER_URI, values,
					UserResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(), "register error", e);
			}
			throw new PassportException(R.string.system_internet_erorr, e);
		}
		UserResult results = responseEntity.getBody();
		if (!results.getSuccess()) {
			throw new PassportException(results.getErrorInfo(), 0);
		} else {
			loginSuccess(context, responseEntity);
		}
	}

	private int VerifyData(String nickname, String account, String pwd,
			String confirmPwd) {
		if (StringUtils.isEmpty(nickname)) {
			return R.string.nickname_is_null;
		}
		int nicknameLength = StringUtil.chineseLength(nickname);
		if (nicknameLength > Validation.NICKNAME_LENGTH_MAX) {
			return R.string.nickname_too_long;
		}
		if (StringUtils.isEmpty(account)) {
			return R.string.email_is_null;
		}
		int emailLength = StringUtil.chineseLength(account);
		if (emailLength > Validation.REGISTER_EMAIL_MAX
				|| emailLength < Validation.REGISTER_EMAIL_MIN
				|| !StringUtil.checkMailFormat(account)) {
			return R.string.email_account_invalid;
		}
		int pwdLength = pwd.length();
		if (pwdLength < Validation.REGISTER_PASSWORD_MIN
				|| pwdLength > Validation.REGISTER_PASSWORD_MAX) {
			return R.string.pwd_length_invalid;
		}
		if (!StringUtils.equals(pwd, confirmPwd)) {
			return R.string.confirm_pwd_error;
		}
		return 0;
	}

	@Override
	public void getbackPwd(Context context, String account)
			throws PassportException {
		int emailLength = StringUtil.chineseLength(account);
		if (emailLength > Validation.REGISTER_EMAIL_MAX
				|| emailLength < Validation.REGISTER_EMAIL_MIN
				|| !StringUtil.checkMailFormat(account)) {
			throw new PassportException(R.string.email_account_invalid);
		}
		Map<String, String> values = new HashMap<String, String>();
		values.put("account", account);
		ResponseEntity<StringResult> response = null;
		try {
			response = HttpUtils.post(GETBACK_PWD_URI, values,
					StringResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(), "getback pwd error", e);
			}
			throw new PassportException(R.string.system_internet_erorr, e);
		}
		StringResult result = response.getBody();
		if (!result.getSuccess()) {
			throw new PassportException(result.getErrorInfo(), 0);
		}
	}

	@Override
	public void tpLogin(Context context, long tpId, String queryString)
			throws PassportException {
		ResponseEntity<UserResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(ACCESS_URI + "/" + tpId + "?"
					+ queryString, UserResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(), "thirdparty login error", e);
			}
			throw new PassportException(R.string.system_internet_erorr);
		}
		UserResult results = responseEntity.getBody();
		if (!results.getSuccess()) {
			throw new PassportException(results.getErrorInfo(), 0);
		} else {
			loginSuccess(context, responseEntity);
		}
	}
}
