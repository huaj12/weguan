package com.juzhai.download.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.download.service.IDownloadService;

@Service
public class DownloadService implements IDownloadService {
	@Value("${local.ios.download.file}")
	private String localIosDownloadFile;
	@Value("local.android.download.url")
	private String localAndroidDownloadUrl;
	@Value("appstore.download.url")
	private String appstoreDownloadUrl;

	@Override
	public File getIOSFile() {
		return new File(localIosDownloadFile);
	}

	@Override
	public String getAndroidWebPath() {
		return localAndroidDownloadUrl;
	}

	@Override
	public String getIOSWebPath() {
		return appstoreDownloadUrl;
	}

}
