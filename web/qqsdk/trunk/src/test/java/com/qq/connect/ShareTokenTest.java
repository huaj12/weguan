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
		shares.put("images",
				"http://t2.qlogo.cn/mbloghead/a9d7a7ec3847e59b0954/180");
		shares.put("title", "我开始使用拒宅网了，快来看看吧!");
		shares.put("url", "http://www.51juzhai.com/home");
		shares.put("summary", "lalal");
		System.out.println(new ShareToken("100249114",
				"570f9120b30fb9e43a1080f6d0449336").addShare(
				"18187558463735926225", "gb7QvXuvKWMaDuq9",
				"7EAB62E8E1769DD371C93F114986E425", shares));
	}

}
