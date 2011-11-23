package com.juzhai.msg.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.account.bean.ConsumeAction;
import com.juzhai.account.service.IAccountService;
import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActService;
import com.juzhai.app.service.IAppService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.msg.bean.MergerActMsg;
import com.juzhai.msg.controller.view.ActMsgView;
import com.juzhai.msg.service.IMergerActMsgService;
import com.juzhai.msg.service.IMsgMessageService;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.passport.service.impl.TpUserService;

@Controller
@RequestMapping(value = "msg")
public class MsgCenterController extends BaseController {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IMergerActMsgService mergerActMsgService;
	@Autowired
	private IMsgMessageService msgMessageService;
	@Autowired
	private IProfileService profileService;
	@Value("${unread.actmsg.rows}")
	private int unReadActMsgRows = 1;
	@Value("${read.actmsg.rows}")
	private int readActMsgRows = 20;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private IAppService appService;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private IActService actService;

	@RequestMapping(value = "/showUnRead", method = RequestMethod.GET)
	public String showUnRead(HttpServletRequest request, Model model,
			Integer page) throws NeedLoginException {
		// if (null == page)
		// page = 1;
		// UserContext context = checkLoginForApp(request);
		// ProfileCache profileCache = queryProfile(context.getUid(), model);
		// if (profileCache != null && !profileCache.getSubEmail()) {
		// showNotSubEmailTip(model);
		// }
		// doPageUnRead(context.getUid(), page, model);
		return "redirect:/app/index";
	}

	@RequestMapping(value = "/pageUnRead", method = RequestMethod.GET)
	public String pageUnRead(HttpServletRequest request, Model model,
			Integer page) throws NeedLoginException {
		if (null == page)
			page = 1;
		UserContext context = checkLoginForApp(request);
		doPageUnRead(context.getUid(), page, model);
		return "msg/app/ajax/unReadContent";
	}

	private void doPageUnRead(long uid, int page, Model model) {
		long totalCount = mergerActMsgService.countUnRead(uid);
		PagerManager pager = new PagerManager(getPage(totalCount, page,
				unReadActMsgRows), unReadActMsgRows, Long.valueOf(totalCount)
				.intValue(), "/msg/pageUnRead", null, "unReadContent");
		List<MergerActMsg> actMsgList = mergerActMsgService.pageUnRead(uid,
				pager.getFirstResult(), pager.getMaxResult());
		List<ActMsgView> actMsgViewList = assembleActMsgView(uid, actMsgList);
		model.addAttribute("actMsgViewList", actMsgViewList);
		model.addAttribute("readCount", mergerActMsgService.countRead(uid));
		model.addAttribute("pager", pager);
		model.addAttribute("invitePoint", Math
				.abs(com.juzhai.account.InitData.CONSUME_ACTION_RULE
						.get(ConsumeAction.OPEN_MESSAGE_INVITE)));
		model.addAttribute("recommendPoint", Math
				.abs(com.juzhai.account.InitData.CONSUME_ACTION_RULE
						.get(ConsumeAction.OPEN_MESSAGE_RECOMMEND)));
	}

	private int getPage(long totalCount, int curpage, int msgRows) {
		int page = 1;
		if (curpage > (totalCount / msgRows) + 1 && totalCount % msgRows != 0) {
			page = (int) (totalCount / msgRows) + 1;
		} else if (curpage > totalCount / msgRows && totalCount % msgRows == 0) {
			page = (int) (totalCount / msgRows);
		} else {
			page = curpage;
		}
		if (page < 1) {
			page = 1;
		}
		return page;
	}

	private List<ActMsgView> assembleActMsgView(long uid,
			List<MergerActMsg> actMsgList) {
		List<ActMsgView> actMsgViewList = new ArrayList<ActMsgView>(
				actMsgList.size());
		List<Long> actIds = new ArrayList<Long>();
		for (MergerActMsg mergerActMsg : actMsgList) {
			for (ActMsg actMsg : mergerActMsg.getMsgs()) {
				actIds.add(actMsg.getActId());
			}
		}
		Map<Long, Act> actMap = actService.getMultiActByIds(actIds);
		for (MergerActMsg actMsg : actMsgList) {
			ActMsgView actMsgView = new ActMsgView();
			List<Act> acts = new ArrayList<Act>();
			for (ActMsg msg : actMsg.getMsgs()) {
				Act act = actMap.get(msg.getActId());
				if (act != null) {
					acts.add(act);
				}
			}
			actMsgView.setActs(acts);
			actMsgView.setProfileCache(profileService
					.getProfileCacheByUid(actMsg.getUid()));
			actMsgView.setMsgType(actMsg.getType());
			actMsgView.setStuts(actMsg.isStuts());
			actMsgView.setDate(actMsg.getDate());
			actMsgViewList.add(actMsgView);
		}
		return actMsgViewList;
	}

	@RequestMapping(value = "/showRead", method = RequestMethod.GET)
	public String showRead(HttpServletRequest request, Model model, Integer page)
			throws NeedLoginException {
		if (null == page)
			page = 1;
		UserContext context = checkLoginForApp(request);
		ProfileCache profileCache = queryProfile(context.getUid(), model);
		if (profileCache != null && !profileCache.getSubEmail()) {
			showNotSubEmailTip(model);
		}
		doPageRead(context.getUid(), model, page);
		// 清空消息数目
		mergerActMsgService.clearMergerActMsgCount(context.getUid());
		return "redirect:/app/index";
	}

	@RequestMapping(value = "/pageRead", method = RequestMethod.GET)
	public String pageRead(HttpServletRequest request, Model model, Integer page)
			throws NeedLoginException {
		if (null == page)
			page = 1;
		UserContext context = checkLoginForApp(request);
		doPageRead(context.getUid(), model, page);
		return "msg/app/ajax/readContent";
	}

	private void doPageRead(long uid, Model model, int page) {
		long totalCount = mergerActMsgService.countRead(uid);
		PagerManager pager = new PagerManager(getPage(totalCount, page,
				readActMsgRows), readActMsgRows, Long.valueOf(totalCount)
				.intValue(), "/msg/pageRead", "", "readContent");
		List<MergerActMsg> actMsgList = mergerActMsgService.pageRead(uid,
				pager.getFirstResult(), pager.getMaxResult());
		List<ActMsgView> actMsgViewList = assembleActMsgView(uid, actMsgList);
		model.addAttribute("actMsgViewList", actMsgViewList);
		model.addAttribute("pager", pager);
		model.addAttribute("unReadCount", mergerActMsgService.countUnRead(uid));
		model.addAttribute("citys", com.juzhai.passport.InitData.CITY_MAP);
	}

	/**
	 * 打开未读信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws NeedLoginException
	 */
	@RequestMapping(value = "/openMessage", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult openMessage(HttpServletRequest request,
			HttpServletResponse response, Model model, Integer curPage,
			Integer curIndex, String type) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		AjaxResult result = new AjaxResult();
		try {
			if (curPage != null && curIndex != null
					&& !StringUtils.isEmpty(type)) {
				// 判断积分余额
				// int openMsgPoint = 0;
				// ConsumeAction consumeAction = null;
				// if (MsgType.INVITE.name().equals(type)) {
				// openMsgPoint =
				// com.juzhai.account.InitData.CONSUME_ACTION_RULE
				// .get(ConsumeAction.OPEN_MESSAGE_INVITE);
				// consumeAction = ConsumeAction.OPEN_MESSAGE_INVITE;
				// } else if (MsgType.RECOMMEND.name().equals(type)) {
				// openMsgPoint =
				// com.juzhai.account.InitData.CONSUME_ACTION_RULE
				// .get(ConsumeAction.OPEN_MESSAGE_RECOMMEND);
				// consumeAction = ConsumeAction.OPEN_MESSAGE_RECOMMEND;
				// }
				// if (point + openMsgPoint >= 0) {
				int index = (curPage - 1) * unReadActMsgRows + curIndex;
				mergerActMsgService.openMessage(context.getUid(), index);
				// accountService
				// .consumePoint(context.getUid(), consumeAction);
				// result.setSuccess(true);
				// } else {
				// 积分余额不足
				// result.setResult(false);
				// result.setErrorCode("-1");
				// }
			} else {
				result.setResult(false);
			}
		} catch (Exception e) {
			result.setResult(false);
		}
		return result;
	}

	@RequestMapping(value = "/reMoveMessage", method = RequestMethod.POST)
	public String reMoveMessage(HttpServletRequest request,
			HttpServletResponse response, Model model, Integer curPage,
			Integer curIndex, String type) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		try {
			if (curPage != null && curIndex != null) {
				int index = (curPage - 1) * unReadActMsgRows + curIndex;
				if ("unread".equals(type)) {
					mergerActMsgService.removeUnRead(context.getUid(), index);
					return pageUnRead(request, model, curPage);
				} else if ("read".equals(type)) {
					mergerActMsgService.removeRead(context.getUid(), index);
					return pageRead(request, model, curPage);
				}
			}
		} catch (Exception e) {
			log.error("remove msg is error", e);
		}
		return null;
	}

	@RequestMapping(value = "/agreeMessage", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult agreeMessage(HttpServletRequest request,
			HttpServletResponse response, Model model, String actIds,
			Long receiverId, Integer curPage, Integer curIndex)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		AjaxResult result = new AjaxResult();
		try {
			if (!StringUtils.isEmpty(actIds) && receiverId != null
					&& curPage != null && curIndex != null) {
				int index = (curPage - 1) * unReadActMsgRows + curIndex;
				// 改变消息状态
				mergerActMsgService.updateMsgStuts(context.getUid(), index);
				String[] ids = actIds.split(",");
				for (String id : ids) {
					long actId = 0;
					try {
						actId = Long.valueOf(id);
					} catch (Exception e) {
					}
					if (actId == 0)
						continue;
					ActMsg msg = new ActMsg(actId, MsgType.INVITE);
					// 发送拒宅邀请

					msgMessageService.sendActMsg(context.getUid(), receiverId,
							msg);
				}
				// accountService.profitPoint(context.getUid(),
				// ProfitAction.INVITE_FRIEND);
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
			}
		} catch (Exception e) {
			result.setSuccess(false);
		}
		return result;
	}

	@RequestMapping(value = "/getUnMessageCount")
	@ResponseBody
	public AjaxResult getUnMessageCount(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		AjaxResult result = new AjaxResult();
		try {
			long unread = mergerActMsgService.getMergerActMsgCount(context
					.getUid());
			result.setSuccess(true);
			result.setResult(unread);
		} catch (Exception e) {
			result.setSuccess(false);
			log.error("getUnMessageCount is error", e);
		}
		return result;
	}

	@RequestMapping(value = "/sendBoard")
	@ResponseBody
	public AjaxResult sendBoard(HttpServletRequest request, String fids,
			String content) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		AjaxResult result = new AjaxResult();
		try {
			if (!StringUtils.isEmpty(fids)) {
				AuthInfo authInfo = tpUserAuthService.getAuthInfo(
						context.getUid(), context.getTpId());
//				if (appService.sendMessage(fids, content, authInfo)) {
//					result.setSuccess(true);
//				} else {
//					result.setSuccess(false);
//				}
			} else {
				result.setSuccess(false);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			log.error("sendBoard is error", e);
		}
		return result;
	}

	@RequestMapping(value = "/sendAbout")
	@ResponseBody
	public AjaxResult sendAbout(HttpServletRequest request, String content,
			Long fuid, Long actId, Model model) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		AjaxResult result = new AjaxResult();
		try {
			if (actId == null) {
				actId = 0l;
			}
			if (fuid == null) {
				result.setSuccess(false);
				return result;
			}
			TpUser tpUser = tpUserService.getTpUserByUid(fuid);
			List<String> fuids = new ArrayList<String>();
			if (tpUser != null) {
				fuids.add(tpUser.getTpIdentity());
			}
			if (appService.aboutFriends(fuids, content, context.getUid(),
					context.getTpId(), actId)) {
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			log.error("sendBoard is error", e);
		}
		return result;
	}

}
