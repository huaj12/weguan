/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.account.bean.ProfitAction;
import com.juzhai.account.service.IAccountService;
import com.juzhai.act.InitData;
import com.juzhai.act.controller.view.UserActView;
import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.mapper.UserActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.UserAct;
import com.juzhai.act.model.UserActExample;
import com.juzhai.act.rabbit.message.ActUpdateMessage;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.home.bean.ReadFeedType;
import com.juzhai.home.exception.IndexException;
import com.juzhai.home.service.IInboxService;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.service.IMsgMessageService;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.ITpUserService;

@Service
public class UserActService implements IUserActService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private IActService actService;
	@Autowired
	private UserActMapper userActMapper;
	@Autowired
	private AmqpTemplate updateActFeedRabbitTemplate;
	@Autowired
	private IFriendService friendService;
	@Autowired
	private IInboxService inboxService;
	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IMsgMessageService msgMessageService;
	@Autowired
	private IAccountService accountService;

	@Override
	public void respRandom(long uid, long actId, String tpFriendId, long tpId,
			ReadFeedType type) throws IndexException {
		if (null == type) {
			throw new IndexException("response feed invalid parameter");
		}
		TpUser tpUser = tpUserService.getTpUserByTpIdAndIdentity(tpId,
				tpFriendId);
		// 处理ActAndFriend
		dealActAndFriend(uid, actId, tpUser == null ? 0 : tpUser.getUid(),
				tpFriendId, tpId, type);
	}

	@Override
	public void respFeed(long uid, long actId, long friendId, ReadFeedType type)
			throws IndexException {
		if (null == type) {
			throw new IndexException("response feed invalid parameter");
		}
		// 验证uid的inbox中是否存在,并取出
		if (!inboxService.remove(uid, friendId, actId)) {
			throw new IndexException("response feed invalid parameter");
		}
		dealActAndFriend(uid, actId, friendId, null, 0, type);
		// 放入该放的已处理列表中
		inboxService.shiftRead(uid, friendId, actId, type);
	}

	private void dealActAndFriend(long uid, long actId, long friendId,
			String tpIdentity, long tpId, ReadFeedType type) {
		switch (type) {
		case DEPEND:
			dependToAct(uid, actId, friendId);
			break;
		case WANT:
			if (friendId > 0) {
				wantToAct(uid, actId, friendId);
			} else {
				wantToAct(uid, actId, tpIdentity, tpId);
			}
			accountService.profitPoint(uid, ProfitAction.WANT_TO);
			break;
		case NILL:
			break;
		}
	}

	private void wantToAct(long uid, long actId, long friendId) {
		try {
			useAct(uid, actId, 2, true);
		} catch (ActInputException e) {
			log.error(e.getErrorCode(), e);
		}
		friendService.incrOrDecrIntimacy(uid, friendId, 2);
	}

	private void wantToAct(long uid, long actId, String tpIdentity, long tpId) {
		try {
			useAct(uid, actId, 2, true);
		} catch (ActInputException e) {
			log.error(e.getErrorCode(), e);
		}
	}

	private void dependToAct(long uid, long actId, long friendId) {
		try {
			useAct(uid, actId, 1, true);
		} catch (ActInputException e) {
			log.error(e.getErrorCode(), e);
		}
		if (friendId > 0) {
			friendService.incrOrDecrIntimacy(uid, friendId, 1);
		}
	}

	@Override
	public void addAct(long uid, String actName, boolean isSyn)
			throws ActInputException {
		Act act = actService.getActByName(actName);
		if (null == act) {
			act = actService.createAct(uid, actName, null);
			if (null == act) {
				log.error("add act by name failed.[createUid:" + uid
						+ ", actName:" + actName + "]");
				return;
			}
		}
		addAct(uid, act.getId(), isSyn);
	}

	@Override
	public void addAct(long uid, long actId, boolean isSyn)
			throws ActInputException {
		useAct(uid, actId, 5, false);
		accountService.profitPoint(uid, ProfitAction.ADD_ACT);
	}

	private void useAct(long uid, long actId, int hotLev, boolean canRepeat)
			throws ActInputException {
		UserActExample example = new UserActExample();
		example.createCriteria().andUidEqualTo(uid).andActIdEqualTo(actId);
		List<UserAct> list = userActMapper.selectByExample(example);
		UserAct userAct = null;
		if (CollectionUtils.isEmpty(list)) {
			// insert
			userAct = new UserAct();
			userAct.setActId(actId);
			userAct.setHotLev(hotLev);
			userAct.setUid(uid);
			userAct.setCreateTime(new Date());
			userAct.setLastModifyTime(userAct.getCreateTime());
			userActMapper.insertSelective(userAct);
			// 添加人气
			actService.inOrDePopularity(actId, 1);
		} else {
			if (!canRepeat) {
				throw new ActInputException(
						ActInputException.ACT_NAME_EXISTENCE);
			}
			// update
			userAct = list.get(0);
			userAct.setHotLev(userAct.getHotLev() + hotLev);
			userAct.setLastModifyTime(new Date());
			userActMapper.updateByPrimaryKeySelective(userAct);
		}
		// save my act to redis
		redisTemplate.opsForZSet().add(
				RedisKeyGenerator.genMyActsKey(userAct.getUid()),
				userAct.getActId(), userAct.getHotLev());
		sendFeed(userAct);
		sendMsg(userAct);
	}

	private void sendFeed(UserAct userAct) {
		// push to friends
		ActUpdateMessage actUpdateMessage = new ActUpdateMessage();
		actUpdateMessage.buildSenderId(userAct.getUid()).buildBody(userAct);
		updateActFeedRabbitTemplate.convertAndSend(actUpdateMessage);
	}

	private void sendMsg(UserAct userAct) {
		msgMessageService.sendActMsg(userAct.getUid(), 0L,
				new ActMsg(userAct.getActId(), userAct.getUid(),
						ActMsg.MsgType.RECOMMEND));
	}

	@Override
	public List<Long> getUserActIdsFromCache(long uid, int count) {
		if (count <= 0) {
			return Collections.emptyList();
		}
		if (count == Integer.MAX_VALUE) {
			count = 0;
		}
		Set<Long> actIds = redisTemplate.opsForZSet().reverseRange(
				RedisKeyGenerator.genMyActsKey(uid), 0, count - 1);
		if (CollectionUtils.isEmpty(actIds)) {
			return Collections.emptyList();
		}
		return new ArrayList<Long>(actIds);
	}

	@Override
	public List<Act> getUserActFromCache(long uid, int count) {
		List<Long> actIdList = getUserActIdsFromCache(uid, count);
		List<Act> actList = new ArrayList<Act>(actIdList.size());
		for (long actId : actIdList) {
			Act act = InitData.ACT_MAP.get(actId);
			if (null != act) {
				actList.add(act);
			}
		}
		return actList;
	}

	@Override
	public void removeAct(long uid, long actId) {
		UserActExample example = new UserActExample();
		example.createCriteria().andUidEqualTo(uid).andActIdEqualTo(actId);
		if (userActMapper.deleteByExample(example) >= 1) {
			// 删除redis里的数据
			redisTemplate.opsForZSet().remove(
					RedisKeyGenerator.genMyActsKey(uid), actId);
			// 更新Act的使用人数
			actService.inOrDePopularity(actId, -1);
		}
	}

	@Override
	public List<UserActView> pageUserActView(long uid, int start, int maxRows) {
		UserActExample example = new UserActExample();
		example.createCriteria().andUidEqualTo(uid);
		example.setOrderByClause("create_time desc, id desc");
		example.setLimit(new Limit(start, maxRows));
		List<UserAct> list = userActMapper.selectByExample(example);
		List<UserActView> returnList = new ArrayList<UserActView>();
		ProfileCache profile = profileService.getProfileCacheByUid(uid);
		for (UserAct userAct : list) {
			Act act = InitData.ACT_MAP.get(userAct.getActId());
			if (null != act) {
				returnList.add(new UserActView(userAct, act, profile));
			}
		}
		return returnList;
	}

	@Override
	public int countUserActByUid(long uid) {
		UserActExample example = new UserActExample();
		example.createCriteria().andUidEqualTo(uid);
		return userActMapper.countByExample(example);
	}

	@Override
	public boolean hasAct(long uid, long actId) {
		return null != redisTemplate.opsForZSet().score(
				RedisKeyGenerator.genMyActsKey(uid), actId);
	}

	@Override
	public UserAct getUserAct(long uid, long actId) {
		UserActExample example = new UserActExample();
		example.createCriteria().andUidEqualTo(uid).andActIdEqualTo(actId);
		List<UserAct> list = userActMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public boolean isInterested(long uid, long actId) {
		List<Long> actIds = getUserActIdsFromCache(uid, Integer.MAX_VALUE);
		if (CollectionUtils.isEmpty(actIds)) {
			return false;
		} else if (actIds.contains(actId)) {
			return true;
		} else {
			List<Long> synonymIds = actService.listSynonymIds(actId);
			if (CollectionUtils.containsAny(actIds, synonymIds)) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public List<UserAct> listFriendsRecentAct(Collection<Long> friendIds,
			Date startDate, int firstResult, int maxResults) {
		if (CollectionUtils.isEmpty(friendIds)) {
			return Collections.emptyList();
		}
		UserActExample example = new UserActExample();
		example.createCriteria().andUidIn(new ArrayList<Long>(friendIds))
				.andLastModifyTimeGreaterThanOrEqualTo(startDate);
		example.setOrderByClause("last_modify_time desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return userActMapper.selectByExample(example);
	}

	@Override
	public int countFriendsRecentAct(Collection<Long> friendIds, Date startDate) {
		if (CollectionUtils.isEmpty(friendIds)) {
			return 0;
		}
		UserActExample example = new UserActExample();
		example.createCriteria().andUidIn(new ArrayList<Long>(friendIds))
				.andLastModifyTimeGreaterThanOrEqualTo(startDate);
		return userActMapper.countByExample(example);
	}

	@Override
	public boolean existUserAct(long uid, long actId) {
		Long rank = redisTemplate.opsForZSet().rank(
				RedisKeyGenerator.genMyActsKey(uid), actId);
		return rank != null;
	}
}
