package com.juzhai.verifycode.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.juzhai.verifycode.bean.VerifyCode;
import com.juzhai.verifycode.bean.VerifyLevel;
import com.juzhai.verifycode.service.IVerifyCodeService;

@Service
public class VerifyCodeService implements IVerifyCodeService {
	private final Log log = LogFactory.getLog(getClass());
	private static final String chars = "0123456789abcdefghjklmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";
	private static final int width = 70;
	private static final int height = 25;

	@Override
	public VerifyCode createVerifyCode(VerifyLevel level) {
		if (level == null) {
			level = VerifyLevel.LEVEL1;
		}
		Graphics g = null;
		try {
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
			return new VerifyCode(sRand.toString(), image);
		} catch (Exception e) {
			log.error("createVerifyCode is error", e);
			return null;
		} finally {
			g.dispose();
		}

	}

	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

}
