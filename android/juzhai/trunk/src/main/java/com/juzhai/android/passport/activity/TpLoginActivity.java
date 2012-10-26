/**
 * 
 */
package com.juzhai.android.passport.activity;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.util.StringUtils;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.juzhai.android.R;
import com.juzhai.android.core.SystemConfig;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.passport.task.BindAuthorizeTask;
import com.juzhai.android.passport.task.ExpiredAuthorizeTask;
import com.juzhai.android.passport.task.LoginAuthorizeTask;

/**
 * @author kooks
 * 
 */
public class TpLoginActivity extends NavigationActivity {
	private String tpLoginUrl = "passport/tpLogin/";
	private String tpBindUrl = "passport/authorize/bind/";
	private String tpExpiredUrl = "passport/authorize/expired/";
	private String loginAccessUrl = "/web/access/";
	private String expiredAccessUrl = "/authorize/access/";
	private String bindAccessUrl = "/authorize/bindAccess/";
	private ProgressBar bar;
	private long tpId;
	private int authorizeType;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// --------------设置NavigationBar--------------------
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.webview_title));
		setNavContentView(R.layout.page_web_view);
		// --------------设置NavigationBar--------------------
		tpId = getIntent().getLongExtra("tpId", 0L);
		authorizeType = getIntent().getIntExtra("authorizeType", 0);
		SystemConfig config = (SystemConfig) getApplication();
		String url = null;
		if (authorizeType == 1) {
			url = config.getBaseUrl() + tpExpiredUrl + tpId;
		} else if (authorizeType == 2) {
			url = config.getBaseUrl() + tpBindUrl + tpId;
		} else {
			url = config.getBaseUrl() + tpLoginUrl + tpId;
		}
		if (!StringUtils.hasText(url)) {
			return;
		}

		WebView webView = (WebView) findViewById(R.id.web_view);
		WebSettings setting = webView.getSettings();
		setting.setBuiltInZoomControls(true);
		setting.setJavaScriptEnabled(true);
		bar = (ProgressBar) findViewById(R.id.pro_bar);
		bar.setProgress(0);
		webView.setWebViewClient(new Webclient());
		webView.loadUrl(url);
	}

	private class Webclient extends WebViewClient {

		@Override
		public void onPageStarted(WebView webView, String url, Bitmap favicon) {
			URL requestURL = null;
			try {
				requestURL = new URL(url);
			} catch (MalformedURLException e1) {
				return;
			}
			if (requestURL.getPath().contains(loginAccessUrl)) {
				webView.stopLoading();
				new LoginAuthorizeTask(TpLoginActivity.this, tpId)
						.execute(requestURL.getQuery());
			} else if (requestURL.getPath().contains(expiredAccessUrl)) {
				webView.stopLoading();
				new ExpiredAuthorizeTask(TpLoginActivity.this, tpId)
						.execute(requestURL.getQuery());
			} else if (requestURL.getPath().contains(bindAccessUrl)) {
				webView.stopLoading();
				new BindAuthorizeTask(TpLoginActivity.this, tpId)
						.execute(requestURL.getQuery());
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
