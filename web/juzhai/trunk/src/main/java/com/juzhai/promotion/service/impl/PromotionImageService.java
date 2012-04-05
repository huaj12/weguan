package com.juzhai.promotion.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.act.exception.UploadImageException;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.image.manager.IImageManager;
import com.juzhai.core.model.MarkFont;
import com.juzhai.core.util.ImageUtil;
import com.juzhai.core.util.StaticUtil;
import com.juzhai.passport.service.IProfileImageService;
import com.juzhai.promotion.service.IPromotionImageService;

@Service
public class PromotionImageService implements IPromotionImageService {
	@Value("${upload.promotion.occasional.image.home}")
	private String webPromotionOccasionalImageHome;
	@Value("${web.promotion.occasional.image.path}")
	private String webPromotionOccasionalImagePath;
	@Value("${web.promotion.occasional.background.image}")
	private String webPromotionOccasionalBackgroundImage;
	@Autowired
	private IImageManager imageManager;
	@Autowired
	private IProfileImageService profileImageService;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;

	@Override
	public String getOccasionalImageUrl(long uid, String nickname,
			String address, String textBegin, String textEnd)
			throws UploadImageException {
		String logoPic = profileImageService.getUserImagePath(uid);
		String filename = ImageUtil.generateUUIDJpgFileName();
		List<MarkFont> list = new ArrayList<MarkFont>();
		list.add(new MarkFont(84, 70, new Font(Font.SERIF, Font.ITALIC, 16),
				Color.BLUE, nickname));
		list.add(new MarkFont(104, 100, new Font(Font.DIALOG_INPUT,
				Font.ITALIC, 20), Color.gray, textBegin));
		list.add(new MarkFont(250, 100, new Font(Font.DIALOG, Font.ITALIC, 25),
				Color.red, address));
		int tagerX = address.length() * 25;
		list.add(new MarkFont(250 + tagerX + 14, 100, new Font(Font.SERIF,
				Font.ITALIC, 20), Color.gray, textEnd));
		redisTemplate.opsForValue().increment(
				RedisKeyGenerator.genOccasionalId(), 1);
		// 在1000个目录里平均分布
		Long id = redisTemplate.opsForValue().increment(
				RedisKeyGenerator.genOccasionalId(), 0) % 1000;
		imageManager.markImage(
				logoPic,
				webPromotionOccasionalBackgroundImage,
				webPromotionOccasionalImageHome + File.separator
						+ ImageUtil.generateHierarchyImagePath(id, 0),
				filename, 198, 133, 0, list);
		String imageUrl = StaticUtil.u(webPromotionOccasionalImagePath
				+ ImageUtil.generateHierarchyImageWebPath(id, 0) + filename);
		return imageUrl;
	}
}
