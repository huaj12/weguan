/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juzhai.passport.dao.ITpUserDao;
import com.juzhai.passport.mapper.TpUserMapper;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.model.TpUserExample;

@Repository
public class TpUserDao implements ITpUserDao {

	@Autowired
	private TpUserMapper tpUserMapper;

	@Override
	public TpUser selectTpUserByTpNameAndTpIdentity(String tpName,
			String tpIdentity) {
		TpUserExample example = new TpUserExample();
		example.createCriteria().andTpNameEqualTo(tpName)
				.andTpIdentityEqualTo(tpIdentity);
		List<TpUser> list = tpUserMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

}
