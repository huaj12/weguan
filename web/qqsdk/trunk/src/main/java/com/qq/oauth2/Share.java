package com.qq.oauth2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.qq.json.JSONException;
import com.qq.json.JSONObject;
import com.qq.oauth2.bean.ResultBean;
import com.qq.util.HttpClientUtils;

public class Share extends QQ {
	private static final long serialVersionUID = 5437848496352594021L;

	public Share(String token, String appkey, String sercret) {
		super(token, appkey, sercret, null);
	}

	/**
	 * 发送动态
	 * 
	 * @param uid
	 * @param shares
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 */
	public ResultBean feed(String uid, Map<String, String> shares)
			throws ClientProtocolException, IOException, JSONException {
		String url = baseURL + "share/add_share";
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("access_token", getToken()));
		parameters
				.add(new BasicNameValuePair("oauth_consumer_key", getAppkey()));
		parameters.add(new BasicNameValuePair("openid", uid));
		parameters.add(new BasicNameValuePair("format", "json"));
		for (Entry<String, String> entry : shares.entrySet()) {
			if (entry.getValue() != null && entry.getValue().length() > 0) {
				parameters.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}
		HttpPost sharePost = new HttpPost(url);
		sharePost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
		DefaultHttpClient httpclient = HttpClientUtils.getHttpClient();
		HttpResponse loginPostRes = httpclient.execute(sharePost);
		String result = HttpClientUtils.getHtml(loginPostRes, "UTF-8", false);
		return jsonToBean(result);
	}

	private ResultBean jsonToBean(String result) throws JSONException {
		ResultBean resultBean = new ResultBean();

		// 接口返回的数据json
		JSONObject jsonObjRoot = new JSONObject(result);
		// 接口返回错误的场合
		if (jsonObjRoot.getInt("ret") != 0) {
			// 设置错误标识为真
			resultBean.setError(true);
			// 设置错误编号
			resultBean.setErrorCode(jsonObjRoot.get("ret").toString());
			// 设置错误信息
			resultBean.setErrorMsg(jsonObjRoot.getString("msg"));
		}
		return resultBean;
	}
}
