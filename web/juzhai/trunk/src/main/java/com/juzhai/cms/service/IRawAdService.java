package com.juzhai.cms.service;

import org.springframework.web.multipart.MultipartFile;

import com.juzhai.act.model.RawAd;
import com.juzhai.cms.exception.RawAdInputException;

public interface IRawAdService {
	/**
	 * 导入团购信息
	 * @param rawAd 导入的txt文件
	 * @return 成功导入的数目
	 * @throws RawAdInputException
	 */
	int importAd(MultipartFile rawAd) throws RawAdInputException;
	
	void createRawAd(RawAd rawAd);
	
	boolean isUrlExist(String md5Link);
}
