package com.juzhai.platform.service.impl;

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
import com.juzhai.passport.bean.TpFriend;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.platform.service.IUserService;
@Service
public class UserService implements IUserService , BeanFactoryAware{
	@Autowired
	private BeanFactory beanFactory;
	private static final Log log = LogFactory.getLog(UserService.class);
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory=beanFactory;
	}
	private IUserService getUserServiceBean(String tpName,
			String jionType) {
		String joinType = StringUtils.upperCase(String.valueOf(jionType
				.charAt(0))) + StringUtils.substring(jionType, 1);
		String beanName = tpName + joinType +this.getClass().getSimpleName();
		IUserService userSerivce = (IUserService) beanFactory
				.getBean(beanName);
		if (log.isDebugEnabled()) {
			log.debug("get bean" + beanName + ".");
		}
		return userSerivce;
	}
	@Override
	public long access(HttpServletRequest request,
			HttpServletResponse response, AuthInfo authInfo, Thirdparty tp) {
		return getUserServiceBean(tp).access(request, response, authInfo, tp);
	}
	@Override
	public List<TpFriend> getAllFriends(AuthInfo authInfo) {
		return getUserServiceBean(authInfo.getThirdpartyName(),
				authInfo.getJoinType()).getAllFriends(authInfo);
	}
	@Override
	public List<String> getAppFriends(AuthInfo authInfo) {
		return  getUserServiceBean(authInfo.getThirdpartyName(),
				authInfo.getJoinType()).getAppFriends(authInfo);
	}
	private IUserService getUserServiceBean(Thirdparty tp) {
		return getUserServiceBean(tp.getName(), tp.getJoinType());
	}
	
}
