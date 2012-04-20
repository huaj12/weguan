/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qplus;

import com.qplus.push.QPushBean;
import com.qplus.push.QPushResult;
import com.qplus.push.QPushService;

/**
 * 
 * @author nbzhang
 */
public class QPushTest {

	public static void main(String[] args) throws Throwable {
		int appid = 200254264;
		String appsecret = "42ZDsPatTguynWAH";
		final QPushService service = QPushService.createInstance(appid,
				appsecret);
		QPushBean bean = new QPushBean();
		bean.setNum(99); // 由App指定，一般展示在App图标的右上角。最大100
		bean.setInstanceid(0); // 桌面实例ID, 数字，目前建议填0
		bean.setOptype(1); // 展现方式: 1-更新内容直接进消息中心
		bean.setQplusid("4E24DF065C54172D08877D26892A21C3"); // 桌面ID，字符串，必填信息，且内容会被校验
		bean.setText("消息概要"); // 文本提示语 Utf8编码，最长90字节
		bean.setPushmsgid("123"); // 本次PUSH的消息ID，建议填写，可以为任意数字
		QPushResult result = service.push(bean);
		/**
		 * 
		 * 错误码: 0 - 处理成功，PUSH消息顺利到达PUSH服务中心 1 - 系统忙，参见提示信息“em“ 2 - Q+桌面KEY信息错误 3
		 * - 指定APPID、指定OP类型的PUSH频率受限 4 - 缺少OPTYPE信息 5 - Q+桌面KEY信息无效 6 - 其他错误信息
		 */
		System.out.println(result);

	}
}
