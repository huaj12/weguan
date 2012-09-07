package com.juzhai.verifycode.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.verifycode.bean.VerifyLevel;
import com.juzhai.verifycode.service.IVerifyCodeService;

@Controller
@RequestMapping("code")
public class VerifyCodeController {
	private final Log log = LogFactory.getLog(getClass());
	@Value("${verify.code.level}")
	private int verifyCodeLevel;
	@Autowired
	private IVerifyCodeService verifyCodeService;

	@ResponseBody
	@RequestMapping(value = "/getverifycode", method = RequestMethod.GET)
	public void getVerifyCode(String key, HttpServletResponse response) {
		ServletOutputStream sos = null;
		try {
			BufferedImage image = verifyCodeService.createVerifyCode(key,
					VerifyLevel.getVerifyLevel(verifyCodeLevel));
			if (image == null) {
				return;
			}
			// 禁止图像缓存。
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");
			sos = response.getOutputStream();
			ImageIO.write(image, "jpeg", sos);
			sos.flush();
		} catch (Exception e) {
			log.error("getVerifyCode is error", e);
		} finally {
			if (sos != null) {
				try {
					sos.close();
				} catch (IOException e) {
					sos = null;
				}
			}
		}

	}

}
