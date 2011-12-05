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

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.platform.service.IMessageService;

@Service
public class MessageService implements IMessageService, BeanFactoryAware {
	@Autowired
	private BeanFactory beanFactory;
	private static final Log log = LogFactory.getLog(MessageService.class);

	private IMessageService getMessageServiceBean(String tpName, String jionType) {
		String joinType = StringUtils.upperCase(String.valueOf(jionType
				.charAt(0))) + StringUtils.substring(jionType, 1);
		String beanName = tpName + joinType + this.getClass().getSimpleName();
		IMessageService messageService = (IMessageService) beanFactory
				.getBean(beanName);
		if (log.isDebugEnabled()) {
			log.debug("get bean" + beanName + ".");
		}
		return messageService;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public boolean sendSysMessage(List<String> fuids, String linktext,
			String link, String word, String text, String picurl,
			AuthInfo authInfo) {
		return getMessageServiceBean(authInfo.getThirdpartyName(),
				authInfo.getJoinType()).sendSysMessage(fuids, linktext, link,
				word, text, picurl, authInfo);
	}

	@Override
	public boolean sendMessage(long sendId, String fuids, String fname,
			String content, AuthInfo authInfo, long actId, String link,
			String typeWeibo, String typeComment) {
		return getMessageServiceBean(authInfo.getThirdpartyName(),
				authInfo.getJoinType()).sendMessage(sendId, fuids, fname,
				content, authInfo, actId, link, typeWeibo, typeComment);
	}

	@Override
	public boolean sendFeed(String linktext, String link, String word,
			String text, String picurl, AuthInfo authInfo, String name,
			long actId) {
		return getMessageServiceBean(authInfo.getThirdpartyName(),
				authInfo.getJoinType()).sendFeed(linktext, link, word, text,
				picurl, authInfo, name, actId);
	}

	@Override
	public boolean sendQuestionMessage(AuthInfo authInfo, List<String> fuids,
			long sendId, String linktext, String link, String word, String text) {
		return getMessageServiceBean(authInfo.getThirdpartyName(),
				authInfo.getJoinType()).sendQuestionMessage(authInfo, fuids,
				sendId, linktext, link, word, text);
	}

	@Override
	public boolean sendMatchMessage(long sendId, List<String> fuids,
			String linktext, String link, String word, String text,
			String picurl, AuthInfo authInfo, long actId) {
		return getMessageServiceBean(authInfo.getThirdpartyName(),
				authInfo.getJoinType()).sendMatchMessage(sendId, fuids,
				linktext, link, word, text, picurl, authInfo, actId);
	}

}
