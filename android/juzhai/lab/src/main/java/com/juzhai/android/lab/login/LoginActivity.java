/**
 * 
 */
package com.juzhai.android.lab.login;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.graphics.Bitmap;
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
				WebSettings setting = webView.getSettings();
				setting.setJavaScriptCanOpenWindowsAutomatically(true);
				setting.setJavaScriptEnabled(true);
				webView.loadUrl("http://m.51juzhai.com/passport/tpLogin/6");
			}
		});
	}

	private class WebViewClientTest extends WebViewClient {

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			Log.i("debug", url);
			if (url != null
					&& url.startsWith("http://www.51juzhai.com/web/access/")) {
				bind(url);
			}
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.i("debug", url);
			if (url != null
					&& url.startsWith("http://www.51juzhai.com/web/access/")) {
				bind(url);
				return true;
			}
			return false;
		}

		private void bind(String url) {
			// 在这里拦截请求
			// 不再网上冒泡了
			url = url.replaceAll("http://www.51juzhai.com/web/access/",
					"http://m.51juzhai.com/passport/tpAccess/");
			Log.i("debug", url);
			HttpHeaders requestHeaders = new HttpHeaders();
			HttpEntity<Object> requestEntity = new HttpEntity<Object>(
					requestHeaders);
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(
					new StringHttpMessageConverter());
			ResponseEntity<String> responseEntity = restTemplate.exchange(url,
					HttpMethod.GET, requestEntity, String.class);
			String results = responseEntity.getBody();
			Log.i("debug", results);
			for (Entry<String, List<String>> entry : requestEntity.getHeaders()
					.entrySet()) {
				Log.i("debug", entry.getKey());
				List<String> strs = responseEntity.getHeaders().get(
						entry.getKey());
				for (String str : strs) {
					Log.i("debug", str);
				}
			}

		}
	}
}
