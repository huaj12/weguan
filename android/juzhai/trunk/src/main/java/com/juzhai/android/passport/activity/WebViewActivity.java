/**
 * 
 */
package com.juzhai.android.passport.activity;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
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
	private ProgressDialog progressDialog;

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
			URL requestURL = null;
			try {
				requestURL = new URL(url);
			} catch (MalformedURLException e1) {
				return;
			}
			if (requestURL.getPath().contains(webAccessUrl)) {
				// 得到url后停止webview加载不然会重复调用login
				webView.stopLoading();

				new AsyncTask<String, Integer, String>() {
					@Override
					protected String doInBackground(String... params) {
						IPassportService passportService = new PassportService();
						try {
							passportService.tpLogin(WebViewActivity.this, tpId,
									params[0]);// 跳转到登录成功页面
							return null;
						} catch (PassportException e) {
							if (e.getMessageId() > 0) {
								return WebViewActivity.this.getResources()
										.getString(e.getMessageId());
							} else {
								return e.getMessage();
							}
						}
					}

					@Override
					protected void onPostExecute(String errorInfo) {
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
						if (errorInfo != null && !errorInfo.equals("")) {
							DialogUtils.showToastText(WebViewActivity.this,
									errorInfo);
						} else {
							clearStackAndStartActivity(new Intent(
									WebViewActivity.this, MainTabActivity.class));
						}
					}

					@Override
					protected void onPreExecute() {
						if (progressDialog != null) {
							progressDialog.show();
						} else {
							progressDialog = ProgressDialog.show(
									WebViewActivity.this,
									getResources().getString(
											R.string.tip_loging),
									getResources().getString(
											R.string.please_wait), true, false);
						}
					}
				}.execute(requestURL.getQuery());
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
