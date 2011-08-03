/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juzhai.act.dao.IActDao;
import com.juzhai.act.mapper.ActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.ActExample;

@Repository
public class ActDao implements IActDao {

	@Autowired
	private ActMapper actMapper;
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public Act selectActByName(String name) {
		ActExample example = new ActExample();
		example.createCriteria().andNameEqualTo(name);
		List<Act> list = actMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public Act insertAct(long uid, String name, List<Long> categoryIds) {
		Act act = assembleAct(uid, name, categoryIds, false);
		actMapper.insert(act);
		return act;
	}

	@Override
	public Act insertVerifiedAct(long uid, String name, List<Long> categoryIds) {
		Act act = assembleAct(uid, name, categoryIds, true);
		actMapper.insert(act);
		return act;
	}

	private Act assembleAct(long uid, String name, List<Long> categoryIds,
			boolean active) {
		Act act = new Act();
		act.setName(name);
		if (CollectionUtils.isNotEmpty(categoryIds)) {
			act.setCategoryIds(StringUtils.join(categoryIds, ","));
		}
		act.setActive(active);
		act.setCreateTime(new Date());
		act.setLastModifyTime(act.getCreateTime());
		act.setCreateUid(uid);
		return act;
	}

	@Override
	public void increasePopularity(long actId, int p) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", actId);
		params.put("p", p);
		sqlSession.update("Act_Mapper.increasePopularity", params);
	}

}
