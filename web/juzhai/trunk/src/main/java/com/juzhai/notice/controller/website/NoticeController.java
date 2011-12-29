package com.juzhai.notice.controller.website;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.notice.bean.NoticeType;
import com.juzhai.notice.model.SysNotice;
import com.juzhai.notice.service.INoticeService;
import com.juzhai.notice.service.ISysNoticeService;

@Controller
@RequestMapping(value = "notice")
public class NoticeController extends BaseController {

	@Autowired
	private INoticeService noticeService;
	@Autowired
	private ISysNoticeService sysNoticeService;
	@Value("${web.sys.notice.max.rows}")
	private int webSysNoticeMaxRows;

	@RequestMapping(value = "/nums", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult noticeNums(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		result.setResult(noticeService.getAllNoticeNum(context.getUid()));
		return result;
	}

	@RequestMapping(value = "/sysNotices", method = RequestMethod.GET)
	public String showSysNotice(HttpServletRequest request, Model model)
			throws NeedLoginException {
		return pageSysNotice(request, model, 1);
	}

	@RequestMapping(value = "/sysNotices/{page}", method = RequestMethod.GET)
	public String pageSysNotice(HttpServletRequest request, Model model,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		PagerManager pager = new PagerManager(page, webSysNoticeMaxRows,
				sysNoticeService.countSysNoticeByUid(context.getUid()));
		List<SysNotice> list = sysNoticeService.listSysNoticeByUid(
				context.getUid(), pager.getFirstResult(), pager.getMaxResult());
		model.addAttribute("pager", pager);
		model.addAttribute("sysNoticeList", list);
		if (page <= 1) {
			noticeService.emptyNotice(context.getUid(), NoticeType.SYS_NOTICE);
		}
		return "web/notice/sys_notice";
	}
}