package com.juzhai.wordfilter.web.servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.juzhai.wordfilter.dataservice.ForbidService;
import com.juzhai.wordfilter.web.util.SpringContextUtil;

public class ForbidUserServlet extends HttpServlet {

	private static final long serialVersionUID = -1524281359981892791L;

	private ForbidService forbidService = (ForbidService) SpringContextUtil
			.getBean("forbidService");

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Writer writer = null;
		try {
			String userId = req.getParameter("userId");
			String releaseTime = req.getParameter("time");
			int result = forbidService.forbidUser(userId, releaseTime);

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

}
