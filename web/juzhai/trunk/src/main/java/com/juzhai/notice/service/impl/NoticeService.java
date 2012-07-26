package com.juzhai.notice.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.notice.bean.NoticeType;
import com.juzhai.notice.bean.NoticeUserTemplate;
import com.juzhai.notice.service.INoticeService;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.platform.exception.AdminException;
import com.juzhai.platform.service.ISynchronizeService;
import com.qplus.push.QPushBean;
import com.qplus.push.QPushResult;
import com.qplus.push.QPushService;

@Service
public class NoticeService implements INoticeService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
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
			// 新访客消息不加入通知列表
			if (noticeType.getType() != NoticeType.VISITOR.getType()) {
				// TODO 只有Q+才加入到通知列表
				if (tpUserAuthService.isExist(uid, 9l)) {
					redisTemplate.opsForZSet().add(
							RedisKeyGenerator.genNoticeUsersKey(), uid,
							System.currentTimeMillis());
				}
			}
		}
		return value;
	}

	@Override
	public void emptyNotice(long uid, NoticeType noticeType) {
		redisTemplate.delete(RedisKeyGenerator.genUserNoticeNumKey(uid,
				noticeType));
		boolean removeNoticeUser = true;
		for (NoticeType notice : NoticeType.values()) {
			if (notice != noticeType
					&& redisTemplate.hasKey(RedisKeyGenerator
							.genUserNoticeNumKey(uid, notice))) {
				removeNoticeUser = false;
				break;
			}
		}
		if (removeNoticeUser) {
			redisTemplate.opsForZSet().remove(
					RedisKeyGenerator.genNoticeUsersKey(), uid);
		}
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

		AuthInfo authInfo = tpUserAuthService.getSecretary(user.getTpName());
		if (authInfo == null) {
			throw new AdminException(AdminException.ADMIN_API_EXCEED_LIMIT);
		}
		String fuid = user.getTpIdentity();
		String[] fuids = new String[] { fuid };
		String text = messageSource
				.getMessage(
						NoticeUserTemplate.NOTICE_USER_TEXT_DEFAULT.getName(),
						new Object[] { String.valueOf(num) },
						Locale.SIMPLIFIED_CHINESE);
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

	@Override
	public void noticeQplusUser(String openid, String text) {
		Thirdparty tp = InitData.TP_MAP.get(9l);
		QPushService service = QPushService.createInstance(
				Integer.parseInt(tp.getAppKey()), tp.getAppSecret());
		QPushBean bean = new QPushBean();
		bean.setNum(1); //
		// 由App指定，一般展示在App图标的右上角。最大100v最长260字节。该字段会在拉起App的时候透传给App应用程序
		bean.setInstanceid(0); // 桌面实例ID, 数字，目前建议填0
		bean.setOptype(1); // 展现方式: 1-更新内容直接进消息中心
		bean.setText(text); // 文本提示语Utf8编码，最长90字节
		bean.setPushmsgid("1");// 本次PUSH的消息ID，建议填写，可以为任意数字

		try {
			bean.setQplusid(openid); // 桌面ID，字符串，必填信息，且内容会被校验
			QPushResult result = null;
			/**
			 * 
			 * 错误码: 0 - 处理成功，PUSH消息顺利到达PUSH服务中心 1 - 系统忙，参见提示信息“em“ 2 -
			 * Q+桌面KEY信息错误 3 - 指定APPID、指定OP类型的PUSH频率受限 4 - 缺少OPTYPE信息 5 -
			 * Q+桌面KEY信息无效 6 - 其他错误信息
			 */
			result = service.push(bean);
			if (result == null || 0 != result.getIntValue("ERRCODE")) {
				log.error("noticeQplusUser TpIdentity:" + openid
						+ " errorcode:" + result);
			}
		} catch (IOException e) {
			log.error("noticeQplusUser is error ", e);
		}
	}
}
