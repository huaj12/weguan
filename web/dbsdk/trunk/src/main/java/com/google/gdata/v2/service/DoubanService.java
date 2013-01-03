package com.google.gdata.v2.service;

import com.google.gdata.v2.model.app.DoubanException;
import com.google.gdata.v2.utils.ErrorHandler;
import com.google.gdata.v2.utils.HttpManager;

/**
 * 
 * @author Zhibo Wei <uglytroll@dongxuexidu.com>
 */
public abstract class DoubanService {

	protected HttpManager client = null;

	protected DoubanService() {
		this.client = new HttpManager();
	}

	protected DoubanService(String accessToken) {
		this.client = new HttpManager(accessToken);
	}

	protected void setAccessToken(String accessToken) throws DoubanException {
		if (accessToken == null || accessToken.isEmpty()) {
			throw ErrorHandler.accessTokenNotSet();
		}
		this.client.setAccessToken(accessToken);
	}

}
