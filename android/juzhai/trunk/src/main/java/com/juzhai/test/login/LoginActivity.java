/**
 * 
 */
package com.juzhai.test.login;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.juzhai.android.R;

/**
 * @author kooks
 * 
 */
public class LoginActivity extends Activity {
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		webView = (WebView) findViewById(R.id.webView);
		Button loginBt = (Button) findViewById(R.id.loginweibo);
		loginBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				webView.setWebViewClient(new WebViewClientTest());
				// 支持js
				webView.getSettings().setJavaScriptEnabled(true);
				webView.loadUrl("http://www.51juzhai.com/m/passport/tpLogin/7");
			}
		});
	}

	private class WebViewClientTest extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.i("debug", url);
			// 在这里拦截请求
			// 不再网上冒泡了
			return true;
		}

	}
}
