package com.juzhai.cms.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.juzhai.cms.service.IStaticVersionService;
import com.juzhai.core.util.StaticUtil;

@Service
public class StaticVersionService implements IStaticVersionService {

	@Override
	public void updateVersion() {
		Map<String, String> versions = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(StaticUtil.JS_FILE_ROOT_PATH)) {
			File jsRoot = new File(StaticUtil.JS_FILE_ROOT_PATH);
			loadFile(jsRoot, versions, File.separator + "js" + File.separator,
					"js");
		}
		if (StringUtils.isNotEmpty(StaticUtil.CSS_FILE_ROOT_PATH)) {
			File cssRoot = new File(StaticUtil.CSS_FILE_ROOT_PATH);
			loadFile(cssRoot, versions,
					File.separator + "css" + File.separator, "css");
		}
		// TODO 通知各个系统
		StaticUtil.STATIC_VERSION_MAP.putAll(versions);

	}

	private void loadFile(File directory, Map<String, String> versions,
			String filePath, String extension) {
		if (null != directory && directory.exists() && directory.canRead()) {
			File[] files = directory.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					loadFile(file, versions, filePath + file.getName()
							+ File.separator, extension);
				} else if (file.isFile()
						&& file.getName().endsWith("." + extension)) {
					versions.put(filePath + file.getName(),
							String.valueOf(file.lastModified() / 1000));
				}
			}
		}
	}

}
