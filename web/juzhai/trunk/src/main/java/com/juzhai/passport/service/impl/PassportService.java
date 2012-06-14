package com.juzhai.passport.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.antiad.service.IAdLockIpService;
import com.juzhai.antiad.service.IFoulService;
import com.juzhai.core.bean.Function;
import com.juzhai.core.bean.UseLevel;
import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.exception.JuzhaiException;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.mapper.PassportMapper;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.model.PassportExample;
import com.juzhai.passport.service.IPassportService;
import com.juzhai.passport.service.IReportService;

@Service
public class PassportService implements IPassportService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private PassportMapper passportMapper;
	@Autowired
	private IFoulService foulService;
	@Autowired
	private MemcachedClient memcachedClient;
	@Autowired
	private IReportService reportService;
	@Autowired
	private IAdLockIpService adLockIpService;

	@Value("${is.permanent.lock.time}")
	private long isPermanentLockTime;
	@Value("${user.gag.expire.time}")
	private int userGagExpireTime;
	@Value("${user.gag.save.expire.time}")
	private int userGagSaveExpireTime;
	@Value("${ip.gag.save.expire.time}")
	private int ipGagSaveExpireTime;
	@Value("${gag.need.foul.count}")
	private int gagNeedFoulCount;
	@Value("${shield.need.gag.count}")
	private int shieldNeedGagCount;
	@Value("${ip.need.foul.count}")
	private int ipNeedFoulCount;
	@Value("${ip.need.gag.count}")
	private int ipNeedGagCount;
	@Value("${ip.need.shield.count}")
	private int ipNeedShieldCount;

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
	public boolean isUse(Function level, long uid) {
		Passport passport = passportMapper.selectByPrimaryKey(uid);
		if (passport.getUseLevel() >= level.getLevel().getUseLevel()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void setUseLevel(long uid, UseLevel useLevel) {
		if (useLevel == null) {
			return;
		}
		Passport passport = new Passport();
		passport.setId(uid);
		passport.setUseLevel(useLevel.getUseLevel());
		passport.setLastModifyTime(new Date());
		passportMapper.updateByPrimaryKeySelective(passport);
	}

	@Override
	public void isAd(UserContext context) throws JuzhaiException {
		String ip = context.getRemoteAddress();
		long uid = context.getUid();
		int userFoul = foulService.getUserFoul(context.getUid());
		int ipFoul = foulService.getIpFoul(ip);
		Integer userGag = 0;
		Integer ipGag = 0;
		Boolean isGag = null;
		try {
			isGag = memcachedClient.get(MemcachedKeyGenerator
					.genGagUserTimeKey(uid));
			ipGag = memcachedClient.get(MemcachedKeyGenerator
					.genGagIpCountKey(context.getRemoteAddress()));
			userGag = memcachedClient.get(MemcachedKeyGenerator
					.genGagUserCountKey(context.getUid()));
			ipGag = ipGag == null ? 0 : ipGag;
			userGag = userGag == null ? 0 : userGag;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 是否被禁言
		if (isGag != null && isGag) {
			throw new JuzhaiException(JuzhaiException.AD_USER_GAG);
		}
		// 禁言
		if (userFoul > gagNeedFoulCount) {
			try {
				memcachedClient.set(MemcachedKeyGenerator
						.genGagIpCountKey(context.getRemoteAddress()),
						ipGagSaveExpireTime, ++ipGag);
				memcachedClient.set(MemcachedKeyGenerator
						.genGagUserCountKey(context.getUid()),
						userGagSaveExpireTime, ++userGag);
				memcachedClient.set(
						MemcachedKeyGenerator.genGagUserTimeKey(uid),
						userGagExpireTime, true);
				// 清空用户犯规
				foulService.resetFoul(uid);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			throw new JuzhaiException(JuzhaiException.AD_USER_GAG);
		}
		// 禁言超过N次屏蔽
		if (userGag > shieldNeedGagCount) {
			reportService
					.adReport(context.getUid(), context.getRemoteAddress());
			// 清空禁言
			try {
				memcachedClient.delete(MemcachedKeyGenerator
						.genGagUserCountKey(context.getUid()));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			throw new JuzhaiException(JuzhaiException.AD_USER_SHIELD);
		}
		// Ip记录犯规了N次
		if (ipFoul > ipNeedFoulCount) {
			adLockIpService.save(ip);
			foulService.resetFoul(ip);
			throw new JuzhaiException(JuzhaiException.AD_IP_SHIELD);
		}
		// 被禁言N次封ip
		if (ipGag > ipNeedGagCount) {
			adLockIpService.save(ip);
			try {
				memcachedClient.delete(MemcachedKeyGenerator
						.genGagIpCountKey(context.getRemoteAddress()));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			throw new JuzhaiException(JuzhaiException.AD_IP_SHIELD);
		}
		// 屏蔽次数大于N次封ip
		if (reportService.countIpReport(ip) > ipNeedShieldCount) {
			adLockIpService.save(ip);
			throw new JuzhaiException(JuzhaiException.AD_IP_SHIELD);
		}

	}
}
