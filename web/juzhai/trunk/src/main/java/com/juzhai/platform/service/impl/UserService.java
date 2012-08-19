package com.juzhai.platform.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.DeviceName;
import com.juzhai.passport.exception.TokenAuthorizeException;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.platform.bean.Terminal;
import com.juzhai.platform.service.IUserService;

@Service
public class UserService implements IUserService, BeanFactoryAware {
	@Autowired
	private BeanFactory beanFactory;
	private static final Log log = LogFactory.getLog(UserService.class);

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	private IUserService getUserServiceBean(String tpName, String jionType) {
		String joinType = StringUtils.upperCase(String.valueOf(jionType
				.charAt(0))) + StringUtils.substring(jionType, 1);
		String beanName = tpName + joinType + this.getClass().getSimpleName();
		IUserService userSerivce = (IUserService) beanFactory.getBean(beanName);
		if (log.isDebugEnabled()) {
			log.debug("get bean" + beanName + ".");
		}
		return userSerivce;
	}

	@Override
	public long access(HttpServletRequest request,
			HttpServletResponse response, AuthInfo authInfo, Thirdparty tp,
			long inviterUid, DeviceName deviceName) {
		return getUserServiceBean(tp).access(request, response, authInfo, tp,
				inviterUid, deviceName);
	}

	private IUserService getUserServiceBean(Thirdparty tp) {
		return getUserServiceBean(tp.getName(), tp.getJoinType());
	}

	@Override
	public String getLoginAuthorizeURLforCode(HttpServletRequest request,
			HttpServletResponse response, Thirdparty tp, Terminal terminal,
			String turnTo, String incode) throws UnsupportedEncodingException {
		return getUserServiceBean(tp).getLoginAuthorizeURLforCode(request,
				response, tp, terminal, turnTo, incode);
	}

	@Override
	public List<String> getUserNames(AuthInfo authInfo, List<String> fuids) {
		return getUserServiceBean(authInfo.getThirdpartyName(),
				authInfo.getJoinType()).getUserNames(authInfo, fuids);
	}

	@Override
	public String getExpiredAuthorizeURLforCode(HttpServletRequest request,
			HttpServletResponse response, Thirdparty tp, Terminal terminal,
			String turnTo, String incode) throws UnsupportedEncodingException {
		return getUserServiceBean(tp).getExpiredAuthorizeURLforCode(request,
				response, tp, terminal, turnTo, incode);
	}

	@Override
	public void expireAccess(HttpServletRequest request, Thirdparty tp,
			long uid, long userTpId) throws TokenAuthorizeException {
		getUserServiceBean(tp).expireAccess(request, tp, uid, userTpId);
	}
}
