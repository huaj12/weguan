package com.qq.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class VerifyTest {

	@Test
	public void testGetInstance() {

	}

	@SuppressWarnings("static-access")
	@Test
	public void testVerifyOpenID() throws InvalidKeyException,
			UnsupportedEncodingException, NoSuchAlgorithmException {
		String openid = "openid";
		String timestamp = "timestamp";
		String oauth_signature = "oauth_signature";
		Verify verify = Verify.getInstance();
		assertThat(verify.verifyOpenID(openid, timestamp, oauth_signature,
				"app_secret"), is(false));
	}

}
