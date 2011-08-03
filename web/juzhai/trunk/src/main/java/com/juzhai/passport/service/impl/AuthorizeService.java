package com.juzhai.passport.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.IAuthorizeService;

/**
 * @author wujiajun Created on 2011-2-15
 */
@Service
public class AuthorizeService implements IAuthorizeService, BeanFactoryAware {
	private static final Log log = LogFactory.getLog(AuthorizeService.class);
	private BeanFactory beanFactory;

	// @Override
	// public AuthInfo requestAuthInfo(Thirdparty tp, AuthorizeInfo
	// authorizeInfo) {
	// IAuthorizeService authorizeService = getAuthorizeServiceBean(tp);
	// return authorizeService.requestAuthInfo(tp, authorizeInfo);
	// }

	@Override
	public long access(HttpServletRequest request,
			HttpServletResponse response, AuthInfo authInfo, Thirdparty tp) {
		IAuthorizeService authorizeService = getAuthorizeServiceBean(tp);
		return authorizeService.access(request, response, authInfo, tp);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	private IAuthorizeService getAuthorizeServiceBean(Thirdparty tp) {
		String joinType = StringUtils.upperCase(String.valueOf(tp.getJoinType()
				.charAt(0))) + StringUtils.substring(tp.getJoinType(), 1);
		String beanName = tp.getName() + joinType
				+ this.getClass().getSimpleName();
		IAuthorizeService authorizeService = (IAuthorizeService) beanFactory
				.getBean(beanName);
		if (log.isDebugEnabled()) {
			log.debug("get bean" + beanName + ".");
		}
		return authorizeService;
	}
}
