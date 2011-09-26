/*
 *The code is written by NaLi, All rights is reserved.
 */
package com.juzhai.wordfilter.service.impl;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.juzhai.wordfilter.core.Config;
import com.juzhai.wordfilter.core.Filter;
import com.juzhai.wordfilter.dataservice.IFilterLogService;
import com.juzhai.wordfilter.service.IWordFilterService;
import com.juzhai.wordfilter.web.util.AppReference;
import com.juzhai.wordfilter.web.util.SpringContextUtil;

/**
 * @author wujiajun Created on 2010-9-1
 */
public class WordFilterService implements IWordFilterService {

	private final Log log = LogFactory.getLog(getClass());

	public static final Charset GBK = Charset.forName("GBK");

	@Override
	public int wordFilter(int application, long userId, String ip,
			byte[] txtData) throws IOException {
		if (log.isDebugEnabled()) {
			log.debug("start filter word[" + txtData + "]");
		}
		// byte[] txtData = null;
		// if (null != txt && txt.length() > 0) {
		// txtData = txt.getBytes(GBK);
		// } else {
		// txtData = new byte[0];
		// }

		if (txtData == null) {
			txtData = new byte[0];
		}

		/**
		 * call check method
		 */
		Filter filter = Filter.getInstance();
		int result = filter.Check(txtData,
				Integer.valueOf(String.valueOf(userId)), ip, null, application);

		if (result != Config.RET_Pass) // illegal operation
			processIllegal(result, txtData,
					Integer.valueOf(String.valueOf(userId)), ip, null,
					application);
		return result;
	}

	/**
	 * this methde will be called if the operating is illegal,responsing for
	 * adding all data related with this operation into memory.
	 * 
	 * @param result
	 *            --result returnd by check method.
	 * @param content
	 *            --content of text need be checked
	 * @param userId
	 *            --userid
	 * @param ip
	 *            -- ip that ip address of host sent request
	 * @param agent
	 *            --the type of browser
	 * @param application
	 *            --indentifier of application
	 */
	private void processIllegal(int result, byte[] content, int userId,
			String ip, String agent, int application) {
		Object[] data = new Object[8];
		data[0] = ((AppReference) SpringContextUtil.getBean("appReference"))
				.getAppNameById(application);
		data[1] = userId;
		data[2] = ip == null ? "" : ip;
		data[3] = agent == null ? "" : agent;
		data[4] = new String(content, GBK);
		if (application < 0 || application >= Config.AppDo.length)
			data[5] = -1;
		else
			data[5] = Config.AppDo[application];

		data[6] = result;
		data[7] = 0; // default 0, -1: 1:

		IFilterLogService logService = (IFilterLogService) SpringContextUtil
				.getBean("filterLogService");
		logService.addLog(data);
	}

}
