package com.juzhai.msg.schedule;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.juzhai.account.service.IAccountService;
import com.juzhai.app.service.IAppService;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.msg.schedule.view.LazyKeyView;
import com.juzhai.msg.service.ISendAppMsgService;
import com.juzhai.msg.task.SendSysMsgTask;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ITpUserService;

@Deprecated
public class LazyMessageHandler extends AbstractScheduleHandler {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private RedisTemplate<String, String> lazyKeyredisTemplate;
	@Autowired
	private ISendAppMsgService sendAppMsgService;
	@Autowired
	private ITpUserService tpUserService;

	@Override
	protected void doHandle() {
		Set<String> keys = lazyKeyredisTemplate.opsForSet().members(
				RedisKeyGenerator.genLazyMsgKey());
		lazyKeyredisTemplate.delete(RedisKeyGenerator.genLazyMsgKey());
		for (String key : keys) {
			Long count = redisTemplate.opsForValue().increment(key, 0);
			redisTemplate.delete(key);
			LazyKeyView lazyKeyView = getLazyKeyView(key);
			if (lazyKeyView != null) {
				TpUser tpUser = tpUserService.getTpUserByUid(lazyKeyView
						.getReceiverId());
				sendAppMsgService.threadSendAppMsg(tpUser,
						lazyKeyView.getSendId(), lazyKeyView.getType(), 0);
			}
		}
	}

	public LazyKeyView getLazyKeyView(String key) {
		try {
			String s[] = key.split("\\.");
			return new LazyKeyView(Long.valueOf(s[0]), Long.valueOf(s[1]),
					MsgType.valueOf(s[2]));
		} catch (Exception e) {
			log.error("getLazyKeyView is error", e);
			return null;
		}
	}

}
