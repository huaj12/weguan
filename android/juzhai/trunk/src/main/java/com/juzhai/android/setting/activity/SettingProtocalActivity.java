package com.juzhai.android.setting.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.juzhai.android.R;
import com.juzhai.android.core.ApplicationContext;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;

public class SettingProtocalActivity extends NavigationActivity {

	private String protocalUrl = "about/protocal";
	private ProgressBar bar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.setting_cell_protocal));
		setNavContentView(R.layout.page_web_view);
		ApplicationContext config = (ApplicationContext) getApplication();
		WebView webView = (WebView) findViewById(R.id.web_view);
		WebSettings setting = webView.getSettings();
		setting.setBuiltInZoomControls(true);
		bar = (ProgressBar) findViewById(R.id.pro_bar);
		bar.setProgress(0);
		webView.setWebViewClient(new Webclient());
		webView.loadUrl(config.getBaseUrl() + protocalUrl);
	}

	private class Webclient extends WebViewClient {

		@Override
		public void onPageFinished(WebView view, String url) {
			if (bar != null) {
				bar.setProgress(100);
				bar.setVisibility(View.GONE);
			}
		}
	}
}
