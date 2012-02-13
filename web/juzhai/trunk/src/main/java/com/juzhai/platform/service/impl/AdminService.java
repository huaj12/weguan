package com.juzhai.platform.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.platform.service.IAdminService;

@Service
public class AdminService implements IAdminService, BeanFactoryAware {
	@Autowired
	private BeanFactory beanFactory;
	@Autowired
	private ITpUserAuthService tpUserAuthService;

	private final Log log = LogFactory.getLog(getClass());

	private IAdminService getAdminServiceBean(String tpName, String jionType) {
		String joinType = StringUtils.upperCase(String.valueOf(jionType
				.charAt(0))) + StringUtils.substring(jionType, 1);
		String beanName = tpName + joinType + this.getClass().getSimpleName();
		IAdminService adminService = (IAdminService) beanFactory
				.getBean(beanName);
		if (log.isDebugEnabled()) {
			log.debug("get bean" + beanName + ".");
		}
		return adminService;
	}

	@Override
	public boolean isAllocation(long uid, long tpId) {
		AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
		return getAdminServiceBean(authInfo.getThirdpartyName(),
				authInfo.getJoinType()).isAllocation(uid, tpId);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;

	}

}
