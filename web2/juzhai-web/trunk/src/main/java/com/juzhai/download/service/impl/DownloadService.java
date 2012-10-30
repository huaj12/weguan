package com.juzhai.download.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.download.service.IDownloadService;

@Service
public class DownloadService implements IDownloadService {
	@Value("${web.download.ios.path}")
	private String webDownloadIosPath;
	@Value("download.android.web.path")
	private String downloadAndroidWebPath;
	@Value("download.ios.web.path")
	private String downloadIOSWebPath;

	@Override
	public File getIOSFile() {
		return new File(webDownloadIosPath);
	}

	@Override
	public String getAndroidWebPath() {
		return downloadAndroidWebPath;
	}

	@Override
	public String getIOSWebPath() {
		return downloadIOSWebPath;
	}

}
