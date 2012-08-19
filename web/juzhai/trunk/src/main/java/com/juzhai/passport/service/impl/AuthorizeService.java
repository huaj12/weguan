package com.juzhai.passport.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.exception.TokenAuthorizeException;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.model.TpUserAuth;
import com.juzhai.passport.service.IAuthorizeService;
import com.juzhai.passport.service.IPassportService;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.platform.service.IUserService;

@Service
public class AuthorizeService implements IAuthorizeService {
	@Autowired
	private IPassportService passportService;
	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private IUserService userService;

	@Override
	public void tokenAuthorize(HttpServletRequest request, long uid,
			long userTpId, long tpId) throws TokenAuthorizeException {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if (null == tp) {
			throw new TokenAuthorizeException(
					TokenAuthorizeException.ILLEGAL_OPERATION);
		}
		if (userTpId != tpId) {
			throw new TokenAuthorizeException(
					TokenAuthorizeException.ILLEGAL_OPERATION);
		}
		Passport passport = passportService.getPassportByUid(uid);
		TpUser tpUser = tpUserService.getTpUserByUid(uid);
		if (null == tpUser || null == passport
				|| StringUtils.isEmpty(passport.getLoginName())
				|| StringUtils.startsWith(passport.getLoginName(), "@")) {
			throw new TokenAuthorizeException(
					TokenAuthorizeException.USER_NOT_REQUIRE_AUTHORIZE);
		}
		AuthInfo authInfo = userService.getAuthInfo(request, tp);
		if (authInfo == null) {
			throw new TokenAuthorizeException(
					TokenAuthorizeException.ILLEGAL_OPERATION);
		}
		if (!tpUser.getTpIdentity().equalsIgnoreCase(authInfo.getTpIdentity())) {
			// 新号授权
			List<TpUserAuth> userAuthList = tpUserAuthService.listUserAuth(uid);
			if (CollectionUtils.isEmpty(userAuthList)) {
				throw new TokenAuthorizeException(
						TokenAuthorizeException.ILLEGAL_OPERATION);
			}
			if (userAuthList.size() > 1) {
				// 一个平台只能绑定一款产品
				throw new TokenAuthorizeException(
						TokenAuthorizeException.BIND_MULTIPLE_PRODUCT_CAN_NOT_AUTHORIZE_NEW_USER);
			}
			if (null != tpUserService.getTpUserByTpIdAndIdentity(tpId,
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

}
