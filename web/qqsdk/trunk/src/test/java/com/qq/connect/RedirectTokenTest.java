package com.qq.connect;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.junit.Test;

public class RedirectTokenTest {

	@Test
	public void testGetRedirectURL() throws InvalidKeyException,
			NoSuchAlgorithmException, IOException {
		final RequestToken rt = new RequestToken("app_key", "app_secret");
		Map<String, String> tokens = rt.getRequestToken();
		RedirectToken ret = new RedirectToken("app_key", "app_secret");
		System.out.println(ret.getRedirectURL(tokens, "call_back"));
	}

}
