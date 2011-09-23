/* 
 * Analyzer.java * 
 * Copyright 2008 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.web.servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.juzhai.wordfilter.web.util.SpamInfo;

/**
 * Spam text analyzer.
 * 
 * @author xiaolin
 * 
 *         2008-5-15 create
 */
public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		SpamInfo.getInstance().getSpamWordString();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		Writer writer = null;
		try {
			writer = response.getWriter();
			writer.write(SpamInfo.getInstance().getSpamWordString());
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
}
