/* 
 * JVMInfoServlet.java * 
 * Copyright 2007 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.web.servlet;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 
 * @author xiaolin
 * 
 *         2008-3-13 create
 */
public class JVMInfoServlet extends HttpServlet {

	public static final String PARAM_UNIT = "unit"; // unit used to describe
													// memory size.

	public static final String UNIT_TYPE_B = "B"; // default value
	public static final String UNIT_TYPE_K = "K";
	public static final String UNIT_TYPE_M = "M";
	public static final String UNIT_TYPE_G = "G";

	public static final String delimiter = "|";

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String unit = validateUnit(request.getParameter(PARAM_UNIT));
		if (unit == null) {
			unit = UNIT_TYPE_B;
		}
		double freeMemory = getSuitableMemorySize(Runtime.getRuntime()
				.freeMemory(), unit);// unit is byte.
		double totalMemory = getSuitableMemorySize(Runtime.getRuntime()
				.totalMemory(), unit);// unit is byte.

		StringBuilder sb = new StringBuilder();
		sb.append(freeMemory).append(delimiter).append(totalMemory);
		/**
		 * write result to client
		 */

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		Writer writer = null;
		try {
			writer = response.getWriter();
			writer.write(sb.toString());
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private double getSuitableMemorySize(long originSize, String unit) {
		if (UNIT_TYPE_M.equals(unit)) {
			return round(((double) originSize / (1024 * 1024)), 3);
		} else if (UNIT_TYPE_K.equals(unit)) {
			return round(((double) originSize / (1024)), 3);
		} else if (UNIT_TYPE_G.equals(unit)) {
			return round(((double) originSize / (1024 * 1024 * 1024)), 3);
		} else
			return (double) originSize;

	}

	public double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * validate for unit,return default value(UNIT_TYPE_B) if unit is illegal.
	 */
	private String validateUnit(String unit) {
		if (unit == null)
			return UNIT_TYPE_B;
		unit = unit.toUpperCase();
		if (unit.equals(UNIT_TYPE_B) || unit.equals(UNIT_TYPE_M)
				|| unit.equals(UNIT_TYPE_K) || unit.equals(UNIT_TYPE_G)) {
			return unit;
		} else
			return UNIT_TYPE_B;
	}
}
