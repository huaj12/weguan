package com.juzhai.home.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.bean.Feed;
import com.juzhai.home.controller.form.AnswerForm;
import com.juzhai.home.service.IInboxService;
import com.juzhai.passport.service.IUserSetupService;

@Controller
@RequestMapping(value = "app")
public class AppTpUserJudgeController extends BaseController {

	@Autowired
	private IInboxService inboxService;
	@Autowired
	private IUserSetupService userSetupService;

	@RequestMapping(value = { "/judge" }, method = RequestMethod.GET)
	public String judge(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		queryProfile(context.getUid(), model);
		request.setAttribute("isAdvise",
				userSetupService.isTpAdvise(context.getUid()));
		return "app/home/judge";
	}

	@RequestMapping(value = "/ajax/showJudge")
	public String showJudge(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		Feed feed = inboxService.showQuestion(context.getUid());
		model.addAttribute("feed", feed);
		return "app/home/judge_fragment";
	}

	@RequestMapping(value = "/ajax/respJudge", method = RequestMethod.POST)
	public String respQuestion(HttpServletRequest request, Model model,
			AnswerForm answerForm) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		inboxService.answer(context.getUid(), context.getTpId(),
				answerForm.getQuestionId(), answerForm.getTpIdentity(),
				answerForm.getAnswerId(), answerForm.isAdvise());
		return showJudge(request, model);
	}
}
