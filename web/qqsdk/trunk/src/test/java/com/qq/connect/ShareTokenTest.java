package com.qq.connect;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class ShareTokenTest {

	@Test
	public void testAddShare() throws IOException, InvalidKeyException,
			NoSuchAlgorithmException {
		// F08332A45A14FEC1CFFA452E4E6FCACE|2317516318754136772|KhzgDDqF6eTmS5JA
		String openid = "F08332A45A14FEC1CFFA452E4E6FCACE";
		String oauth_token = "2317516318754136772";
		String oauth_token_secret = "KhzgDDqF6eTmS5JA";

		Map<String, String> shares = new HashMap<String, String>();
		shares.put("title", "我开始使用B3log了，快来看看吧!" + System.currentTimeMillis());
		shares.put("url",
				"http://www.b3log.org/?time=" + System.currentTimeMillis());
		shares.put("images", "http://www.b3log.org/images/logo.png)?time="
				+ System.currentTimeMillis());
		new ShareToken().addShare(oauth_token, oauth_token_secret, openid,
				shares);
	}

}
