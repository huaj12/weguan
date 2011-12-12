package com.juzhai.platform.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.passport.model.Thirdparty;
import com.juzhai.platform.service.IAuthorizeURLService;
@Service
public class AuthorizeURLService implements IAuthorizeURLService{
	@Autowired
	private BeanFactory beanFactory;
	private static final Log log = LogFactory.getLog(AuthorizeURLService.class);

	@Override
	public String getAuthorizeURLforCode(Thirdparty tp) {
		return getUserServiceBean(tp.getName(),tp.getJoinType()).getAuthorizeURLforCode(tp);
	}
	private IAuthorizeURLService getUserServiceBean(String tpName, String jionType) {
		String joinType = StringUtils.upperCase(String.valueOf(jionType
				.charAt(0))) + StringUtils.substring(jionType, 1);
		String beanName = tpName + joinType + this.getClass().getSimpleName();
		IAuthorizeURLService authorizeURLService = (IAuthorizeURLService) beanFactory.getBean(beanName);
		if (log.isDebugEnabled()) {
			log.debug("get bean" + beanName + ".");
		}
		return authorizeURLService;
	}
}
