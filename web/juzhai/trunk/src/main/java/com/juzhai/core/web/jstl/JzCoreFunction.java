package com.juzhai.core.web.jstl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.juzhai.cms.bean.SizeType;
import com.juzhai.core.util.ImageUtil;
import com.juzhai.core.util.StaticUtil;
import com.juzhai.core.util.TextTruncateUtil;

public class JzCoreFunction {

	/**
	 * 静态资源路径生成
	 * 
	 * @param uri
	 * @return
	 */
	public static String u(String uri) {
		return StaticUtil.u(uri);
	}

	/**
	 * 截取字符串
	 * 
	 * @param originalText
	 * @param targetLength
	 * @param suffix
	 * @return
	 */
	public static String truncate(String originalText, int targetLength,
			String suffix) {
		return TextTruncateUtil.truncate(originalText, targetLength, suffix);
	}

	/**
	 * 比较时间
	 * 
	 * @param date
	 * @return
	 */
	public static boolean dateAfter(Date date) {
		return date.before(new Date());
	}

	/**
	 * 获取ActLogo
	 * 
	 * @param actId
	 * @param fileName
	 * @param size
	 * @return
	 */
	public static String actLogo(long actId, String fileName, int size) {
		SizeType sizeType = SizeType.getSizeTypeBySize(size);
		if (StringUtils.isEmpty(fileName) || actId <= 0 || sizeType == null) {
			return StaticUtil.u("/images/" + size + "_defaultActLogo.gif");
		} else {
			if (ImageUtil.isInternalUrl(fileName)) {
				return StaticUtil.u("/update/act"
						+ ImageUtil.generateHierarchyImageWebPath(actId,
								sizeType) + fileName);
			} else {
				return fileName;
			}
		}
	}
}
