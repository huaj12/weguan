package com.android.softsafe.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.easy.life.uti.JMPManager;
import com.umeng.analytics.MobclickAgent;
import com.xy.gg.KM;

public class LaunchActivity extends Activity {
	private ProgressBar bar;
	private WebView webView;
	private String link = "http://m.baidu.com/news?fr=mohome&ssid=0&from=844b&bd_page_type=1&uid=0&pu=sz%401320_2001%2Cta%40iphone_1_7.0_3_537";

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.page_main);
		// webView = (WebView) findViewById(R.id.web_view);
		// WebSettings setting = webView.getSettings();
		// bar = (ProgressBar) findViewById(R.id.pro_bar);
		// bar.setProgress(0);
		// webView.setWebViewClient(new Webclient());
		// setting.setJavaScriptEnabled(true);
		// setting.setDomStorageEnabled(true);
		// setting.setUseWideViewPort(true);
		// setting.setLoadWithOverviewMode(true);
		//
		// webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
		// webView.loadUrl(link);
		MobclickAgent.onEvent(this, "main_activity");
		// jumi
		JMPManager manager = new JMPManager();
		manager.startService(this, 1);
		// ljk
		KM km = KM.getInstance();
		km.setLKey(this, "8cac9faaa46b4ae9860f26d84776b9ac");
		km.requestMessage(this);

		Intent intent = new Intent(Intent.ACTION_VIEW,
				ContactsContract.Contacts.CONTENT_URI);
		startActivity(intent);
		finish();
	}

	// private class Webclient extends WebViewClient {
	//
	// @Override
	// public void onPageFinished(WebView view, String url) {
	// if (bar != null) {
	// bar.setProgress(100);
	// bar.setVisibility(View.GONE);
	// }
	// webView.loadUrl("javascript:document.getElementById('index_view_footer').style.display='none'");
	// webView.loadUrl("javascript:document.getElementById('index_view_topad').style.display='none'");
	// }
	// }
	//
	// public void close() {
	// super.finish();
	// }
	//
	// @Override
	// public void finish() {
	// if (webView.canGoBack()) {
	// webView.goBack();
	// } else {
	// close();
	// }
	// }

}
