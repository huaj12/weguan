package com.juzhai.download.service;

import java.io.File;

public interface IDownloadService {
	/**
	 * 获取ipa文件
	 * 
	 * @return
	 */
	File getIOSFile();

	/**
	 * 获取android web地址
	 * 
	 * @return
	 */
	String getAndroidWebPath();

	/**
	 * 获取ios web地址
	 * 
	 * @return
	 */
	String getIOSWebPath();
}
