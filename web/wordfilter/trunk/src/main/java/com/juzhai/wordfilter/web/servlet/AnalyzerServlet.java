/* 
 * Analyzer.java * 
 * Copyright 2008 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.web.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.juzhai.wordfilter.web.util.FilterAnalyzer;
import com.juzhai.wordfilter.web.util.Token;

/**
 * Spam text analyzer.
 * 
 * @author xiaolin
 * 
 *         2008-5-15 create
 */
public class AnalyzerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		FilterAnalyzer.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		byte[] txtData = null;

		String txt = request.getParameter("txt");
		if (txt != null) {
			txtData = txt.getBytes("GBK");
		} else {
			txtData = new byte[0];
		}

		List<Token> result = FilterAnalyzer.getInstance().analyze(txtData);

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		Writer writer = null;
		try {
			writer = response.getWriter();
			writer.write(buildResult(result));
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private String buildResult(List<Token> result)
			throws UnsupportedEncodingException {
		if (result == null || result.size() == 0)
			return "no spam words";

		int countScore = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < result.size(); i++) {
			Token t = result.get(i);
			countScore += t.getScore();
			String str = new String(t.getText(), "GBK");
			sb.append((i + 1) + "-->word:" + str + ",score:" + t.getScore())
					.append("\n");
		}
		sb.append("--------------------------------\n");
		sb.append("total score:" + countScore);

		return sb.toString();
	}
}
