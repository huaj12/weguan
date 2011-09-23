package com.juzhai.wordfilter.web.servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.juzhai.wordfilter.dataservice.ForbidService;
import com.juzhai.wordfilter.web.util.SpringContextUtil;

public class ForbidIpServlet extends HttpServlet {

	private static final long serialVersionUID = -8109358539335074299L;

	private ForbidService forbidService = (ForbidService) SpringContextUtil
			.getBean("forbidService");

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Writer writer = null;
		try {
			String ip = req.getParameter("ip");
			String releaseTime = req.getParameter("time");

			int result = forbidService.forbidIp(ip, releaseTime);

			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/plain");
			writer = resp.getWriter();
			writer.write("" + result);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
