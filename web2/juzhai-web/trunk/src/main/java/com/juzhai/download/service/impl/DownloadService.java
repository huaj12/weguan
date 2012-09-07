package com.juzhai.download.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.download.service.IDownloadService;

@Service
public class DownloadService implements IDownloadService {
	@Value("${web.download.ios.path}")
	private String webDownloadIosPath;

	@Override
	public File getIOSFile() {
		return new File(webDownloadIosPath);
	}

}
