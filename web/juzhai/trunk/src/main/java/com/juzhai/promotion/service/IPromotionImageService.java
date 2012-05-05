package com.juzhai.promotion.service;

import com.juzhai.core.exception.UploadImageException;

public interface IPromotionImageService {
	/**
	 * 获取加水印的图片地址
	 * 
	 * @param tagerUid
	 * @param nickname
	 * @param address
	 * @return
	 */
	String getOccasionalImageUrl(long tagerUid, String nickname,
			String address, String textBegin, String textEnd)
			throws UploadImageException;
}
