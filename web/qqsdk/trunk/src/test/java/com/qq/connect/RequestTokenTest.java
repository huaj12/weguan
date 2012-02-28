package com.qq.connect;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class RequestTokenTest {

	@Test
	public void testGetRequestToken() throws IOException, InvalidKeyException,
			NoSuchAlgorithmException {
		final RequestToken rt = new RequestToken("app_key", "app_secret");
		// System.out.println(html);
		// assertThat(html, startsWith("oauth_token="));
	}

}
