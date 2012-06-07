package com.juzhai.passport.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.core.bean.FunctionLevel;
import com.juzhai.core.dao.Limit;
import com.juzhai.passport.mapper.PassportMapper;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.model.PassportExample;
import com.juzhai.passport.service.IPassportService;

@Service
public class PassportService implements IPassportService {

	@Autowired
	private PassportMapper passportMapper;
	@Value("${is.permanent.lock.time}")
	private long isPermanentLockTime;

	@Override
	public Passport getPassportByLoginName(String loginName) {
		PassportExample example = new PassportExample();
		example.createCriteria().andLoginNameEqualTo(loginName);
		List<Passport> list = passportMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public Passport getPassportByUid(long uid) {
		return passportMapper.selectByPrimaryKey(uid);
	}

	@Override
	public void lockUser(long uid, Date time) {
		Passport passport = getPassportByUid(uid);
		// 如果是管理员则不操作
		if (passport == null || passport.getAdmin()) {
			return;
		}
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

	@Override
	public boolean isPermanentLock(long uid) {
		Passport passport = passportMapper.selectByPrimaryKey(uid);
		Date date = passport.getShieldTime();
		if (date.getTime() - new Date().getTime() > isPermanentLockTime) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean isUse(FunctionLevel level, long uid) {
		Passport passport = passportMapper.selectByPrimaryKey(uid);
		if (passport.getUseLevel() >= level.getLevel()) {
			return true;
		} else {
			return false;
		}
	}

}
