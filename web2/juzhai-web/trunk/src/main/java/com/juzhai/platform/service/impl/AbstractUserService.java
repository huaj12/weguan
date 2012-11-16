package com.juzhai.platform.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.juzhai.core.SystemConfig;
import com.juzhai.core.bean.DeviceName;
import com.juzhai.core.web.bean.RequestParameter;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.dao.ITpUserDao;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.IPassportService;
import com.juzhai.passport.service.IRegisterService;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.platform.bean.Terminal;
import com.juzhai.platform.exception.TokenAuthorizeException;
import com.juzhai.platform.service.ISynchronizeService;
import com.juzhai.platform.service.IUserService;

;

public abstract class AbstractUserService implements IUserService {

	private static final Log log = LogFactory.getLog(AbstractUserService.class);

	@Autowired
	private ITpUserDao tpUserDao;
	@Autowired
	private IRegisterService registerService;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	// @Autowired
	// private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IPassportService passportService;
	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private ISynchronizeService synchronizeService;

	@Override
	public long access(RequestParameter requestParameter, AuthInfo authInfo,
			Thirdparty tp, long inviterUid, DeviceName deviceName) {
		if (authInfo == null) {
			authInfo = new AuthInfo();
		}
		String tpIdentity = fetchTpIdentity(requestParameter, authInfo, tp);
		if (StringUtils.isEmpty(tpIdentity) || "null".equals(tpIdentity)) {
			log.error("Fetch thirdparty identity failed.[tpName:"
					+ tp.getName() + ", joinType:" + tp.getJoinType());
			return 0L;
		}
		authInfo.setTpIdentity(tpIdentity);
		return completeAccessUser(requestParameter, authInfo, tpIdentity, tp,
				inviterUid, deviceName);
	}

	private long completeAccessUser(RequestParameter requestParameter,
			AuthInfo authInfo, String tpIdentity, Thirdparty tp,
			long inviterUid, DeviceName deviceName) {
		log.debug("completeAssessUser");
		TpUser tpUser = tpUserDao.selectTpUserByTpNameAndTpIdentity(
				tp.getName(), tpIdentity);
		long uid = 0;
		if (null == tpUser || tpUser.getUid() <= 0) {
			if (log.isDebugEnabled()) {
				log.debug("tpu is null[tp=" + tp.getName()
						+ ", need to create.]");
			}
			// 注册&激活账号&激活产品
			Profile profile = convertToProfile(requestParameter, authInfo,
					tpIdentity);
			if (null == profile) {
				return 0;
			}
			uid = registerService.autoRegister(tp, tpIdentity, authInfo,
					profile, inviterUid, deviceName);
			// redis记录已安装App的用户
			// redisTemplate.opsForSet().add(
			// RedisKeyGenerator.genTpInstallUsersKey(tp.getName()),
			// tpIdentity);
			registerSucesssAfter(tp, authInfo, deviceName);
		} else {
			if (log.isDebugEnabled()) {
				log.debug("save authInfo.[tp=" + tpUser.getTpName() + ", uid="
						+ tpUser.getUid() + "].");
			}
			uid = tpUser.getUid();
			tpUserAuthService.updateTpUserAuth(uid, tp.getId(), authInfo);
		}
		if (uid > 0) {
			// 缓存AuthInfo
			tpUserAuthService.cacheAuthInfo(uid, authInfo);
		}

		return uid;
	}

	@Override
	public String getLoginAuthorizeURLforCode(Thirdparty tp, Terminal terminal,
			String turnTo, String incode) throws UnsupportedEncodingException {
		return getAuthorizeURLforCode(tp, terminal, turnTo, incode,
				tp.getAppUrl());
	}

	@Override
	public String getExpiredAuthorizeURLforCode(Thirdparty tp, Terminal terminal)
			throws UnsupportedEncodingException {
		String callback = messageSource.getMessage(
				"authorize.token.callback.url",
				new Object[] { SystemConfig.getDomain(), tp.getId() },
				Locale.SIMPLIFIED_CHINESE);
		return getAuthorizeURLforCode(tp, terminal, null, null, callback);
	}

	@Override
	public String getBindAuthorizeURLforCode(Thirdparty tp, Terminal terminal)
			throws UnsupportedEncodingException {
		String callback = messageSource.getMessage(
				"authorize.bind.callback.url",
				new Object[] { SystemConfig.getDomain(), tp.getId() },
				Locale.SIMPLIFIED_CHINESE);
		return getAuthorizeURLforCode(tp, terminal, null, null, callback);
	}

	@Override
	public void expireAccess(RequestParameter requestParameter, Thirdparty tp,
			long uid) throws TokenAuthorizeException {
		Passport passport = passportService.getPassportByUid(uid);
		TpUser tpUser = tpUserService.getTpUserByUid(uid);
		if (null == tpUser || !registerService.hasAccount(passport)) {
			throw new TokenAuthorizeException(
					TokenAuthorizeException.USER_NOT_REQUIRE_AUTHORIZE);
		}
		AuthInfo authInfo = new AuthInfo();
		String tpIdentity = fetchTpIdentity(requestParameter, authInfo, tp);
		if (StringUtils.isEmpty(tpIdentity) || "null".equals(tpIdentity)) {
			throw new TokenAuthorizeException(
					TokenAuthorizeException.ILLEGAL_OPERATION);
		}
		if (!tpUser.getTpIdentity().equalsIgnoreCase(authInfo.getTpIdentity())) {
			// 新号授权
			if (tpUserAuthService.countUserAuth(uid) > 1) {
				// 一个平台绑定了多个产品。不能切换新号授权只能用原来的号
				throw new TokenAuthorizeException(
						TokenAuthorizeException.BIND_MULTIPLE_PRODUCT_CAN_NOT_AUTHORIZE_NEW_USER);
			}
			if (tpUserService.existTpUserByTpIdAndIdentity(tp.getId(),
					authInfo.getTpIdentity())) {
				// 新授权的号已注册过
				throw new TokenAuthorizeException(
						TokenAuthorizeException.USER_IS_EXIST);
			}
			tpUserService.updateTpIdentity(uid, authInfo.getTpIdentity());
		}
		tpUserAuthService.updateTpUserAuth(uid, tp.getId(), authInfo);
		tpUserAuthService.cacheAuthInfo(uid, authInfo);
	}

	@Override
	public void bindAccess(RequestParameter requestParameter, Thirdparty tp,
			long uid) throws TokenAuthorizeException {
		Passport passport = passportService.getPassportByUid(uid);
		TpUser tpUser = tpUserService.getTpUserByUid(uid);
		if (null != tpUser || !registerService.hasAccount(passport)) {
			throw new TokenAuthorizeException(
					TokenAuthorizeException.USER_NOT_REQUIRE_BIND);
		}
		AuthInfo authInfo = new AuthInfo();
		String tpIdentity = fetchTpIdentity(requestParameter, authInfo, tp);
		if (StringUtils.isEmpty(tpIdentity) || "null".equals(tpIdentity)) {
			throw new TokenAuthorizeException(
					TokenAuthorizeException.ILLEGAL_OPERATION);
		}
		if (tpUserService.existTpUserByTpIdAndIdentity(tp.getId(),
				authInfo.getTpIdentity())) {
			// 新授权的号已注册过
			throw new TokenAuthorizeException(
					TokenAuthorizeException.USER_IS_EXIST);
		}
		tpUserService.registerTpUser(tp, authInfo.getTpIdentity(), passport);
		tpUserAuthService.updateTpUserAuth(uid, tp.getId(), authInfo);
		tpUserAuthService.cacheAuthInfo(uid, authInfo);
	}

	protected String buildAuthorizeURLParams(String callbackUrl, String turnTo,
			String incode) {
		StringBuilder redirectURL = new StringBuilder(callbackUrl);
		if (StringUtils.isNotEmpty(turnTo)) {
			redirectURL.append("?turnTo=").append(turnTo);
		}
		if (StringUtils.isNotEmpty(incode)) {
			redirectURL.append(StringUtils.isNotEmpty(turnTo) ? "&" : "?");
			redirectURL.append("incode=").append(incode);
		}
		return redirectURL.toString();
	}

	protected abstract Profile convertToProfile(
			RequestParameter requestParameter, AuthInfo authInfo,
			String thirdpartyIdentity);

	protected abstract String fetchTpIdentity(
			RequestParameter requestParameter, AuthInfo authInfo, Thirdparty tp);

	/**
	 * 获取授权地址
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected abstract String getAuthorizeURLforCode(Thirdparty tp,
			Terminal terminal, String turnTo, String incode, String callback)
			throws UnsupportedEncodingException;

	/**
	 * 注册成功后调用
	 * 
	 * @param tp
	 * @param authInfo
	 * @param deviceName
	 */
	protected abstract void registerSucesssAfter(Thirdparty tp,
			AuthInfo authInfo, DeviceName deviceName);

	protected String getMessage(String name) {
		return messageSource.getMessage(name, null, Locale.SIMPLIFIED_CHINESE);
	}
}
