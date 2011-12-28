package com.juzhai.notice.controller.website;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.notice.service.INoticeService;

@Controller
@RequestMapping(value = "notice")
public class NoticeController extends BaseController {

	@Autowired
	private INoticeService noticeService;

	@RequestMapping(value = "/nums", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult noticeNums(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		result.setResult(noticeService.getAllNoticeNum(context.getUid()));
		return result;
	}
}
