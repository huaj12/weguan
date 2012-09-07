package com.juzhai.passport.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.mapper.TpUserMapper;
import com.juzhai.passport.model.Passport;
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

	@Override
	public void updateTpIdentity(long uid, String newTpIdentity) {
		TpUser tpUser = new TpUser();
		tpUser.setUid(uid);
		tpUser.setLastModifyTime(new Date());
		tpUser.setTpIdentity(newTpIdentity);
		tpUserMapper.updateByPrimaryKeySelective(tpUser);
	}

	@Override
	public boolean existTpUserByTpIdAndIdentity(long tpId, String identity) {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		TpUserExample example = new TpUserExample();
		example.createCriteria().andTpNameEqualTo(tp.getName())
				.andTpIdentityEqualTo(identity);
		return tpUserMapper.countByExample(example) > 0;
	}

	@Override
	public void registerTpUser(Thirdparty tp, String identity, Passport passport) {
		TpUser tpUser = new TpUser();
		tpUser.setUid(passport.getId());
		tpUser.setTpName(tp.getName());
		tpUser.setTpIdentity(identity);
		tpUser.setCreateTime(passport.getCreateTime());
		tpUser.setLastModifyTime(passport.getLastModifyTime());
		tpUserMapper.insertSelective(tpUser);
	}

	@Override
	public Thirdparty getTpByUidAndJoinType(long uid, JoinTypeEnum joinType) {
		Thirdparty tp = null;
		TpUser tpUser = getTpUserByUid(uid);
		if (null != tpUser) {
			tp = InitData.getTpByTpNameAndJoinType(tpUser.getTpName(),
					JoinTypeEnum.CONNECT);
		}
		return tp;
	}

}
