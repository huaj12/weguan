/* 
 * AdminServlet.java * 
 * Copyright 2007 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.web.servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.juzhai.wordfilter.core.Config;
import com.juzhai.wordfilter.dataservice.ISpamDataService;
import com.juzhai.wordfilter.web.util.SpringContextUtil;

/**
 * server for administrator,updating data cached in memory.
 * 
 * @author xiaolin
 * 
 *         2008-3-6 create
 */
public class AdminServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(AdminServlet.class);

	private static final long serialVersionUID = 1L;

	private static final int ACTION_TYPE_UPDATE = 0;
	private static final int ACTION_TYPE_RECORDLOG = 1;
	private static final int ACTION_TYPE_PRINTLOG = 2;

	@Override
	public void init() {
		((ISpamDataService) SpringContextUtil.getBean("spamDataService"))
				.launchTask();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tmpType = request.getParameter("type");
		int type = 0;
		boolean isSuccess = true;
		try {
			type = Integer.parseInt(tmpType);
		} catch (Exception e) {
			type = ACTION_TYPE_UPDATE;
		}
		if (type == ACTION_TYPE_RECORDLOG) {
			Config.isRecordLog = true;
			if (logger.isInfoEnabled()) {
				logger.info("Log Mode was changed : recording to database");
			}
		} else if (type == ACTION_TYPE_PRINTLOG) {
			Config.isRecordLog = false;
			if (logger.isInfoEnabled()) {
				logger.info("Log Mode was changed : printing to file");
			}
		} else {
			isSuccess = ((ISpamDataService) SpringContextUtil
					.getBean("spamDataService")).updateSpamData();
		}

		response.setContentType("text/plain");
		Writer writer = null;
		try {
			writer = response.getWriter();
			writer.write(isSuccess ? "1" : "-1");
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

}
