/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.act.dao.IActDao;
import com.juzhai.act.mapper.UserActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.UserAct;
import com.juzhai.act.model.UserActExample;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.util.StringUtil;
import com.juzhai.passport.service.IFriendService;

@Service
public class UserActService implements IUserActService {

	@Autowired
	private IActDao actDao;
	@Autowired
	private UserActMapper userActMapper;
	@Autowired
	private AmqpTemplate updateActRabbitTemplate;
	@Autowired
	private IFriendService friendService;
	@Value("${act.name.length.min}")
	private int actNameLengthMin = 2;
	@Value("${act.name.length.max}")
	private int actNameLengthMax = 20;

	@Override
	public void removeAct(long uid, long actId) {
		UserActExample example = new UserActExample();
		example.createCriteria().andUidEqualTo(uid).andActIdEqualTo(actId);
		userActMapper.deleteByExample(example);

		// 更新Act的使用人数
		actDao.incrOrDecrPopularity(actId, -1);
	}

	@Override
	public void wantToAct(long uid, long actId, long friendId) {
		useAct(uid, actId, 2, true);
		friendService.incrOrDecrIntimacy(uid, friendId, 1);
	}

	@Override
	public void dependToAct(long uid, long actId, long friendId) {
		useAct(uid, actId, 1, true);
	}

	@Override
	public void addAct(long uid, String actName) {
		Act act = actDao.selectActByName(actName);
		if (null == act) {
			// TODO 过滤词
			long length = StringUtil.chineseLength(actName);
			if (length < actNameLengthMin || length > actNameLengthMax) {
				// TODO 抛出异常
			}
			act = actDao.insertAct(uid, actName, null);
		}
		addAct(uid, act.getId());
	}

	@Override
	public void addAct(long uid, long actId) {
		useAct(uid, actId, 5, false);
	}

	private void useAct(long uid, long actId, int hotLev, boolean canRepeat) {
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
				// TODO 抛出异常
			}
			// update
			userAct = list.get(0);
			userAct.setHotLev(userAct.getHotLev() + hotLev);
			userAct.setLastModifyTime(new Date());
			userActMapper.updateByPrimaryKeySelective(userAct);
		}

		// push to friends
		updateActRabbitTemplate.convertAndSend(userAct);
	}
}
