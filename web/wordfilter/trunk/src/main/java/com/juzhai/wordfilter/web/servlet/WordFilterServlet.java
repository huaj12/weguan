/* 
 * WordFilterServlet.java * 
 * Copyright 2007 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.web.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.juzhai.wordfilter.core.Config;
import com.juzhai.wordfilter.core.Filter;
import com.juzhai.wordfilter.dataservice.IFilterLogService;
import com.juzhai.wordfilter.web.util.AppReference;
import com.juzhai.wordfilter.web.util.SpringContextUtil;

/**
 * 文字过滤系统servlet类.
 * 
 * @author xiaolin
 * 
 *         2008-2-29 create
 */
public class WordFilterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		Filter.init();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// doPost(request,response);
		/**
		 * this application doesn't support GET request.
		 */
		response.setContentType("text/plain");
		Writer writer = null;
		try {
			writer = response.getWriter();
			writer.write(Config.RET_NoTxtParameter + "");
		} finally {
			if (writer != null) {
				writer.close();
			}
		}

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		InputStream is = request.getInputStream();
		byte[] txtData = new byte[1024];
		int count = 0;

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		// input.
		/**
		 * filter the parameter name part of body of request
		 */
		for (int i = 0; i < 4; i++) {
			is.read();
		}
		/**
		 * get data part of body of request
		 */
		while ((count = is.read(txtData)) != -1) {
			output.write(txtData, 0, count);
		}
		txtData = output.toByteArray();

		/**
		 * get all parameters from request object.
		 */
		int application = getIntParameterFromRequest(request,
				WebConstant.PARAMETER_APP, 0);
		int userId = getIntParameterFromRequest(request,
				WebConstant.PARAMETER_UID, 0);
		String ip = request.getParameter(WebConstant.PARAMETER_IP);
		String agent = request.getParameter(WebConstant.PARAMETER_AGENT);

		/**
		 * the code below avoids the condition that the data of parameter(txt)
		 * was sent using GET method.
		 */
		// if(txtData==null)
		// {
		// txtData=new byte[0];
		// }
		// if(txtData.length==0)
		// {
		// String tmpTxt=request.getParameter(WebConstant.PARAMETER_TXT);
		// if(tmpTxt!=null)
		// {
		// txtData=tmpTxt.getBytes("ISO-8859-1");
		// }
		//
		// }
		/**
		 * call check method
		 */
		int result = Filter.getInstance().Check(txtData, userId, ip, agent,
				application);

		if (result != Config.RET_Pass) // illegal operation
			processIllegal(result, txtData, userId, ip, agent, application);
		/**
		 * write result to client
		 */

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		Writer writer = null;
		try {
			writer = response.getWriter();
			writer.write(result + "");
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
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
		try {
			data[4] = new String(content, "GBK");
		} catch (UnsupportedEncodingException e) {
			data[4] = new String(content);
		}
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

	/**
	 * get the integer value from request,according to name.
	 * 
	 * @param request
	 *            --request object
	 * @param name
	 *            --the name of data placed in request object.
	 * @param defaultValue
	 *            --it'll return this value if the data according to name can't
	 *            be transformed to integer value.
	 */
	private int getIntParameterFromRequest(HttpServletRequest request,
			String name, int defaultValue) {
		try {
			return Integer.parseInt(request.getParameter(name));
		} catch (Exception e) {
			return defaultValue;
		}
	}
}
