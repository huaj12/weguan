/**
 * 
 */
package com.juzhai.android.passport.activity;

import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.juzhai.android.R;
import com.juzhai.android.core.SystemConfig;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.main.activity.MainTabActivity;
import com.juzhai.android.passport.bean.UserCacheManager;
import com.juzhai.android.passport.model.UserResults;

/**
 * @author kooks
 * 
 */
public class WebViewActivity extends NavigationActivity {
	private WebView webView;
	private String toLoginUrl = SystemConfig.BASEURL + "passport/tpLogin/";
	private String webAccessUrl = "http://test.51juzhai.com/web/access/";
	private String mAccessUrl = SystemConfig.BASEURL + "passport/tpAccess/";
	private Intent intent;
	private Context mContext;
	ProgressBar bar = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// --------------设置NavigationBar--------------------
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.webview_title));
		setNavContentView(R.layout.webview);
		// --------------设置NavigationBar--------------------

		mContext = this;
		webView = (WebView) findViewById(R.id.webView);
		WebSettings setting = webView.getSettings();
		setting.setJavaScriptCanOpenWindowsAutomatically(true);
		setting.setJavaScriptEnabled(true);
		String tpId = getIntent().getStringExtra("tpId");
		bar = (ProgressBar) findViewById(R.id.pro_bar);
		bar.setProgress(0);
		webView.setWebViewClient(new Webclient());
		webView.loadUrl(toLoginUrl + tpId);
	}

	private class Webclient extends WebViewClient {

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			if (url != null && url.startsWith(webAccessUrl)) {
				// 得到url后停止webview加载不然会重复调用login
				webView.stopLoading();
				login(url);
			}
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			if (bar != null) {
				bar.setProgress(100);
				bar.setVisibility(View.GONE);
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

		ResponseEntity<UserResults> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(url, UserResults.class);
		} catch (Exception e) {
			intent = new Intent(mContext, LoginActivity.class);
			intent.putExtra(
					"errorInfo",
					mContext.getResources().getString(
							R.string.system_internet_erorr));
			pushIntent(intent);
			popIntent();
			return;
		}
		UserResults results = responseEntity.getBody();
		if (!results.getSuccess()) {
			intent = new Intent(mContext, LoginActivity.class);
			intent.putExtra("errorInfo", results.getErrorInfo());
			pushIntent(intent);
			popIntent();
		} else {
			// 保存登录信息
			UserCacheManager.initUserCacheManager(responseEntity, this);
			// 跳转到登录成功页面
			intent = new Intent(mContext, MainTabActivity.class);
			pushIntent(intent);
			popAllIntent();
		}
	}
}
