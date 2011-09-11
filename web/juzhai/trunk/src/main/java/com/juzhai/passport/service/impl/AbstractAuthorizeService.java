package com.juzhai.passport.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.dao.ITpUserDao;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.IAuthorizeService;
import com.juzhai.passport.service.IRegisterService;
import com.juzhai.passport.service.ITpUserAuthService;

/**
 * @author wujiajun Created on 2011-2-15
 */
public abstract class AbstractAuthorizeService implements IAuthorizeService {
	private static final Log log = LogFactory
			.getLog(AbstractAuthorizeService.class);

	@Autowired
	private ITpUserDao tpUserDao;
	@Autowired
	private IRegisterService registerService;
	@Autowired
	private ITpUserAuthService tpUserAuthService;

	@Override
	public long access(HttpServletRequest request,
			HttpServletResponse response, AuthInfo authInfo, Thirdparty tp) {
		if (authInfo == null) {
			authInfo = new AuthInfo();
		}
		String tpIdentity = fetchTpIdentity(request, authInfo, tp);
		if (StringUtils.isEmpty(tpIdentity)) {
			log.error("Fetch thirdparty identity failed.[tpName:"
					+ tp.getName() + ", joinType:" + tp.getJoinType());
			return 0L;
		}
		return completeAccessUser(request, response, authInfo, tpIdentity, tp);
	}

	private long completeAccessUser(HttpServletRequest request,
			HttpServletResponse response, AuthInfo authInfo, String tpIdentity,
			Thirdparty tp) {
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
			Profile profile = convertToProfile(request, response, authInfo,
					tpIdentity, tp);
			if (null == profile) {
				return 0;
			}
			uid = registerService.autoRegister(tp, tpIdentity, authInfo,
					profile);
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
			tpUserAuthService.saveAuthInfoToSession(request, authInfo);
		}

		return uid;
	}

	protected abstract Profile convertToProfile(HttpServletRequest request,
			HttpServletResponse response, AuthInfo authInfo,
			String thirdpartyIdentity, Thirdparty tp);

	protected abstract String fetchTpIdentity(HttpServletRequest request,
			AuthInfo authInfo, Thirdparty tp);

	// protected Map<String, String> getCookieMap(HttpServletRequest request) {
	// Cookie[] cookies = request.getCookies();
	// if (ArrayUtils.isEmpty(cookies)) {
	// return Collections.emptyMap();
	// }
	// Map<String, String> cookieMap = new HashMap<String, String>(
	// cookies.length);
	// for (Cookie cookie : cookies) {
	// cookieMap.put(cookie.getName(), cookie.getValue());
	// }
	// return cookieMap;
	// }
}
