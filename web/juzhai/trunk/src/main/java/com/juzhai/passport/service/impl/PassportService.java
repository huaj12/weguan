package com.juzhai.passport.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.core.dao.Limit;
import com.juzhai.passport.mapper.PassportMapper;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.model.PassportExample;
import com.juzhai.passport.service.IPassportService;

@Service
public class PassportService implements IPassportService {

	@Autowired
	private PassportMapper passportMapper;

	@Override
	public Passport getPassportByUid(long uid) {
		return passportMapper.selectByPrimaryKey(uid);
	}

	@Override
	public void lockUser(long uid, Date time) {
		Passport passport = getPassportByUid(uid);
		if (time != null) {
			if (time.getTime() < System.currentTimeMillis()) {
				return;
			}
		}
		passport.setShieldTime(time);
		passport.setLastModifyTime(new Date());
		passportMapper.updateByPrimaryKey(passport);

	}

	@Override
	public int totalCount() {
		return passportMapper.countByExample(new PassportExample());
	}

	@Override
	public List<Passport> listLockUser(int firstResult, int maxResults) {
		PassportExample example = new PassportExample();
		example.createCriteria().andShieldTimeGreaterThan(new Date());
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause("last_modify_time desc ");
		return passportMapper.selectByExample(example);
	}

	@Override
	public int countLockUser() {
		PassportExample example = new PassportExample();
		example.createCriteria().andShieldTimeGreaterThan(new Date());
		return passportMapper.countByExample(example);
	}

	@Override
	public List<Long> listInviteUsers(long inviterUid) {
		PassportExample example = new PassportExample();
		example.createCriteria().andInviterUidEqualTo(inviterUid);
		example.setOrderByClause("create_time desc");
		List<Passport> list = passportMapper.selectByExample(example);
		List<Long> uids = new ArrayList<Long>(list.size());
		for (Passport passport : list) {
			uids.add(passport.getId());
		}
		return uids;
	}

}
