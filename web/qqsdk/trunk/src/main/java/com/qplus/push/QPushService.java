/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qplus.push;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author nbzhang
 */
public class QPushService extends QPlusService {

	public static final String VERSION = "1.0.1";

	public static final QPushService createInstance(int appid, String appsecret) {
		return new QPushService(appid, appsecret);
	}

	private QPushService(int appid, String appsecret) {
		super(appid, appsecret + "&");
	}

	public QPushResult push(final QPushBean bean) throws IOException {
		List<String> list1 = new ArrayList<String>(10);
		List<String> list2 = new ArrayList<String>(10);
		list1.add("APPID=" + this.appid);
		list1.add("NONCE=" + random());
		list1.add("TIME=" + intNow());
		list1.add("INSTANCEID=" + bean.getInstanceid());
		list1.add("OPTYPE=" + bean.getOptype());
		list1.add("NUM=" + bean.getNum());
		list2.addAll(list1);

		list1.add("QPLUSID=" + encode(bean.getQplusid()));
		list2.add("QPLUSID=" + bean.getQplusid());
		list1.add("PUSHMSGID=" + encode(bean.getPushmsgid()));
		list2.add("PUSHMSGID=" + bean.getPushmsgid());
		list1.add("TEXT=" + encode(bean.getText()));
		list2.add("TEXT=" + bean.getText());
		list1.add("APPCUSTOMIZE=" + encode(bean.getCustomize()));
		list2.add("APPCUSTOMIZE=" + bean.getCustomize());
		String str = createHttpParam(list1) + "&IDENTIFYSTRING="
				+ createSigValue("app_push&" + createHttpParam(list2));
		String content = send("http://tpns.qq.com/cgi-bin/app_push", str);
		return new QPushResult(content);
	}
}
