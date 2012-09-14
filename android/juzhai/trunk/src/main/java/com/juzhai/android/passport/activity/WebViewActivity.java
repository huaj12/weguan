/**
 * 
 */
package com.juzhai.android.passport.activity;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.juzhai.android.R;
import com.juzhai.android.core.SystemConfig;
import com.juzhai.android.passport.bean.UserCacheManager;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.model.UserResults;

/**
 * @author kooks
 * 
 */
public class WebViewActivity extends Activity {
	private WebView webView;
	private ProgressDialog progressDialog;
	// private String toLoginUrl = "http://m.51juzhai.com/passport/tpLogin/";
	// private String webAccessUrl = "http://www.51juzhai.com/web/access/";
	// private String mAccessUrl = "http://m.51juzhai.com/passport/tpAccess/";
	private String toLoginUrl = SystemConfig.BASEURL + "passport/tpLogin/";
	private String webAccessUrl = "http://test.51juzhai.com/web/access/";
	private String mAccessUrl = SystemConfig.BASEURL + "passport/tpAccess/";
	private Intent intent;
	private Context mContext;
	ProgressBar bar = null;

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
			Log.i("debug", url);
			if (url != null && url.startsWith(webAccessUrl)) {
				// 得到url后停止webview加载不然会重复调用login
				webView.stopLoading();
				login(url);
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
	public void login(String url) {
		url = url.replaceAll(webAccessUrl, mAccessUrl);
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(
				requestHeaders);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());
		ResponseEntity<UserResults> responseEntity = restTemplate.exchange(
				url, HttpMethod.GET, requestEntity, UserResults.class);
		UserResults results = responseEntity.getBody();
		if (!results.getSuccess()) {
			intent = new Intent(mContext, LoginActivity.class);
			intent.putExtra("errorInfo", results.getErrorInfo());
			startActivity(intent);
		} else {
			// 保存登录信息
			UserCacheManager.initUserCacheManager(responseEntity, this);
			// 跳转到登录成功页面
			intent = new Intent(mContext, LoginActivity.class);
			intent.putExtra("errorInfo", UserCache.getUserInfo().getNickname()
					+ "登录成功拉 l_token=" + UserCache.getlToken());
			startActivity(intent);
		}
		finish();
	}
}
