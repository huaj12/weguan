/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.act.InitData;
import com.juzhai.act.bean.ActDealType;
import com.juzhai.act.dao.IActDao;
import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.mapper.UserActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.UserAct;
import com.juzhai.act.model.UserActExample;
import com.juzhai.act.service.IInboxService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.util.StringUtil;
import com.juzhai.home.exception.IndexException;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.ITpUserService;

@Service
public class UserActService implements IUserActService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private IActDao actDao;
	@Autowired
	private UserActMapper userActMapper;
	@Autowired
	private AmqpTemplate updateActRabbitTemplate;
	@Autowired
	private IFriendService friendService;
	@Autowired
	private IInboxService inboxService;
	@Autowired
	private ITpUserService tpUserService;
	@Value("${act.name.length.min}")
	private int actNameLengthMin = 2;
	@Value("${act.name.length.max}")
	private int actNameLengthMax = 20;

	@Override
	public void respFeed(long uid, long actId, String tpFriendId, long tpId,
			ActDealType type) {
		TpUser tpUser = tpUserService.getTpUserByTpIdAndIdentity(tpId,
				tpFriendId);
		// 处理ActAndFriend
		dealActAndFriend(uid, actId, tpUser == null ? 0 : tpUser.getUid(), type);
		// TODO 第三方系统消息
	}

	@Override
	public void respFeed(long uid, long actId, long friendId, ActDealType type)
			throws IndexException {
		if (null == type) {
			throw new IndexException("response feed invalid parameter");
		}
		// 验证uid的inbox中是否存在,并取出
		if (!inboxService.remove(uid, friendId, actId)) {
			throw new IndexException("response feed invalid parameter");
		}
		dealActAndFriend(uid, actId, friendId, type);
		// 放入该放的已处理列表中
		inboxService.shiftRead(uid, friendId, actId, type);
	}

	private void dealActAndFriend(long uid, long actId, long friendId,
			ActDealType type) {
		switch (type) {
		case DEPEND:
			dependToAct(uid, actId, friendId);
			break;
		case WANT:
			wantToAct(uid, actId, friendId);
			break;
		case NILL:
			break;
		}
	}

	private void wantToAct(long uid, long actId, long friendId,
			String tpIdentity, long tpId) {
		try {
			useAct(uid, actId, 2, true);
		} catch (ActInputException e) {
			log.error(e.getErrorCode(), e);
		}
		if (friendId > 0) {
			friendService.incrOrDecrIntimacy(uid, friendId, 2);
			// TODO 发送私信
		} else if (StringUtils.isNotEmpty(tpIdentity) && tpId > 0) {
			// TODO 发送第三方系统消息
			// TODO 发送预存私信
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
	public void addAct(long uid, String actName) throws ActInputException {
		Act act = actDao.selectActByName(actName);
		if (null == act) {
			// TODO 过滤词
			long length = StringUtil.chineseLength(actName);
			if (length < actNameLengthMin || length > actNameLengthMax) {
				throw new ActInputException(ActInputException.ACT_NAME_INVALID);
			}
			act = actDao.insertAct(uid, actName, null);
		}
		addAct(uid, act.getId());
	}

	@Override
	public void addAct(long uid, long actId) throws ActInputException {
		useAct(uid, actId, 5, false);
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
			actDao.incrOrDecrPopularity(actId, 1);
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

		// push to friends
		updateActRabbitTemplate.convertAndSend(userAct);
	}

	@Override
	public List<Long> getUserActIdsFromCache(long uid) {
		Set<Long> actIds = redisTemplate.opsForZSet().reverseRange(
				RedisKeyGenerator.genMyActsKey(uid), 0, -1);
		if (CollectionUtils.isEmpty(actIds)) {
			return Collections.emptyList();
		}
		return new ArrayList<Long>(actIds);
	}

	@Override
	public List<Act> getUserActFromCache(long uid) {
		List<Long> actIdList = getUserActIdsFromCache(uid);
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
		userActMapper.deleteByExample(example);
		// 更新Act的使用人数
		actDao.incrOrDecrPopularity(actId, -1);
	}

}
