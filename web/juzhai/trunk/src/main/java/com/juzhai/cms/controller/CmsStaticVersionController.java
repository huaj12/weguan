package com.juzhai.cms.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.util.StaticUtil;

@Controller
@RequestMapping("cms")
public class CmsStaticVersionController {

	@ResponseBody
	@RequestMapping(value = "/static/updateVersion", method = RequestMethod.GET)
	public String updateVersion(HttpServletRequest request, Model model) {
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
		return "success";
	}

	public void loadFile(File directory, Map<String, String> versions,
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
