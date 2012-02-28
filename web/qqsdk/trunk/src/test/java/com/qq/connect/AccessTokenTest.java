package com.qq.connect;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class AccessTokenTest {

	@Test
	public void testGetAccessToken() throws IOException, InvalidKeyException,
			NoSuchAlgorithmException {
		new AccessToken("app_key", "app_secret").getAccessToken(
				"13282187343341746703", "5pp3pzFaDWrqGBGB", "703608128");
	}

}
