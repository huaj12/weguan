/**
 * 
 */
package com.juzhai.android.passport.activity;

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
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.main.activity.MainTabActivity;
import com.juzhai.android.passport.exception.PassportException;
import com.juzhai.android.passport.service.IPassportService;
import com.juzhai.android.passport.service.impl.PassportService;

/**
 * @author kooks
 * 
 */
public class WebViewActivity extends NavigationActivity {
	private WebView webView;
	private String toLoginUrl = SystemConfig.BASEURL + "passport/tpLogin/";
	private String webAccessUrl = "/web/access/";
	private ProgressBar bar;
	private long tpId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// --------------设置NavigationBar--------------------
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.webview_title));
		setNavContentView(R.layout.web_view);
		// --------------设置NavigationBar--------------------
		tpId = Long.valueOf(getIntent().getStringExtra("tpId"));
		webView = (WebView) findViewById(R.id.web_view);
		WebSettings setting = webView.getSettings();
		// setting.setJavaScriptCanOpenWindowsAutomatically(true);
		setting.setJavaScriptEnabled(true);
		bar = (ProgressBar) findViewById(R.id.pro_bar);
		bar.setProgress(0);
		webView.setWebViewClient(new Webclient());
		webView.loadUrl(toLoginUrl + tpId);
	}

	private class Webclient extends WebViewClient {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			if (url != null && url.contains(webAccessUrl)) {
				// 得到url后停止webview加载不然会重复调用login
				webView.stopLoading();
				IPassportService passportService = new PassportService();
				String queryString = url.substring(url.indexOf("?"));
				try {
					passportService.tpLogin(WebViewActivity.this, tpId,
							queryString);// 跳转到登录成功页面
					clearStackAndStartActivity(new Intent(WebViewActivity.this,
							MainTabActivity.class));
				} catch (PassportException e) {
					if (e.getMessageId() > 0) {
						DialogUtils.showToastText(WebViewActivity.this,
								e.getMessageId());
					} else {
						DialogUtils.showToastText(WebViewActivity.this,
								e.getMessage());
					}
				}
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
}
