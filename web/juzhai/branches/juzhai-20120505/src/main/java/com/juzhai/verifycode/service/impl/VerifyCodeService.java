package com.juzhai.verifycode.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.UUID;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.verifycode.bean.VerifyLevel;
import com.juzhai.verifycode.service.IVerifyCodeService;

@Service
public class VerifyCodeService implements IVerifyCodeService {
	private final Log log = LogFactory.getLog(getClass());
	private static final String chars = "0123456789abcdefghjklmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";
	private static final int width = 80;
	private static final int height = 30;
	@Value("${verify.code.expire.time}")
	private int verifyCodeExpireTime;
	@Autowired
	private MemcachedClient memcachedClient;

	@Override
	public BufferedImage createVerifyCode(String key, VerifyLevel level) {
		if (level == null) {
			level = VerifyLevel.LEVEL1;
		}
		Graphics g = null;
		try {
			if (StringUtils.isEmpty(key)) {
				return null;
			}
			Object obj = memcachedClient.get(key);
			if (null == obj) {
				return null;
			}
			int charsLength = chars.length();
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			g = image.getGraphics();
			Random random = new Random();
			g.setColor(getRandColor(200, 250));
			g.fillRect(0, 0, width, height);
			StringBuilder sRand = new StringBuilder();
			String[] fontNames = { Font.SANS_SERIF, Font.DIALOG,
					Font.DIALOG_INPUT, Font.MONOSPACED };
			for (int i = 0; i < 4; i++) {
				g.setFont(new Font(fontNames[random.nextInt(3)], Font.ITALIC,
						height));
				char rand = chars.charAt(random.nextInt(charsLength));
				sRand.append(rand);
				g.setColor(new Color(20 + random.nextInt(110), 20 + random
						.nextInt(110), 20 + random.nextInt(110)));
				g.drawString(String.valueOf(rand), 16 * i + random.nextInt(6)
						+ 3, height - random.nextInt(4));
			}

			g.setColor(getRandColor(160, 200));
			for (int i = 0; i < level.getLevel(); i++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(width);
				int yl = random.nextInt(width);
				g.drawLine(x, y, x + xl, y + yl);
			}
			memcachedClient.set(key, verifyCodeExpireTime, sRand.toString());
			return image;
		} catch (Exception e) {
			log.error("createVerifyCode is error", e);
			return null;
		} finally {
			if (g != null) {
				g.dispose();
			}
		}

	}

	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	@Override
	public String getVerifyCodeKey() {
		String key = UUID.randomUUID().toString();
		try {
			memcachedClient.add(key, verifyCodeExpireTime, "");
		} catch (Exception e) {
			log.error("getVerifyCodeKey is error", e);
			return null;
		}
		return key;
	}

	@Override
	public boolean verify(String key, String input) {
		try {
			String str = memcachedClient.get(key);
			if (StringUtils.isEmpty(str)) {
				return false;
			}
			if (StringUtils.isEmpty(input)) {
				return false;
			}
			if (str.equalsIgnoreCase(input.trim())) {
				memcachedClient.delete(key);
				return true;
			}
		} catch (Exception e) {
			log.error("isVerifyCode is error", e);
		}
		return false;
	}
}
