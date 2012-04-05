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
import com.juzhai.core.image.bean.MarkFont;
import com.juzhai.core.image.manager.IImageManager;
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
	@Value("${promotion.image.directory}")
	private long promotionImageDirectory;
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
		// TODO (done) 下面两次调用redis，你认为你获取到的值会是一个什么值？是不是你刚刚加完的那个值呢？考虑一下
		// TODO (done) 变量命名不当
		Long directoryName = redisTemplate.opsForValue().increment(
				RedisKeyGenerator.genOccasionalId(), 1)
				% promotionImageDirectory;

		// TODO (done) 限定死0-999，不用再根据算法切分目录了
		imageManager.markImage(logoPic, webPromotionOccasionalBackgroundImage,
				webPromotionOccasionalImageHome + File.separator
						+ directoryName, filename, 198, 133, 0, list);
		String imageUrl = StaticUtil.u(webPromotionOccasionalImagePath
				+ directoryName + filename);
		return imageUrl;
	}
}
