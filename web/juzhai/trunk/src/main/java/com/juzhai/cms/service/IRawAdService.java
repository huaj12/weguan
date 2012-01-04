package com.juzhai.cms.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.juzhai.cms.exception.RawAdInputException;
import com.juzhai.cms.model.RawAd;

public interface IRawAdService {
	/**
	 * 导入团购信息
	 * 
	 * @param rawAd
	 *            导入的txt文件
	 * @return 成功导入的数目
	 * @throws RawAdInputException
	 */
	int importAd(MultipartFile rawAd) throws RawAdInputException;

	void createRawAd(RawAd rawAd);

	void updateRawAd(RawAd rawAd);

	boolean isUrlExist(String md5Link);
	
	RawAd getRawAd(String url);

	int countRawAd();

	List<RawAd> searchRawAd(String status, Long cityId, String source,
			int firstResult, int maxResults);

	int countSearchRawAd(String status, Long cityId, String source);

	List<RawAd> showRawAdList(int firstResult, int maxResults);

	RawAd getRawAd(long id);

}
