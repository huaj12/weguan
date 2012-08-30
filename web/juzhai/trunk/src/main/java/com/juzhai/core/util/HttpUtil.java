package com.juzhai.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpUtil {
	public static String getUrl(String url) throws IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet method = new HttpGet(url);
		HttpResponse res = null;
		try {
			res = httpclient.execute(method);
			String content = getHtml(res, "UTF-8", false);
			return content;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	private static String getHtml(HttpResponse res, String encode,
			Boolean breakLine) throws IOException {
		breakLine = (breakLine == null) ? false : breakLine;
		InputStream input = null;
		StatusLine status = res.getStatusLine();
		if (status.getStatusCode() != 200) {
			throw new IOException("StatusCode !=200");
		}
		if (res.getEntity() == null) {
			return "";
		}
		input = res.getEntity().getContent();
		InputStreamReader reader = new InputStreamReader(input, encode);
		BufferedReader bufReader = new BufferedReader(reader);
		String tmp = null, html = "";
		while ((tmp = bufReader.readLine()) != null) {
			html += tmp + (breakLine ? "\r\n" : "");
		}
		if (input != null) {
			input.close();
		}
		return html;
	}
}
