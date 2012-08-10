package com.juzhai.cms.task;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.juzhai.passport.InitData;
import com.juzhai.passport.model.Thirdparty;
import com.qplus.push.QPushBean;
import com.qplus.push.QPushResult;
import com.qplus.push.QPushService;

public class QplusSendTask implements Runnable {
	private final Log log = LogFactory.getLog(getClass());
	private String openid;
	private String text;
	private String link;

	public QplusSendTask(String openid, String text, String link) {
		this.openid = openid;
		this.text = text;
		this.link = link;
	}

	@Override
	public void run() {
		//TODO (review) 为什么还是放在task里，第三方的服务，就写到platform里
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
