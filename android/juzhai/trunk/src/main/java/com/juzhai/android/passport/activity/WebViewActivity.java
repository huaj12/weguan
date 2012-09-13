/**
 * 
 */
package com.juzhai.android.passport.activity;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.juzhai.android.R;

/**
 * @author kooks
 * 
 */
public class WebViewActivity extends Activity {
	private WebView webView;
	private ProgressDialog progressDialog;
	private String toLoginUrl = "http://m.51juzhai.com/passport/tpLogin/";
	private String webAccessUrl = "http://www.51juzhai.com/web/access/";
	private String mAccessUrl = "http://m.51juzhai.com/passport/tpAccess/";
	private Intent intent;
	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		mContext = this;
		webView = (WebView) findViewById(R.id.webView);
		Button backBt = (Button) findViewById(R.id.back);
		backBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WebViewActivity.this.finish();
			}
		});
		WebSettings setting = webView.getSettings();
		setting.setJavaScriptCanOpenWindowsAutomatically(true);
		setting.setJavaScriptEnabled(true);
		String tpId = getIntent().getStringExtra("tpId");
		progressDialog = ProgressDialog
				.show(this, "正在打开页面", "请稍等", true, false);
		webView.setWebViewClient(new Webclient());
		webView.loadUrl(toLoginUrl + tpId);
	}

	private class Webclient extends WebViewClient {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			if (url != null && url.startsWith(webAccessUrl)) {
				login(url);
			} else {
				super.onPageStarted(view, url, favicon);
			}
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// 第一次载入页面完成取消等待框
			if (progressDialog != null) {
				progressDialog.dismiss();
				progressDialog = null;
			}
		}

	}

	/**
	 * 找到登录请求
	 * 
	 * @param url
	 */
	private void login(String url) {
		url = url.replaceAll(webAccessUrl, mAccessUrl);
		new AsyncTask<String, Integer, String>() {
			@Override
			protected String doInBackground(String... params) {
				String loginUrl = params[0];
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setAccept(Collections
						.singletonList(new MediaType("application", "json")));
				HttpEntity<Object> requestEntity = new HttpEntity<Object>(
						requestHeaders);
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(
						new StringHttpMessageConverter());
				ResponseEntity<String> responseEntity = restTemplate.exchange(
						loginUrl, HttpMethod.GET, requestEntity, String.class);
				Log.i("debug", responseEntity.getBody());
				List<String> list = responseEntity.getHeaders().get(
						"Set-Cookie");
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				// 关闭等待框
				progressDialog.dismiss();
			}

			@Override
			protected void onPreExecute() {
				progressDialog = ProgressDialog.show(mContext, "正在登录",
						"请稍等...", true, false);
			}
		}.execute(url);
	}

}
