package com.juzhai.notice.schedule;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

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
	//TODO (review) 要经常注意警告，能处理的处理掉
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
			Thirdparty tp = InitData.TP_MAP.get(9l);
			while (true) {
				List<Long> uids = noticeService.getNoticUserList(100);
				//TODO (review) uids是空list，会发生什么情况？再结合下面的break，想想应该怎么处理break？
				TpUserAuthExample example = new TpUserAuthExample();
				example.createCriteria().andTpIdEqualTo(tp.getId())
						.andUidIn(uids);
				List<TpUserAuth> userAuthList = tpUserAuthMapper
						.selectByExample(example);
				push(userAuthList, tp);
				if (uids.size() < 100) {
					break;
				}
			}
		} catch (Exception e) {
			log.error("NoticeQplusHandler  is error", e);
		}
	}

	private void push(List<TpUserAuth> qplusUids, Thirdparty tp) {
		String text = messageSource.getMessage("qq.plus.push.text", null,
				Locale.SIMPLIFIED_CHINESE);
		QPushService service = QPushService.createInstance(
				Integer.parseInt(tp.getAppKey()), tp.getAppSecret());
		QPushBean bean = new QPushBean();
		for (TpUserAuth tpUserAuth : qplusUids) {
			AuthInfo authInfo = null;
			try {
				authInfo = AuthInfo.convertToBean(tpUserAuth.getAuthInfo());
			} catch (JsonGenerationException e1) {
			}
			if (authInfo == null) {
				continue;
			}
			// TODO (review) 框起来的代码都不会变吧？
			// ------------------------------//
			bean.setNum(1); // 由App指定，一般展示在App图标的右上角。最大100v最长260字节。该字段会在拉起App的时候透传给App应用程序
			bean.setInstanceid(0); // 桌面实例ID, 数字，目前建议填0
			bean.setOptype(1); // 展现方式: 1-更新内容直接进消息中心
			bean.setText(text); // 文本提示语Utf8编码，最长90字节
			// ------------------------------//
			bean.setQplusid(authInfo.getTpIdentity()); // 桌面ID，字符串，必填信息，且内容会被校验
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
			} finally {
				// 不管发送成功失败要删除不然会死循环
				// TODO (review) 这里写的非常好，能考虑到异常情况。上面continue地方呢？
				noticeService.removeFromNoticeUsers(tpUserAuth.getUid());
			}

		}
	}
}
