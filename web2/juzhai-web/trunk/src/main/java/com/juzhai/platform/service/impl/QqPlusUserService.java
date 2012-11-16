package com.juzhai.platform.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.juzhai.common.util.IOSEmojiUtil;
import com.juzhai.core.bean.DeviceName;
import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.core.web.bean.RequestParameter;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.platform.bean.Terminal;
import com.qplus.QOpenBean;
import com.qplus.QOpenResult;
import com.qplus.QOpenService;

@Service
public class QqPlusUserService extends AbstractUserService {
	private final Log log = LogFactory.getLog(getClass());
	@Value(value = "${nickname.length.max}")
	private int nicknameLengthMax;

	@Override
	public String getAuthorizeURLforCode(Thirdparty tp, Terminal terminal,
			String turnTo, String incode, String callbackUrl)
			throws UnsupportedEncodingException {
		return null;
	}

	@Override
	public List<String> getUserNames(AuthInfo authInfo, List<String> fuids) {
		return Collections.emptyList();
	}

	@Override
	protected Profile convertToProfile(RequestParameter requestParameter,
			AuthInfo authInfo, String thirdpartyIdentity) {
		try {
			QOpenService service = QOpenService.createInstance(
					Integer.parseInt(authInfo.getAppKey()),
					authInfo.getAppSecret());
			QOpenBean bean = new QOpenBean(authInfo.getTpIdentity(),
					authInfo.getToken(), requestParameter.getIp());
			QOpenResult result = service.getUserInfo(bean);
			if (result.isSuccess()) {
				Profile profile = new Profile();
				String nickname = null;
				if (StringUtils.isEmpty(result.getValue("info.nick"))) {
					nickname = authInfo.getTpIdentity();
				} else {
					nickname = result.getValue("info.nick");
				}
				if (StringUtils.isNotEmpty(nickname)) {
					profile.setNickname(TextTruncateUtil.truncate(
							IOSEmojiUtil.removeUtf8mb4Char(HtmlUtils
									.htmlUnescape(nickname)),
							nicknameLengthMax, StringUtils.EMPTY));
				}
				// 只有小图而且url超级长不存了。
				// profile.setNewLogoPic(result.getValue("info.face"));
				profile.setLogoVerifyState(LogoVerifyState.NONE.getType());
				int gender = 1;
				try {
					gender = result.getIntValue("info.gender");
				} catch (Exception e) {
				}
				if (1 == gender) {
					profile.setGender(1);
				} else {
					profile.setGender(0);
				}
				return profile;
			} else {
				log.error("QQ Plus getUserInfo is error result:" + result);
				return null;
			}
		} catch (Exception e) {
			log.error("QQ Plus convertToProfile is error." + e.getMessage());
			return null;
		}
	}

	@Override
	protected String fetchTpIdentity(RequestParameter requestParameter,
			AuthInfo authInfo, Thirdparty tp) {
		if (!checkAuthInfo(requestParameter, authInfo, tp)) {
			log.error("QQ Plus checkAuthInfo is error.");
			return null;
		}
		String uid = requestParameter.get("app_openid");
		String appOpenKey = requestParameter.get("app_openkey");
		authInfo.setThirdparty(tp);
		authInfo.setTpIdentity(uid);
		authInfo.setToken(appOpenKey);
		return uid;
	}

	private boolean checkAuthInfo(RequestParameter requestParameter,
			AuthInfo authInfo, Thirdparty tp) {
		QOpenService service = QOpenService.createInstance(
				Integer.parseInt(tp.getAppId()), tp.getAppSecret());
		return service.checkParamsSig(requestParameter.getQueryString());
	}

	@Override
	protected void registerSucesssAfter(Thirdparty tp, AuthInfo authInfo,
			DeviceName deviceName) {

	}
}
