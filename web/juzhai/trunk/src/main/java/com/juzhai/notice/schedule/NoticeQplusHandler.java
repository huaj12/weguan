package com.juzhai.notice.schedule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.notice.service.INoticeService;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.mapper.TpUserAuthMapper;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUserAuth;
import com.juzhai.passport.model.TpUserAuthExample;
import com.qplus.push.QPushBean;
import com.qplus.push.QPushResult;
import com.qplus.push.QPushService;

@Component
public class NoticeQplusHandler extends AbstractScheduleHandler {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private INoticeService noticeService;
	@Autowired
	private TpUserAuthMapper tpUserAuthMapper;
	@Autowired
	private MessageSource messageSource;

	@Override
	protected void doHandle() {
		try {
			List<TpUserAuth> qplusUids = new ArrayList<TpUserAuth>();
			Thirdparty tp = InitData.TP_MAP.get(9l);
			Long count = redisTemplate.opsForZSet().size(
					RedisKeyGenerator.genNoticeUsersKey());
			List<Long> uids = null;
			if (count != null && count != 0) {
				uids = noticeService.getNoticUserList(count.intValue());
			}
			TpUserAuthExample example = new TpUserAuthExample();
			example.createCriteria().andTpIdEqualTo(tp.getId());
			List<TpUserAuth> userAuthList = tpUserAuthMapper
					.selectByExample(example);
			for (Long uid : uids) {
				for (TpUserAuth tpuserAuth : userAuthList) {
					if (uid.longValue() == tpuserAuth.getUid().longValue()) {
						qplusUids.add(tpuserAuth);
					}
				}
			}
			push(qplusUids, tp);
		} catch (Exception e) {
			log.error("NoticeQplusHandler  is error", e);
		}
	}

	private void push(List<TpUserAuth> qplusUids, Thirdparty tp) {
		for (TpUserAuth tpUserAuth : qplusUids) {
			AuthInfo authInfo = null;
			try {
				authInfo = AuthInfo.convertToBean(tpUserAuth.getAuthInfo());
			} catch (JsonGenerationException e1) {
			}
			if (authInfo == null) {
				break;
			}
			final QPushService service = QPushService.createInstance(
					Integer.parseInt(tp.getAppKey()), tp.getAppSecret());
			QPushBean bean = new QPushBean();
			bean.setNum(1); // 由App指定，一般展示在App图标的右上角。最大100v最长260字节。该字段会在拉起App的时候透传给App应用程序
			bean.setInstanceid(0); // 桌面实例ID, 数字，目前建议填0
			bean.setOptype(1); // 展现方式: 1-更新内容直接进消息中心
			bean.setQplusid(authInfo.getTpIdentity()); // 桌面ID，字符串，必填信息，且内容会被校验
			bean.setText(messageSource.getMessage("qq.plus.push.text", null,
					Locale.SIMPLIFIED_CHINESE)); // 文本提示语Utf8编码，最长90字节
			bean.setPushmsgid(String.valueOf(System.currentTimeMillis())); // 本次PUSH的消息ID，建议填写，可以为任意数字
			QPushResult result = null;
			try {
				/**
				 * 
				 * 错误码: 0 - 处理成功，PUSH消息顺利到达PUSH服务中心 1 - 系统忙，参见提示信息“em“ 2 -
				 * Q+桌面KEY信息错误 3 - 指定APPID、指定OP类型的PUSH频率受限 4 - 缺少OPTYPE信息 5 -
				 * Q+桌面KEY信息无效 6 - 其他错误信息
				 */
				result = service.push(bean);
				if (0 != result.getIntValue("ERRCODE")) {
					log.error("qq plus push TpIdentity:"
							+ authInfo.getTpIdentity() + " uid:"
							+ tpUserAuth.getUid() + " errorcode:" + result);
				}
			} catch (IOException e) {
				log.error("qq plus push TpIdentity:" + authInfo.getTpIdentity()
						+ " uid:" + tpUserAuth.getUid(), e);
			}

		}
	}
}
