package com.juzhai.msg.schedule;

import java.util.Set;

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
import com.juzhai.msg.service.ISendAppMsgService;
import com.juzhai.msg.task.SendSysMsgTask;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ITpUserService;
@Component
public class LazyMessageHandler extends AbstractScheduleHandler  {
	@Autowired
	private RedisTemplate<String,Long> redisTemplate;
	@Autowired
	private RedisTemplate<String,String> lazyKeyredisTemplate;
	@Autowired
	private ISendAppMsgService sendAppMsgService;
	@Autowired
	private ITpUserService tpUserService;
	@Override
	protected void doHandle() {
		Set<String> keys= lazyKeyredisTemplate.opsForSet().members(RedisKeyGenerator.genLazyMsgKey());
		lazyKeyredisTemplate.delete(RedisKeyGenerator.genLazyMsgKey());
		for(String key:keys){
				Long count=redisTemplate.opsForValue().increment(key, 0);
				TpUser tpUser=tpUserService.getTpUserByUid(getReceiverId(key));
				sendAppMsgService.threadSendAppMsg(tpUser,getSendId(key), getMsgType(key), count);
		}
	}
	public long getSendId(String key){
		try{
			String s[]=key.split("\\.");
			return Long.valueOf(s[0]);
		}catch (Exception e) {
			return 0;	
		}
	}
	public MsgType getMsgType(String key){
		
		try{
			String s[]=key.split("\\.");
			return MsgType.valueOf(s[2]);
		}catch (Exception e) {
			return null;	
		}
	}
	public long getReceiverId(String key){
		try{
			String s[]=key.split("\\.");
			return Long.valueOf(s[1]);
		}catch (Exception e) {
			return 0;	
		}
	}

}
