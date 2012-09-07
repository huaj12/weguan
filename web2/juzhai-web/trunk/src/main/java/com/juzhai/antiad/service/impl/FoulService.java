package com.juzhai.antiad.service.impl;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.antiad.service.IFoulService;
import com.juzhai.core.bean.Function;
import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.platform.service.impl.SynchronizeService;

@Service
public class FoulService implements IFoulService, BeanFactoryAware {
	private final Log log = LogFactory.getLog(SynchronizeService.class);
	@Autowired
	private BeanFactory beanFactory;
	@Autowired
	private MemcachedClient memcachedClient;

	private IFoulService getFoulServiceBean(Function function) {
		String beanName = function.getType() + this.getClass().getSimpleName();
		IFoulService foulService = (IFoulService) beanFactory.getBean(beanName);
		if (log.isDebugEnabled()) {
			log.debug("get bean" + beanName + ".");
		}
		return foulService;
	}

	@Override
	public void foul(UserContext context, long targetUid, String content,
			Function function) {
		getFoulServiceBean(function)
				.foul(context, targetUid, content, function);

	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;

	}

	@Override
	public int getUserFoul(long uid) {
		Integer uidCount = 0;
		String foulUserKey = MemcachedKeyGenerator.genFoulUserCountKey(uid);
		try {
			uidCount = memcachedClient.get(foulUserKey);
		} catch (Exception e) {
			log.error("getUserFoul get foul count is error");
		}
		return uidCount == null ? 0 : uidCount;
	}

	@Override
	public int getIpFoul(String ip) {
		Integer ipCount = 0;
		String foulIpKey = MemcachedKeyGenerator.genFoulIpCountKey(ip);
		try {
			ipCount = memcachedClient.get(foulIpKey);
		} catch (Exception e) {
			log.error(" getIpFoul foul count is error");
		}
		return ipCount == null ? 0 : ipCount;
	}

	@Override
	public void resetFoul(long uid) {
		String foulUserKey = MemcachedKeyGenerator.genFoulUserCountKey(uid);
		try {
			memcachedClient.delete(foulUserKey);
		} catch (Exception e) {
			log.error("resetFoul  is error");
		}
	}

	@Override
	public void resetFoul(String ip) {
		String foulIpKey = MemcachedKeyGenerator.genFoulIpCountKey(ip);
		try {
			memcachedClient.delete(foulIpKey);
		} catch (Exception e) {
			log.error("resetFoul  is error");
		}

	}

}
