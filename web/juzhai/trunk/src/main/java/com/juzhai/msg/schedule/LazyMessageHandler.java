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
import com.juzhai.msg.service.ISendAppMsgService;
import com.juzhai.msg.task.SendSysMsgTask;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ITpUserService;
@Component
public class LazyMessageHandler extends AbstractScheduleHandler  {
	@Autowired
	private RedisTemplate<String,ActMsg> redisTemplate;
	@Autowired
	private RedisTemplate<String,String> lazyKeyredisTemplate;
	@Autowired
	private ISendAppMsgService sendAppMsgService;
	@Autowired
	private ITpUserService tpUserService;
	@Override
	protected void doHandle() {
		Set<String> keys= lazyKeyredisTemplate.opsForSet().members(RedisKeyGenerator.genLazyMsgKey());
		for(String key:keys){
			//在算count时先删除lazyKey
			redisTemplate.opsForSet().remove(RedisKeyGenerator.genLazyMsgKey(),key);
			ActMsg actMsg=null;
			long count=redisTemplate.opsForList().size(key);
			if(count>0){
				for(int i=0;i<count;i++){
					//移除所有的消息
					actMsg=(ActMsg) redisTemplate.opsForList().rightPop(key);
				}
				TpUser tpUser=tpUserService.getTpUserByUid(getReceiverId(key));
				sendAppMsgService.threadSendAppMsg(tpUser,actMsg.getUid(), actMsg.getType(), count);
			}
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
