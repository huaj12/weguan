package com.juzhai.platform.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.core.bean.DeviceName;
import com.juzhai.core.web.bean.RequestParameter;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.platform.bean.Terminal;
import com.juzhai.platform.exception.TokenAuthorizeException;
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
	public long access(RequestParameter requestParameter, AuthInfo authInfo,
			Thirdparty tp, long inviterUid, DeviceName deviceName) {
		if (tp == null) {
			return 0l;
		}
		IUserService service = getUserServiceBean(tp);
		if (service == null) {
			return 0l;
		}
		return service.access(requestParameter, authInfo, tp, inviterUid,
				deviceName);
	}

	private IUserService getUserServiceBean(Thirdparty tp) {
		return getUserServiceBean(tp.getName(), tp.getJoinType());
	}

	@Override
	public String getLoginAuthorizeURLforCode(Thirdparty tp, Terminal terminal,
			String turnTo, String incode) throws UnsupportedEncodingException {
		if (tp == null) {
			return null;
		}
		IUserService service = getUserServiceBean(tp);
		if (service == null) {
			return null;
		}
		return service
				.getLoginAuthorizeURLforCode(tp, terminal, turnTo, incode);
	}

	@Override
	public List<String> getUserNames(AuthInfo authInfo, List<String> fuids) {
		if (authInfo == null) {
			return Collections.emptyList();
		}
		IUserService service = getUserServiceBean(authInfo.getThirdpartyName(),
				authInfo.getJoinType());
		if (service == null) {
			return Collections.emptyList();
		}
		return service.getUserNames(authInfo, fuids);
	}

	@Override
	public String getExpiredAuthorizeURLforCode(Thirdparty tp, Terminal terminal)
			throws UnsupportedEncodingException {
		if (tp == null) {
			return null;
		}
		IUserService service = getUserServiceBean(tp);
		if (service == null) {
			return null;
		}
		return service.getExpiredAuthorizeURLforCode(tp, terminal);
	}

	@Override
	public void expireAccess(RequestParameter requestParameter, Thirdparty tp,
			long uid) throws TokenAuthorizeException {
		if (tp == null) {
			return;
		}
		IUserService service = getUserServiceBean(tp);
		if (service == null) {
			return;
		}
		service.expireAccess(requestParameter, tp, uid);
	}

	@Override
	public String getBindAuthorizeURLforCode(Thirdparty tp, Terminal terminal)
			throws UnsupportedEncodingException {
		if (tp == null) {
			return null;
		}
		IUserService service = getUserServiceBean(tp);
		if (service == null) {
			return null;
		}
		return service.getBindAuthorizeURLforCode(tp, terminal);
	}

	@Override
	public void bindAccess(RequestParameter requestParameter, Thirdparty tp,
			long uid) throws TokenAuthorizeException {
		if (tp == null) {
			return;
		}
		IUserService service = getUserServiceBean(tp);
		if (service != null) {
			service.bindAccess(requestParameter, tp, uid);
		}

	}

}
