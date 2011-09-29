package com.juzhai.passport.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.passport.InitData;
import com.juzhai.passport.mapper.TpUserMapper;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.model.TpUserExample;
import com.juzhai.passport.service.ITpUserService;

@Service
public class TpUserService implements ITpUserService {
	@Autowired
	private TpUserMapper tpUserMapper;

	@Override
	public TpUser getTpUserByTpIdAndIdentity(long tpId, String identity) {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if (null == tp) {
			return null;
		}
		TpUserExample example = new TpUserExample();
		example.createCriteria().andTpNameEqualTo(tp.getName())
				.andTpIdentityEqualTo(identity);
		List<TpUser> list = tpUserMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public TpUser getTpUserByUid(long uid) {
		return tpUserMapper.selectByPrimaryKey(uid);
	}

}
