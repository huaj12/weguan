/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.act.dao.IActDao;
import com.juzhai.act.mapper.UserActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.UserAct;
import com.juzhai.act.model.UserActExample;
import com.juzhai.act.service.IUserActService;

@Service
public class UserActService implements IUserActService {

	@Autowired
	private IActDao actDao;
	@Autowired
	private UserActMapper userActMapper;

	@Override
	public void useAct(long uid, long actId) {
		UserActExample example = new UserActExample();
		example.createCriteria().andUidEqualTo(uid).andActIdEqualTo(actId);
		List<UserAct> list = userActMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			// insert
			UserAct userAct = new UserAct();
			userAct.setActId(actId);
			userAct.setHotLev(1);
			userAct.setUid(uid);
			userAct.setCreateTime(new Date());
			userAct.setLastModifyTime(userAct.getCreateTime());
			userActMapper.insertSelective(userAct);
		} else {
			// update
			UserAct userAct = list.get(0);
			userAct.setHotLev(userAct.getHotLev() + 1);
			userAct.setLastModifyTime(new Date());
			userActMapper.updateByPrimaryKeySelective(userAct);
		}
		
		//push
	}

	@Override
	public void useAct(long uid, String actName) {
		Act act = actDao.selectActByName(actName);
		if (null == act) {
			// TODO 验证长度，过滤词等
			act = actDao.insertAct(uid, actName, null);
		} else {
			actDao.increasePopularity(act.getId(), 1);
		}
		useAct(uid, act.getId());
	}

	@Override
	public void removeAct(long uid, long actId) {
		UserActExample example = new UserActExample();
		example.createCriteria().andUidEqualTo(uid).andActIdEqualTo(actId);
		userActMapper.deleteByExample(example);
	}

}
