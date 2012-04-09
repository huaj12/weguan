package com.juzhai.verifycode.bean;

import java.awt.image.BufferedImage;

public class VerifyCode {
	private String code;
	private BufferedImage image;

	public VerifyCode(String code, BufferedImage image) {
		this.image = image;
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
