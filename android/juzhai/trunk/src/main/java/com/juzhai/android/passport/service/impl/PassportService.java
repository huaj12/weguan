package com.juzhai.android.passport.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import android.content.Context;
import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.StringResult;
import com.juzhai.android.core.model.Result.UserResult;
import com.juzhai.android.core.stat.UmengEvent;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.core.utils.StringUtil;
import com.juzhai.android.core.utils.Validation;
import com.juzhai.android.passport.data.UserCacheManager;
import com.juzhai.android.passport.exception.PassportException;
import com.juzhai.android.passport.service.IPassportService;
import com.umeng.analytics.MobclickAgent;

public class PassportService implements IPassportService {

	private static final String LOGIN_URI = "passport/login";
	private static final String LOGOUT_URI = "passport/logout";
	private static final String REGISTER_URI = "passport/register";
	private static final String GETBACK_PWD_URI = "passport/getbackpwd";
	private static final String LOGIN_ACCESS_URI = "passport/tpAccess";
	private static final String BIND_ACCESS_URI = "passport/authorize/bind/access";
	private static final String EXPIRED_ACCESS_URI = "passport/authorize/expired/access";

	@Override
	public boolean checkLogin(Context context) {
		String pToken = UserCacheManager.getPersistToken(context);
		if (!StringUtils.hasText(pToken)) {
			return false;
		} else {
			// 有记录登录状态直接登录
			Map<String, String> cookies = new HashMap<String, String>();
			cookies.put("p_token", pToken);
			cookies.put("l_token", UserCacheManager.getPersistLToken(context));
			ResponseEntity<UserResult> responseEntity = null;
			try {
				responseEntity = HttpUtils.post(context, LOGIN_URI, null,
						cookies, UserResult.class);
			} catch (Exception e) {
				// 登录失败跳转到登录页面
				return false;
			}
			if (responseEntity.getBody() == null
					|| !responseEntity.getBody().getSuccess()) {
				return false;
			}
			loginSuccess(context, responseEntity);
			return true;
		}
	}

	@Override
	public void login(Context context, String account, String password)
			throws PassportException {
		if (!StringUtil.checkMailFormat(account)) {
			throw new PassportException(context,
					R.string.login_account_is_null_error);
		}
		if (!StringUtils.hasText(account) || !StringUtils.hasText(password)) {
			throw new PassportException(context, R.string.login_defalut_error);
		}
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("account", account);
		values.put("password", password);
		ResponseEntity<UserResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.post(context, LOGIN_URI, values,
					UserResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(), "login error", e);
			}
			throw new PassportException(context,
					R.string.system_internet_erorr, e);
		}
		UserResult results = responseEntity.getBody();
		if (!results.getSuccess()) {
			throw new PassportException(context, results.getErrorInfo());
		} else {
			loginSuccess(context, responseEntity);
		}
	}

	private void loginSuccess(Context context,
			ResponseEntity<UserResult> responseEntity) {
		// 保存登录信息
		UserCacheManager.cache(context, responseEntity);
		UserCacheManager.persistInfo(context, responseEntity);
	}

	@Override
	public void register(Context context, String account, String nickname,
			String pwd, String confirmPwd) throws PassportException {
		int errorId = verifyData(nickname, account, pwd, confirmPwd);
		if (errorId > 0) {
			throw new PassportException(context, errorId);
		}
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("nickname", nickname);
		values.put("account", account);
		values.put("pwd", pwd);
		values.put("confirmPwd", confirmPwd);
		ResponseEntity<UserResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.post(context, REGISTER_URI, values,
					UserResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(), "register error", e);
			}
			throw new PassportException(context,
					R.string.system_internet_erorr, e);
		}
		UserResult results = responseEntity.getBody();
		if (!results.getSuccess()) {
			throw new PassportException(context, results.getErrorInfo());
		} else {
			MobclickAgent.onEvent(context, UmengEvent.LOCAL_REGISTER);
			loginSuccess(context, responseEntity);
		}
	}

	private int verifyData(String nickname, String account, String pwd,
			String confirmPwd) {
		int emailLength = StringUtil.chineseLength(account);
		if (emailLength > Validation.REGISTER_EMAIL_LENGTH_MAX
				|| emailLength < Validation.REGISTER_EMAIL_LENGTH_MIN
				|| !StringUtil.checkMailFormat(account)) {
			return R.string.email_account_invalid_error;
		}
		if (!StringUtils.hasText(nickname)) {
			return R.string.nickname_is_null_error;
		}
		int nicknameLength = StringUtil.chineseLength(nickname);
		if (nicknameLength > Validation.NICKNAME_LENGTH_MAX) {
			return R.string.nickname_too_long_error;
		}
		int pwdLength = pwd.length();
		if (pwdLength < Validation.REGISTER_PASSWORD_LENGTH_MIN
				|| pwdLength > Validation.REGISTER_PASSWORD_LENGTH_MAX) {
			return R.string.pwd_length_invalid_error;
		}
		if (!pwd.equals(confirmPwd)) {
			return R.string.confirm_pwd_error;
		}
		return 0;
	}

	@Override
	public void getbackPwd(Context context, String account)
			throws PassportException {
		int emailLength = StringUtil.chineseLength(account);
		if (emailLength > Validation.REGISTER_EMAIL_LENGTH_MAX
				|| emailLength < Validation.REGISTER_EMAIL_LENGTH_MIN
				|| !StringUtil.checkMailFormat(account)) {
			throw new PassportException(context,
					R.string.email_account_invalid_error);
		}
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("account", account);
		ResponseEntity<StringResult> response = null;
		try {
			response = HttpUtils.post(context, GETBACK_PWD_URI, values,
					StringResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(), "getback pwd error", e);
			}
			throw new PassportException(context,
					R.string.system_internet_erorr, e);
		}
		StringResult result = response.getBody();
		if (!result.getSuccess()) {
			throw new PassportException(context, result.getErrorInfo());
		}
	}

	@Override
	public void tpLogin(Context context, long tpId, String queryString)
			throws PassportException {
		ResponseEntity<UserResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(context, LOGIN_ACCESS_URI + "/"
					+ tpId + "?" + queryString, UserResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(), "thirdparty login error", e);
			}
			throw new PassportException(context, R.string.system_internet_erorr);
		}
		UserResult results = responseEntity.getBody();
		if (!results.getSuccess()) {
			throw new PassportException(context, results.getErrorInfo());
		} else {
			loginSuccess(context, responseEntity);
		}
	}

	@Override
	public void logout(Context context) {
		ResponseEntity<UserResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.post(context, LOGOUT_URI, null,
					UserResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(this.getClass().getSimpleName(), "logout error.", e);
			}
		}
		if (responseEntity.getBody() == null
				|| !responseEntity.getBody().getSuccess()) {
			if (BuildConfig.DEBUG) {
				Log.d(this.getClass().getSimpleName(), "logout response error.");
			}
		}
		// 本地登出
		UserCacheManager.localLogout(context);
	}

	@Override
	public void tpExpiredAuthorize(Context context, long tpId,
			String queryString) throws PassportException {
		doAuthorize(context, tpId, EXPIRED_ACCESS_URI + "/" + tpId + "?"
				+ queryString);
	}

	@Override
	public void tpBind(Context context, long tpId, String queryString)
			throws PassportException {
		doAuthorize(context, tpId, BIND_ACCESS_URI + "/" + tpId + "?"
				+ queryString);
	}

	private void doAuthorize(Context context, long tpId, String url)
			throws PassportException {
		ResponseEntity<UserResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(context, url, UserResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(), "thirdparty login error", e);
			}
			throw new PassportException(context, R.string.system_internet_erorr);
		}
		UserResult result = responseEntity.getBody();
		if (!result.getSuccess()) {
			throw new PassportException(context, result.getErrorInfo());
		} else {
			UserCacheManager.updateUserCache(context, result.getResult());
		}
	}
}
