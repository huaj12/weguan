package com.juzhai.platform.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.platform.bean.UserWeibo;
import com.juzhai.platform.service.IDataService;

@Service
public class DataService implements IDataService, BeanFactoryAware {
	@Autowired
	private BeanFactory beanFactory;
	@Autowired
	private ITpUserService tpUserService;

	private static final Log log = LogFactory.getLog(DataService.class);

	private IDataService getDataServiceBean(String tpName, String jionType) {
		String joinType = StringUtils.upperCase(String.valueOf(jionType
				.charAt(0))) + StringUtils.substring(jionType, 1);
		String beanName = tpName + joinType + this.getClass().getSimpleName();
		IDataService dataService = (IDataService) beanFactory.getBean(beanName);
		if (log.isDebugEnabled()) {
			log.debug("get bean" + beanName + ".");
		}
		return dataService;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;

	}

	@Override
	public List<UserWeibo> listWeibo(long uid, long fuid, long tpId) {
		String tpName = null;
		String joinType = JoinTypeEnum.CONNECT.getName();
		if (tpId <= 0) {
			TpUser tpUser = tpUserService.getTpUserByUid(fuid);
			if (null == tpUser) {
				return null;
			}
			tpName = tpUser.getTpName();
		} else {
			Thirdparty thirdparty = InitData.TP_MAP.get(tpId);
			if (null == thirdparty) {
				return null;
			}
			tpName = thirdparty.getName();
			joinType = thirdparty.getJoinType();
		}
		IDataService service = getDataServiceBean(tpName, joinType);
		if (null == service) {
			return null;
		}
		return service.listWeibo(uid, fuid, tpId);
	}

	@Override
	public List<UserWeibo> refreshListWeibo(long uid, long fuid, long tpId) {
		String tpName = null;
		String joinType = JoinTypeEnum.CONNECT.getName();
		if (tpId <= 0) {
			TpUser tpUser = tpUserService.getTpUserByUid(fuid);
			if (null == tpUser) {
				return null;
			}
			tpName = tpUser.getTpName();
		} else {
			Thirdparty thirdparty = InitData.TP_MAP.get(tpId);
			if (null == thirdparty) {
				return null;
			}
			tpName = thirdparty.getName();
			joinType = thirdparty.getJoinType();
		}
		IDataService service = getDataServiceBean(tpName, joinType);
		if (null == service) {
			return null;
		}
		return service.refreshListWeibo(uid, fuid, tpId);
	}
}
