package com.juzhai.platform.service.impl;

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

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.TpFriend;
import com.juzhai.platform.service.IRelationshipService;

@Service
public class RelationshipService implements IRelationshipService,
		BeanFactoryAware {
	@Autowired
	private BeanFactory beanFactory;
	private static final Log log = LogFactory.getLog(UserService.class);

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public List<TpFriend> getAllFriends(AuthInfo authInfo) {
		if (authInfo == null) {
			return Collections.emptyList();
		}
		IRelationshipService relationshipService = getRelationshipSerivceBean(
				authInfo.getThirdpartyName(), authInfo.getJoinType());
		if (relationshipService == null) {
			return Collections.emptyList();
		}
		return relationshipService.getAllFriends(authInfo);
	}

	@Override
	public List<String> getAppFriends(AuthInfo authInfo) {
		if (authInfo == null) {
			return Collections.emptyList();
		}
		IRelationshipService relationshipService = getRelationshipSerivceBean(
				authInfo.getThirdpartyName(), authInfo.getJoinType());
		if (relationshipService == null) {
			return Collections.emptyList();
		}
		return relationshipService.getAppFriends(authInfo);
	}

	@Override
	public List<String> getInstallFollows(AuthInfo authInfo) {
		if (authInfo == null) {
			return Collections.emptyList();
		}
		IRelationshipService relationshipService = getRelationshipSerivceBean(
				authInfo.getThirdpartyName(), authInfo.getJoinType());
		if (relationshipService == null) {
			return Collections.emptyList();
		}
		return relationshipService.getInstallFollows(authInfo);
	}

	private IRelationshipService getRelationshipSerivceBean(String tpName,
			String jionType) {
		String joinType = StringUtils.upperCase(String.valueOf(jionType
				.charAt(0))) + StringUtils.substring(jionType, 1);
		String beanName = tpName + joinType + this.getClass().getSimpleName();
		IRelationshipService relationshipSerivce = (IRelationshipService) beanFactory
				.getBean(beanName);
		if (log.isDebugEnabled()) {
			log.debug("get bean" + beanName + ".");
		}
		return relationshipSerivce;
	}

}
