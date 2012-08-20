package com.juzhai.platform.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.platform.bean.UserStatus;
import com.juzhai.platform.service.IQplusPushService;
import com.juzhai.platform.service.ISynchronizeService;
import com.qplus.QOpenResult;
import com.qplus.QOpenService;
import com.qplus.push.QPushBean;
import com.qplus.push.QPushResult;
import com.qplus.push.QPushService;

@Service
public class QqPlusSynchronizeService implements ISynchronizeService,
		IQplusPushService {
	private final Log log = LogFactory.getLog(getClass());

	@Override
	public List<UserStatus> listStatus(AuthInfo authInfo, long fuid, int size) {
		return Collections.emptyList();
	}

	@Override
	public void sendMessage(AuthInfo authInfo, String title, String text,
			String link, byte[] image, String imageUrl) {
		try {
			QOpenService service = QOpenService.createInstance(
					Integer.parseInt(authInfo.getAppKey()),
					authInfo.getAppSecret());
			Map<String, String> map = new HashMap<String, String>();
			map.put("appid", authInfo.getAppKey());
			map.put("openid", authInfo.getTpIdentity());
			map.put("openkey", authInfo.getToken());
			map.put("title", title);
			map.put("source", "3");
			// if (StringUtils.isNotEmpty(text)) {
			// map.put("summary", text);
			// }
			if (StringUtils.isNotEmpty(imageUrl)) {
				map.put("images", imageUrl);
			}
			QOpenResult result = service.feed(map);
			if (result == null || StringUtils.isEmpty(result.getValue("ret"))
					|| result.getValue("ret").equals("null")
					|| 0 != result.getIntValue("ret")) {
				log.error("q+ feed is error openid=" + authInfo.getTpIdentity()
						+ "result:" + result);
			}
		} catch (Exception e) {
			log.error("Qplus  sendMessage is error.", e);
		}
	}

	@Override
	public void inviteMessage(AuthInfo authInfo, String text, byte[] image,
			List<String> fuids) {

	}

	@Override
	public void notifyMessage(AuthInfo authInfo, String[] fuids, String text) {

	}

	@Override
	public void push(String openid, String text, String link) {
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
		bean.setCustomize(link);

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
				log.error("QplusSendTask TpIdentity:" + openid + " errorcode:"
						+ result);
			}
		} catch (IOException e) {
			log.error("QplusSendTask is error ", e);
		}
	}

}
