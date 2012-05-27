package com.juzhai.spider.share.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.platform.service.impl.SynchronizeService;
import com.juzhai.post.controller.form.RawIdeaForm;
import com.juzhai.spider.bean.Domain;
import com.juzhai.spider.share.exception.SpiderIdeaException;
import com.juzhai.spider.share.service.ISpiderIdeaService;

@Service
public class SpiderIdeaService implements ISpiderIdeaService, BeanFactoryAware {

	private final Log log = LogFactory.getLog(SynchronizeService.class);
	@Autowired
	private BeanFactory beanFactory;

	private ISpiderIdeaService getSpiderIdeaServiceBean(String url)
			throws SpiderIdeaException {

		if (StringUtils.isEmpty(url)) {
			throw new SpiderIdeaException(
					SpiderIdeaException.SPIDER_IDEA_URL_IS_NULL);
		}
		if (url.indexOf("http://") == -1) {
			url = "http://" + url;
		}
		String joinType = null;
		for (Domain domain : Domain.values()) {
			if (url.indexOf(domain.getUrl()) != -1) {
				joinType = domain.getName();
				break;
			}
		}
		if (StringUtils.isEmpty(joinType)) {
			// 暂不支持改网站
			throw new SpiderIdeaException(
					SpiderIdeaException.SPIDER_IDEA_URL_IS_NOT_EXIST);
		}
		String beanName = joinType + this.getClass().getSimpleName();
		ISpiderIdeaService spiderIdeaService = (ISpiderIdeaService) beanFactory
				.getBean(beanName);
		if (log.isDebugEnabled()) {
			log.debug("get bean" + beanName + ".");
		}
		return spiderIdeaService;
	}

	@Override
	public RawIdeaForm crawl(String url) throws SpiderIdeaException {
		return getSpiderIdeaServiceBean(url).crawl(url);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;

	}

}
