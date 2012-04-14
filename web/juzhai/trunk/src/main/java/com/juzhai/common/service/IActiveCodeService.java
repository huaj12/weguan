package com.juzhai.common.service;

import com.juzhai.common.bean.ActiveCodeType;

public interface IActiveCodeService {

	/**
	 * 生成激活嘛供使用
	 * 
	 * @param uid
	 * @param activeCodeType
	 * @return
	 */
	String generateActiveCode(long uid, ActiveCodeType activeCodeType);

	// /**
	// * 检查并删除
	// *
	// * @param uid
	// * @param activeCodeType
	// * @return
	// */
	// boolean checkAndDel(long uid, String code, ActiveCodeType
	// activeCodeType);

	/**
	 * 检查
	 * 
	 * @param uid
	 * @param code
	 * @param activeCodeType
	 * @return
	 */
	long check(String code, ActiveCodeType activeCodeType);

	/**
	 * 删除
	 * 
	 * @param code
	 * @return
	 */
	void del(String code);

	/**
	 * 删除过期的
	 */
	void delExpired();
}
