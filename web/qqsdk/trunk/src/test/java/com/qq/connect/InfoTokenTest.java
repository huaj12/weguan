package com.qq.connect;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.junit.Test;

public class InfoTokenTest {

	@Test
	public void testGetInfo() throws IOException, InvalidKeyException,
			NoSuchAlgorithmException {
		Map<String, String> html = new InfoToken("app_key", "app_secret")
				.getInfo("13282187343341746703", "5pp3pzFaDWrqGBGB",
						"703608128");
		System.out.println(html);
	}

}
