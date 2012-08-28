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
import com.juzhai.platform.bean.UserStatus;
import com.juzhai.platform.service.ISynchronizeService;

@Service
public class SynchronizeService implements ISynchronizeService,
		BeanFactoryAware {
	private final Log log = LogFactory.getLog(SynchronizeService.class);
	@Autowired
	private BeanFactory beanFactory;

	private ISynchronizeService getSynchronizeServiceBean(String tpName,
			String jionType) {
		String joinType = StringUtils.upperCase(String.valueOf(jionType
				.charAt(0))) + StringUtils.substring(jionType, 1);
		String beanName = tpName + joinType + this.getClass().getSimpleName();
		ISynchronizeService synchronizeService = (ISynchronizeService) beanFactory
				.getBean(beanName);
		if (log.isDebugEnabled()) {
			log.debug("get bean" + beanName + ".");
		}
		return synchronizeService;
	}

	@Override
	public List<UserStatus> listStatus(AuthInfo authInfo, long fuid, int size) {
		if (authInfo == null) {
			return Collections.emptyList();
		}
		ISynchronizeService service = getSynchronizeServiceBean(
				authInfo.getThirdpartyName(), authInfo.getJoinType());
		if (null == service) {
			return Collections.emptyList();
		}
		return service.listStatus(authInfo, fuid, size);
	}

	// private ISynchronizeService getSynchronizeService(long uid, long fuid,
	// long tpId) {
	// String tpName = null;
	// String joinType = JoinTypeEnum.CONNECT.getName();
	// if (tpId <= 0) {
	// TpUser tpUser = tpUserService.getTpUserByUid(fuid);
	// if (null == tpUser) {
	// return null;
	// }
	// tpName = tpUser.getTpName();
	// } else {
	// Thirdparty thirdparty = InitData.TP_MAP.get(tpId);
	// if (null == thirdparty) {
	// return null;
	// }
	// tpName = thirdparty.getName();
	// joinType = thirdparty.getJoinType();
	// }
	// ISynchronizeService service = getSynchronizeServiceBean(tpName,
	// joinType);
	// if (null == service) {
	// return null;
	// }
	// return service;
	// }

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;

	}

	@Override
	public void sendMessage(AuthInfo authInfo, String title, String text,
			String link, byte[] image, String imageUrl) {
		if (authInfo == null) {
			return;
		}
		ISynchronizeService synchronizeService = getSynchronizeServiceBean(
				authInfo.getThirdpartyName(), authInfo.getJoinType());
		if (synchronizeService != null) {
			synchronizeService.sendMessage(authInfo, title, text, link, image,
					imageUrl);
		}
	}

	@Override
	public void inviteMessage(AuthInfo authInfo, String text, byte[] image,
			List<String> fuids) {
		if (authInfo == null) {
			return;
		}
		ISynchronizeService synchronizeService = getSynchronizeServiceBean(
				authInfo.getThirdpartyName(), authInfo.getJoinType());
		if (synchronizeService != null) {
			synchronizeService.inviteMessage(authInfo, text, image, fuids);
		}
	}

	@Override
	public void notifyMessage(AuthInfo authInfo, String[] fuids, String text) {
		if (authInfo == null) {
			return;
		}
		ISynchronizeService synchronizeService = getSynchronizeServiceBean(
				authInfo.getThirdpartyName(), authInfo.getJoinType());
		if (synchronizeService != null) {
			synchronizeService.notifyMessage(authInfo, fuids, text);
		}
	}
}
