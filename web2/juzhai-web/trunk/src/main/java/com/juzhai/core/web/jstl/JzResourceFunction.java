package com.juzhai.core.web.jstl;

import org.apache.commons.lang.StringUtils;

import com.juzhai.core.image.ImageUrl;
import com.juzhai.core.util.StaticUtil;
import com.juzhai.passport.InitData;
import com.juzhai.passport.model.Thirdparty;

public class JzResourceFunction {

	/**
	 * 静态资源路径生成
	 * 
	 * @param uri
	 * @return
	 */
	public static String u(String uri) {
		return StaticUtil.u(uri);
	}

	public static String ideaTempLogo(String fileName) {
		return ImageUrl.ideaTempLogo(fileName);
	}

	public static String postPic(long postId, long ideaId, String fileName,
			int size) {
		return ImageUrl.postPic(postId, ideaId, fileName, size);
	}

	public static String ideaPic(long ideaId, String fileName, int size) {
		return ImageUrl.ideaPic(ideaId, fileName, size);
	}

	public static String dialogContentPic(long dialogContentId,
			String fileName, int size) {
		return ImageUrl.dialogContentPic(dialogContentId, fileName, size);
	}

	/**
	 * 获取UserLogo
	 * 
	 * @param actId
	 * @param fileName
	 * @param size
	 * @return
	 */
	public static String userLogo(long userid, String fileName, int size) {
		return ImageUrl.userLogo(userid, fileName, size);
	}

	/**
	 * 第三方用户首页
	 * 
	 * @param tpIdentity
	 * @param tpId
	 * @return
	 */
	public static String tpHomeUrl(String tpIdentity, long tpId) {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if (null != tp && StringUtils.isNotEmpty(tp.getUserHomeUrl())
				&& StringUtils.isNotEmpty(tpIdentity)) {
			return tp.getUserHomeUrl().replace("{0}", tpIdentity);
		}
		return "#";
	}

}
