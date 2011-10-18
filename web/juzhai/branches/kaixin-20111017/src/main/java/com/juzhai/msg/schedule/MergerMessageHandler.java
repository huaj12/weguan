package com.juzhai.msg.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActService;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.msg.bean.MergerActMsg;
import com.juzhai.msg.schedule.view.LazyKeyView;
import com.juzhai.msg.service.IMsgService;
import com.juzhai.msg.service.ISendAppMsgService;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ITpUserService;

@Component
public class MergerMessageHandler extends AbstractScheduleHandler {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private RedisTemplate<String, Long> redisLongTemplate;
	@Autowired
	private ISendAppMsgService sendAppMsgService;
	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private IActService actService;
	@Autowired
	private IMsgService<MergerActMsg<ActMsg>> msgService;

	@Override
	protected void doHandle() {
		Set<String> keys = redisTemplate.opsForSet().members(
				RedisKeyGenerator.genMergerMsgKey());
		redisTemplate.delete(RedisKeyGenerator.genMergerMsgKey());
		for (String key : keys) {
			long count = redisTemplate.opsForZSet().size(key);
			if (count == 0) {
				continue;
			}
			if (count > 20) {
				count = 20;
			}
			Set<Long> actIds = redisLongTemplate.opsForZSet().reverseRange(key,
					0, count);
			redisTemplate.delete(key);
			LazyKeyView lazyKeyView = getLazyKeyView(key);
			MergerActMsg<ActMsg> merge = new MergerActMsg<ActMsg>();
			merge.setUid(lazyKeyView.getSendId());
			List<ActMsg> actMsgs = new ArrayList<ActMsg>();
			for (Long actId : actIds) {
				ActMsg msg = new ActMsg(actId, lazyKeyView.getSendId(),
						lazyKeyView.getType());
				actMsgs.add(msg);
			}
			merge.setMsgs(actMsgs);
			merge.setType(lazyKeyView.getType());
			msgService.sendMsg(lazyKeyView.getReceiverId(), merge);
			TpUser tpUser = tpUserService.getTpUserByUid(lazyKeyView
					.getReceiverId());
			sendAppMsgService.threadSendAppMsg(tpUser, lazyKeyView.getSendId(),
					lazyKeyView.getType(), count);

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
