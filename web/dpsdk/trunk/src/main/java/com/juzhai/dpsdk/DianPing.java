package com.juzhai.dpsdk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.juzhai.dpsdk.exception.DianPingException;
import com.juzhai.dpsdk.http.HttpClient;
import com.juzhai.dpsdk.model.Configuration;
import com.juzhai.dpsdk.model.Paging;
import com.juzhai.dpsdk.model.PostParameter;

public class DianPing implements java.io.Serializable {

	private static final long serialVersionUID = 7870107824621633045L;

	protected HttpClient client = new HttpClient();

	private String appkey;
	private String appSecret;

	private String baseURL = Configuration.getScheme() + "api.dianping.com/"
			+ Configuration.getCilentVersion() + "/";

	public DianPing(String appkey, String appSecret) {
		this.appkey = appkey;
		this.appSecret = appSecret;
		client.setAppkey(appkey);
		client.setAppSecret(appSecret);
	}

	public HttpClient getClient() {
		return this.client;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	protected String getEncodeUrl(String url, PostParameter[] params)
			throws DianPingException {
		if (null != params && params.length > 0) {
			String encodedParams = HttpClient.encodeParameters(params);
			if (-1 == url.indexOf("?")) {
				url += "?" + encodedParams;
			} else {
				url += "&" + encodedParams;
			}
		}
		return url;
	}

	protected PostParameter[] generateParameterArray(Map<String, String> parames)
			throws DianPingException {
		PostParameter[] array = new PostParameter[parames.size()];
		int i = 0;
		for (String key : parames.keySet()) {
			if (parames.get(key) != null) {
				array[i] = new PostParameter(key, parames.get(key));
				i++;
			}
		}
		return array;
	}

	protected String getEncodeUrl(String url, PostParameter[] params,
			Paging paging) throws DianPingException {
		if (null != paging) {
			List<PostParameter> pagingParams = new ArrayList<PostParameter>(4);
			if (-1 != paging.getPage()) {
				pagingParams.add(new PostParameter("page", String
						.valueOf(paging.getPage())));
			}
			if (-1 != paging.getCount()) {
				pagingParams.add(new PostParameter("limit", String
						.valueOf(paging.getCount())));
			}
			PostParameter[] newparams = null;
			PostParameter[] arrayPagingParams = pagingParams
					.toArray(new PostParameter[pagingParams.size()]);
			if (null != params) {
				newparams = new PostParameter[params.length
						+ pagingParams.size()];
				System.arraycopy(params, 0, newparams, 0, params.length);
				System.arraycopy(arrayPagingParams, 0, newparams,
						params.length, pagingParams.size());
			} else {
				if (0 != arrayPagingParams.length) {
					String encodedParams = HttpClient
							.encodeParameters(arrayPagingParams);
					if (-1 != url.indexOf("?")) {
						url += "&appkey=" + appkey + "&" + encodedParams;
					} else {
						url += "?appkey=" + appkey + "&" + encodedParams;
					}
				}
			}
			return getEncodeUrl(url, newparams);
		} else {
			return getEncodeUrl(url, params);
		}
	}
}
