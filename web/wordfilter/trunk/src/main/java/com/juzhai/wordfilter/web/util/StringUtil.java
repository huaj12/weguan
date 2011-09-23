/* 
 * StringUtil.java * 
 * Copyright 2008 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.web.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * @author xiaolin
 * 
 *         2009-3-2 create
 */
public class StringUtil {

	public static int stringToInt(String stringValue, int defaultValue) {
		int intValue = defaultValue;
		if (stringValue != null) {
			try {
				intValue = Integer.parseInt(stringValue);
			} catch (NumberFormatException ex) {
				intValue = defaultValue;
			}
		}
		return intValue;
	}

	/**
	 * ���ݸ�����url����ȡ������Ϣ��
	 * 
	 * @param u
	 *            �����URL
	 * @return ������Ӧ���ݣ��ı�����
	 */
	public static String getRequest(String u) {
		if (u == null) {
			throw new RuntimeException("��Ч��·��");
		}
		try {
			URL url = new URL(u);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			httpURLConnection.setConnectTimeout(3000);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setReadTimeout(20000);
			httpURLConnection.connect();

			InputStream inputStream = (InputStream) httpURLConnection
					.getInputStream();

			StringBuffer result = new StringBuffer();
			int nextData = 0;
			while ((nextData = inputStream.read()) != -1) {
				result.append((char) nextData);
			}
			inputStream.close();

			httpURLConnection.disconnect();

			return result.toString();
		} catch (Exception e) {
			throw new RuntimeException("��ȡ��������Դʧ��:" + e, e);
		}
	}
}
