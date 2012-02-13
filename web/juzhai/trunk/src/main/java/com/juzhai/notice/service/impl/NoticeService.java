package com.juzhai.notice.service.impl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.notice.NoticeConfig;
import com.juzhai.notice.bean.NoticeType;
import com.juzhai.notice.bean.NoticeUserTemplate;
import com.juzhai.notice.service.INoticeService;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.ThirdpartyNameEnum;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.platform.exception.AdminException;
import com.juzhai.platform.service.IAdminService;
import com.juzhai.platform.service.ISynchronizeService;

@Service
public class NoticeService implements INoticeService {

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private IAdminService adminService;
	@Autowired
	private ISynchronizeService synchronizeService;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private MessageSource messageSource;

	@Override
	public long incrNotice(long uid, NoticeType noticeType) {
		return redisTemplate.opsForValue().increment(
				RedisKeyGenerator.genUserNoticeNumKey(uid, noticeType), 1);
	}

	@Override
	public void emptyNotice(long uid, NoticeType noticeType) {
		redisTemplate.delete(RedisKeyGenerator.genUserNoticeNumKey(uid,
				noticeType));
	}

	@Override
	public Map<Integer, Long> getAllNoticeNum(long uid) {
		Map<Integer, Long> map = new HashMap<Integer, Long>();
		for (NoticeType noticeType : NoticeType.values()) {
			map.put(noticeType.getType(), getNoticeNum(uid, noticeType));
		}
		return map;
	}

	@Override
	public Long getNoticeNum(long uid, NoticeType noticeType) {
		return redisTemplate.opsForValue().increment(
				RedisKeyGenerator.genUserNoticeNumKey(uid, noticeType), 0);
	}

	@Override
	public void noticeUserUnReadNum(long receiver, int num)
			throws AdminException {
		TpUser user = tpUserService.getTpUserByUid(receiver);

		long uid = NoticeConfig.getValue(
				ThirdpartyNameEnum.valueOf(user.getTpName()), "uid");
		long tpId = NoticeConfig.getValue(
				ThirdpartyNameEnum.valueOf(user.getTpName()), "tpId");
		if (!adminService.isAllocation(uid, tpId)) {
			// 超过配额
			throw new AdminException(AdminException.ADMIN_API_EXCEED_LIMIT);
		}

		AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);

		String fuid = user.getTpIdentity();
		String[] fuids = new String[] { fuid };
		String text = messageSource.getMessage(
				NoticeUserTemplate.NOTICE_USER_TEXT_DEFAULT.getName(),
				new Object[] { num }, Locale.SIMPLIFIED_CHINESE);
		synchronizeService.notifyMessage(authInfo, fuids, text);

	}
}
