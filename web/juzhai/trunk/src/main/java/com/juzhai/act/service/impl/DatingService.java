package com.juzhai.act.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.act.bean.ConsumeType;
import com.juzhai.act.bean.ContactType;
import com.juzhai.act.bean.DatingResponse;
import com.juzhai.act.exception.DatingInputException;
import com.juzhai.act.mapper.DatingMapper;
import com.juzhai.act.model.Dating;
import com.juzhai.act.model.DatingExample;
import com.juzhai.act.service.IDatingService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.util.StringUtil;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;

@Service
public class DatingService implements IDatingService {

	@Autowired
	private IProfileService profileService;
	@Autowired
	private DatingMapper datingMapper;
	@Autowired
	private IUserActService userActService;
	@Value("${max.dating.week.limit}")
	private int maxDatingWeekLimit;
	@Value("${max.dating.total.limit}")
	private int maxDatingTotalLimit;
	@Value("${contact.value.length.max}")
	private int contactValueLengthMax;
	@Value("${contact.value.length.min}")
	private int contactValueLengthMin;

	@Override
	public void checkCanDate(long uid, long targetId, long datingId)
			throws DatingInputException {
		checkProfileSimple(uid);
		if (userActService.getUserActCountFromCache(targetId) <= 0) {
			throw new DatingInputException(
					DatingInputException.DATING_TARGET_NONE_ACTS);
		}
		DatingExample example = new DatingExample();
		if (datingId <= 0) {
			// 是否已经在约中
			example.createCriteria().andStarterUidEqualTo(uid)
					.andReceiverUidEqualTo(targetId);
			if (datingMapper.countByExample(example) > 0) {
				throw new DatingInputException(
						DatingInputException.DATING_EXISTENCE);
			}
		}
		// 本周是否到达10人
		example.clear();
		example.createCriteria()
				.andStarterUidEqualTo(uid)
				.andCreateTimeGreaterThanOrEqualTo(
						getDayOfWeek(null, Calendar.MONDAY))
				.andCreateTimeLessThanOrEqualTo(
						DateUtils.addDays(getDayOfWeek(null, Calendar.SUNDAY),
								1)).andIdNotEqualTo(datingId);
		if (datingMapper.countByExample(example) >= maxDatingWeekLimit) {
			throw new DatingInputException(
					DatingInputException.DATING_WEEK_LIMIT);
		}
		// 总共是否到达100人
		example.clear();
		example.createCriteria().andStarterUidEqualTo(uid);
		if (datingMapper.countByExample(example) >= maxDatingTotalLimit) {
			throw new DatingInputException(
					DatingInputException.DATING_TOTAL_LIMIT);
		}
	}

	@Override
	public Dating checkCanRespDating(long uid, long datingId)
			throws DatingInputException {
		// 验证是否是此约会的接收者
		Dating dating = getDatingByDatingId(datingId);
		if (null == dating || dating.getReceiverUid() != uid
				|| null != dating.getReceiverContactType()
				|| StringUtils.isNotEmpty(dating.getReceiverContactValue())
				|| dating.getResponse() != 0) {
			throw new DatingInputException(
					DatingInputException.ILLEGAL_OPERATION);
		}
		checkProfileSimple(uid);
		return dating;
	}

	@Override
	public long date(long uid, long targetId, long actId,
			ConsumeType consumeType, ContactType contactType,
			String contactValue) throws DatingInputException {
		checkCanDate(uid, targetId, 0);
		if (actId <= 0) {
			throw new DatingInputException(
					DatingInputException.DATING_ACT_NOT_CHOOSE);
		}
		ProfileCache targetProfile = profileService
				.getProfileCacheByUid(targetId);
		if (null == targetProfile || !userActService.hasAct(targetId, actId)
				|| null == consumeType || null == contactType) {
			throw new DatingInputException(
					DatingInputException.ILLEGAL_OPERATION);
		}
		checkContact(contactType, contactValue);
		// 开始约会
		Dating dating = new Dating();
		dating.setActId(actId);
		dating.setConsumeType(consumeType.getValue());
		dating.setCreateTime(new Date());
		dating.setLastModifyTime(dating.getCreateTime());
		dating.setReceiverUid(targetId);
		dating.setStarterUid(uid);
		dating.setStarterContactType(contactType.getValue());
		dating.setStarterContactValue(contactValue);
		datingMapper.insertSelective(dating);

		// TODO redis存储
		return dating.getId();
	}

	@Override
	public long modifyDating(long uid, long datingId, long actId,
			ConsumeType consumeType, ContactType contactType,
			String contactValue) throws DatingInputException {
		Dating dating = getDatingByDatingId(datingId);
		if (null == dating) {
			throw new DatingInputException(
					DatingInputException.ILLEGAL_OPERATION);
		}
		checkCanDate(uid, dating.getReceiverUid(), datingId);
		if (actId <= 0) {
			throw new DatingInputException(
					DatingInputException.DATING_ACT_NOT_CHOOSE);
		}
		if (dating.getStarterUid() != uid
				|| null != dating.getReceiverContactType()
				|| StringUtils.isNotEmpty(dating.getReceiverContactValue())
				|| dating.getResponse() != 0
				|| !userActService.hasAct(dating.getReceiverUid(), actId)
				|| null == consumeType || null == contactType) {
			throw new DatingInputException(
					DatingInputException.ILLEGAL_OPERATION);
		}
		checkContact(contactType, contactValue);
		// 开始修改
		dating.setConsumeType(consumeType.getValue());
		dating.setActId(actId);
		dating.setCreateTime(new Date());
		dating.setLastModifyTime(dating.getCreateTime());
		dating.setStarterContactType(contactType.getValue());
		dating.setStarterContactValue(contactValue);
		datingMapper.updateByPrimaryKeySelective(dating);

		// TODO redis
		return dating.getId();
	}

	@Override
	public void deleteDating(long uid, long datingId) {
		DatingExample example = new DatingExample();
		example.createCriteria().andIdEqualTo(datingId)
				.andStarterUidEqualTo(uid);
		datingMapper.deleteByExample(example);
	}

	@Override
	public void acceptDating(long uid, long datingId, ContactType contactType,
			String contactValue) throws DatingInputException {
		Dating dating = checkCanRespDating(uid, datingId);
		checkContact(contactType, contactValue);
		dating.setReceiverContactType(contactType.getValue());
		dating.setReceiverContactValue(contactValue);
		dating.setResponse(DatingResponse.ACCEPT.getValue());
		dating.setLastModifyTime(new Date());
		dating.setHasRead(true);
		datingMapper.updateByPrimaryKeySelective(dating);

		// TODO redis
	}

	private void checkProfileSimple(long uid) throws DatingInputException {
		ProfileCache profile = profileService.getProfileCacheByUid(uid);
		if (null == profile) {
			throw new DatingInputException(
					DatingInputException.ILLEGAL_OPERATION);
		}
		// TODO 个人资料是否完善
		if (profile.getBirthYear() == null) {
			throw new DatingInputException(
					DatingInputException.DATING_PROFILE_SIMPLE);
		}
	}

	private void checkContact(ContactType contactType, String contactValue)
			throws DatingInputException {
		if (null == contactType) {
			throw new DatingInputException(
					DatingInputException.DATING_CONTACT_VALUE_INVALID);
		}
		int contactValueLength = StringUtil.chineseLength(contactValue);
		if (contactValueLength < contactValueLengthMin
				|| contactValueLength > contactValueLengthMax) {
			throw new DatingInputException(
					DatingInputException.DATING_CONTACT_VALUE_INVALID);
		}
	}

	private Date getDayOfWeek(Date date, int dayOfWeek) {
		Calendar c = Calendar.getInstance();
		if (null == date) {
			date = new Date();
		}
		c.setTime(DateUtils.truncate(date, Calendar.DATE));
		int todayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		if (todayOfWeek == dayOfWeek) {
			return c.getTime();
		}
		if (dayOfWeek == Calendar.SUNDAY) {
			dayOfWeek = Calendar.SATURDAY + 1;
		}
		int addDay = dayOfWeek - todayOfWeek;
		c.add(Calendar.DATE, addDay);
		return c.getTime();
	}

	@Override
	public List<Dating> listDating(long uid, Integer response, int firstResult,
			int maxResults) {
		DatingExample example = new DatingExample();
		DatingExample.Criteria c = example.createCriteria()
				.andStarterUidEqualTo(uid);
		if (null != response) {
			c.andResponseEqualTo(response);
		}
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause("last_modify_time desc, id desc");
		return datingMapper.selectByExample(example);
	}

	@Override
	public int countDating(long uid, Integer response) {
		DatingExample example = new DatingExample();
		DatingExample.Criteria c = example.createCriteria()
				.andStarterUidEqualTo(uid);
		if (null != response) {
			c.andResponseEqualTo(response);
		}
		return datingMapper.countByExample(example);
	}

	@Override
	public List<Dating> listDatingMe(long uid, int firstResult, int maxResults) {
		DatingExample example = new DatingExample();
		example.createCriteria().andReceiverUidEqualTo(uid);
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause("last_modify_time desc, id desc");
		return datingMapper.selectByExample(example);
	}

	@Override
	public int countDatingMe(long uid) {
		DatingExample example = new DatingExample();
		example.createCriteria().andReceiverUidEqualTo(uid);
		return datingMapper.countByExample(example);
	}

	@Override
	public Dating fetchDating(long uid, long targetUid) {
		DatingExample example = new DatingExample();
		example.createCriteria().andStarterUidEqualTo(uid)
				.andReceiverUidEqualTo(targetUid);
		List<Dating> list = datingMapper.selectByExample(example);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Dating getDatingByDatingId(long datingId) {
		return datingMapper.selectByPrimaryKey(datingId);
	}
}
