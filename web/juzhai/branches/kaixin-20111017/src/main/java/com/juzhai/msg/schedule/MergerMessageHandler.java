package com.juzhai.msg.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;

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
import com.juzhai.passport.service.IUserSetupService;

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
	private IMsgService<MergerActMsg> msgService;
	@Autowired
	private IUserSetupService userSetupService;

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
			Set<TypedTuple<Long>> typedTuples = redisLongTemplate.opsForZSet()
					.reverseRangeWithScores(key, 0, count);
			redisTemplate.delete(key);
			LazyKeyView lazyKeyView = getLazyKeyView(key);
			MergerActMsg merge = new MergerActMsg();
			merge.setUid(lazyKeyView.getSendId());
			List<ActMsg> actMsgs = new ArrayList<ActMsg>();
			for (TypedTuple<Long> typeTuple : typedTuples) {
				ActMsg msg = new ActMsg(typeTuple.getValue(),
						lazyKeyView.getSendId(), lazyKeyView.getType());
				if (actMsgs.size() == 0) {
					long date = new Double(typeTuple.getScore()).longValue();
					merge.setDate(new Date(date));
				}
				actMsgs.add(msg);
			}
			merge.setMsgs(actMsgs);
			merge.setType(lazyKeyView.getType());
			msgService.sendMsg(lazyKeyView.getReceiverId(), merge);
			// 判断用户是否发送消息
			if (userSetupService.isTpAdvise(lazyKeyView.getSendId())
					&& sendAppMsgService.checkTpMsgLimitAndAddCnt(
							lazyKeyView.getReceiverId(), merge.getType())) {
				TpUser tpUser = tpUserService.getTpUserByUid(lazyKeyView
						.getReceiverId());
				sendAppMsgService.threadSendAppMsg(tpUser,
						lazyKeyView.getSendId(), lazyKeyView.getType(), count);
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
