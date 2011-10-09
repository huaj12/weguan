package com.juzhai.msg.rabbit.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.juzhai.act.service.IUserActService;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.rabbit.listener.IRabbitMessageListener;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.msg.rabbit.message.MsgMessage;
import com.juzhai.msg.service.IMsgService;
import com.juzhai.msg.service.ISendAppMsgService;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.ITpUserService;

@Component
public class ActMsgMessageListener implements
		IRabbitMessageListener<MsgMessage<ActMsg>, Object> {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IFriendService friendService;
	@Autowired
	private IMsgService<ActMsg> msgService;
	@Autowired
	private IUserActService userActService;
	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private RedisTemplate<String,Long> redisTemplate;
	@Autowired
	private RedisTemplate<String,String> redisStringTemplate;
	ISendAppMsgService sendAppMsgService;
	@Override
	public Object handleMessage(MsgMessage<ActMsg> msgMessage) {
		if (null == msgMessage) {
			log.error("MsgMessage must not bu null.");
			return null;
		}
		if (null == msgMessage.getBody()) {
			log.error("Object actMsg must not be null.");
			return null;
		}
		if (null == msgMessage.getBody().getType()) {
			log.error("ActMsg's type must not be null.");
			return null;
		}
		//接受消息如果接受者UID为0则找出同城且同兴趣的所有好友
		if(msgMessage.getReceiverId()==0&&MsgType.RECOMMEND.equals(msgMessage.getBody().getType())){
			List<Long> fuids= getPushTargets(msgMessage.getSenderId(),msgMessage.getBody().getActId());
			for(Long fuid:fuids){
				msgMessage.buildReceiverId(fuid);
				receiverActMsg(msgMessage);	
			}
		}else{
			receiverActMsg(msgMessage);	
		}
	
		return null;
	}

	private void receiverActMsg(MsgMessage<ActMsg> msgMessage) {
		try{
		TpUser tpUser=null;
		if (msgMessage.getReceiverId() > 0) {
			msgService
					.sendMsg(msgMessage.getReceiverId(), msgMessage.getBody());
			 tpUser=tpUserService.getTpUserByUid(msgMessage.getReceiverId());
		} else if (msgMessage.getReceiverTpId() > 0
				&& StringUtils.isNotEmpty(msgMessage.getReceiverIdentity())) {
			msgService.sendMsg(msgMessage.getReceiverTpId(),
					msgMessage.getReceiverIdentity(), msgMessage.getBody());
			tpUser=tpUserService.getTpUserByTpIdAndIdentity(msgMessage.getReceiverTpId(), msgMessage.getReceiverIdentity());
		}
		//如果是延迟发送则存到redis里面
		if(msgMessage.getBody().isLazy()){
			String lazyMsgKey=RedisKeyGenerator.genLazyMessageKey(msgMessage.getSenderId(),
					msgMessage.getReceiverId(),msgMessage.getBody().getType(),msgMessage.getBody().getClass().getSimpleName());
			redisTemplate.opsForValue().increment(lazyMsgKey, 1);
			//存储需要延迟发送的msgkey
			redisStringTemplate.opsForSet().add(RedisKeyGenerator.genLazyMsgKey(), lazyMsgKey);
		}else{
			//发送第三方消息
			sendAppMsgService.threadSendAppMsg(tpUser, msgMessage.getSenderId(), msgMessage.getBody().getType(), 1);
		}
		}catch (Exception e) {
			log.error("receiverActMsg is error",e);
		}	
		
	}

	/**
	 * 找出同城同兴趣的好友
	 * @param uid
	 * @param actId
	 * @return
	 */
	private List<Long> getPushTargets(long uid,long actId) {
		Set<Long> friendIds = friendService.getAppFriends(uid);
		List<Long> targets = new ArrayList<Long>();
		for (Long friendUid : friendIds) {
			if(profileService.isMaybeSameCity(uid, friendUid)!=0){
				if (friendUid != null && friendUid > 0
						&& userActService.isInterested(friendUid, actId)) {
					targets.add(friendUid);
				}
			}
		}
		return targets;
	}
}


