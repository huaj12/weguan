/*
 *The code is written by NaLi, All rights is reserved.
 */
package com.juzhai.wordfilter.service;

import java.io.IOException;

/**
 * @author wujiajun Created on 2010-9-1
 */
public interface IWordFilterService {

	/**
	 * 过滤词判断
	 * 
	 * @param application
	 *            应用ID（话题,投票...）
	 * @param userId
	 *            发表人ID
	 * @param ip
	 *            发表人的IP
	 * @param txt
	 *            发表内容
	 * @return 0：正常通过 <br />
	 *         -1：用户在黑名单中，不允许用户操作 <br />
	 *         -2：用户发帖太快 <br />
	 *         -3：ip被禁止 <br />
	 *         -4：对应该IP的主机发帖太快 <br />
	 *         -5：被禁止的浏览器类型 <br />
	 *         -6：文本太长 <br />
	 *         -7：连续的中文字符太长 <br />
	 *         -8：连续的非中文字符太长 <br />
	 *         -9：文本内容结构太窄 <br />
	 *         -10：过多的空字符 <br />
	 *         -11：文本内容中包含了不合法的词汇 <br />
	 *         -12：用户重复发帖过于频繁 <br />
	 *         -13：IP对应的主机重复发帖过于频繁 <br />
	 *         -14：因为用户之前重复发帖，而被禁止操作 <br />
	 *         -15：因为该IP对应主机重复发帖，而被禁止操作 <br />
	 *         -99：未知的应用
	 * @throws IOException
	 */
	int wordFilter(int application, long userId, String ip, byte[] txtData)
			throws IOException;
}
