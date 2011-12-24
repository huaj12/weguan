package com.juzhai.core.web.jstl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.juzhai.cms.bean.SizeType;
import com.juzhai.core.util.ImageUtil;
import com.juzhai.core.util.StaticUtil;
import com.juzhai.passport.InitData;
import com.juzhai.passport.model.Thirdparty;

@Component
public class JzResourceFunction {

	@Value("${web.act.image.path}")
	private static String webActImagePath;
	@Value("${web.user.image.path}")
	private static String webUserImagePath;

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
				return StaticUtil.u(webActImagePath
						+ ImageUtil.generateHierarchyImageWebPath(actId,
								sizeType) + fileName);
			} else {
				return fileName;
			}
		}
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
		if (userid == 4652L) {
			System.out.println(fileName + "******");
		}
		if (ImageUtil.isInternalUrl(fileName)) {
			SizeType sizeType = SizeType.getSizeTypeBySize(size);
			if (StringUtils.isEmpty(fileName) || userid <= 0
					|| sizeType == null) {
				return StaticUtil.u("/images/" + size + "_defaultUserLogo.gif");
			} else {
				return StaticUtil.u(webUserImagePath
						+ ImageUtil.generateHierarchyImageWebPath(userid,
								sizeType) + fileName);
			}
		} else {
			return fileName;
		}

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
