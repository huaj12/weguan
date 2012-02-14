package com.juzhai.notice.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
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
		long value = redisTemplate.opsForValue().increment(
				RedisKeyGenerator.genUserNoticeNumKey(uid, noticeType), 1);
		if (value > 0) {
			redisTemplate.opsForZSet().add(
					RedisKeyGenerator.genNoticeUsersKey(), uid,
					System.currentTimeMillis());
		}
		return value;
	}

	@Override
	public void emptyNotice(long uid, NoticeType noticeType) {
		redisTemplate.delete(RedisKeyGenerator.genUserNoticeNumKey(uid,
				noticeType));
		redisTemplate.opsForZSet().remove(
				RedisKeyGenerator.genNoticeUsersKey(), uid);
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
	public void noticeUserUnReadNum(long receiver, long num)
			throws AdminException {
		TpUser user = tpUserService.getTpUserByUid(receiver);

		// TODO (review) 获取有配额的小秘书，是不是应该在AdminService里封装呢？获取微博的地方就需要用到一样的代码
		List<Long> tagerUids = NoticeConfig.getValue(
				ThirdpartyNameEnum.getThirdpartyNameEnum(user.getTpName()),
				"uid");
		List<Long> tagerTpIds = NoticeConfig.getValue(
				ThirdpartyNameEnum.getThirdpartyNameEnum(user.getTpName()),
				"tpId");
		if (CollectionUtils.isEmpty(tagerTpIds)
				|| CollectionUtils.isEmpty(tagerUids)) {
			return;
		}
		long uid = 0;
		long tpId = 0;
		for (Long tUid : tagerUids) {
			// TODO (review) 是不是应该参数用AuthInfo？或者isAllocation返回AuthInfo？因为下面又从库搜了一次authInfo
			if (adminService.isAllocation(tUid, tagerTpIds.get(0))) {
				uid = tUid;
				tpId = tagerTpIds.get(0);
				break;
			}
		}
		AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
		// TODO (review) 这里判断不适合了吧
		if (authInfo == null) {
			throw new AdminException(AdminException.ADMIN_API_EXCEED_LIMIT);
		}
		String fuid = user.getTpIdentity();
		String[] fuids = new String[] { fuid };
		String text = messageSource.getMessage(
				NoticeUserTemplate.NOTICE_USER_TEXT_DEFAULT.getName(),
				new Object[] { num }, Locale.SIMPLIFIED_CHINESE);
		synchronizeService.notifyMessage(authInfo, fuids, text);
	}

	@Override
	public List<Long> getNoticUserList(int count) {
		Set<TypedTuple<Long>> users = redisTemplate.opsForZSet()
				.rangeWithScores(RedisKeyGenerator.genNoticeUsersKey(), 0,
						count - 1);
		if (CollectionUtils.isEmpty(users)) {
			return Collections.emptyList();
		}
		List<Long> userIdList = new ArrayList<Long>(users.size());
		for (TypedTuple<Long> user : users) {
			userIdList.add(user.getValue());
		}
		return userIdList;
	}

	@Override
	public void removeFromNoticeUsers(long uid) {
		redisTemplate.opsForZSet().remove(
				RedisKeyGenerator.genNoticeUsersKey(), uid);
	}
}
