package com.juzhai.act.controller.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.act.model.Act;
import com.juzhai.act.model.RawAct;
import com.juzhai.act.model.UserAct;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IRawActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.app.bean.TpMessageKey;
import com.juzhai.app.controller.view.ActUserView;
import com.juzhai.cms.service.IUploadImageService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.AddRawActException;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.service.IMsgMessageService;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.IProfileService;

@Controller
@RequestMapping(value = "app")
public class AppActController extends BaseController {

	@Autowired
	private IUserActService userActService;
	@Autowired
	private IActService actService;
	@Autowired
	private IFriendService friendService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IMsgMessageService msgMessageService;
	@Autowired
	private IUploadImageService uploadImageService;
	@Autowired
	private MessageSource messageSource;
	@Value("${act.user.maxResult}")
	private int actUserMaxResult;
	@Autowired
	IRawActService rawActService;
 
	@RequestMapping(value = "/showAct/{actId}", method = RequestMethod.GET)
	public String showAct(HttpServletRequest request, Model model,
			@PathVariable long actId, Integer friendUser)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		Act act = actService.getActById(actId);
		if (act == null) {
			return error_404;
		}
		model.addAttribute("act", act);
		model.addAttribute("hasAct",
				userActService.hasAct(context.getUid(), actId));
		model.addAttribute("isShield", actService.isShieldAct(actId));

		model.addAttribute("userActCount",
				userActService.countUserActByActId(context.getTpId(), actId));
		model.addAttribute(
				"fUserActCount",
				userActService.countFriendUserActByActId(
						friendService.getAppFriends(context.getUid()), actId));
		model.addAttribute("showFriendUser", null != friendUser);
		return "app/act/act";
	}

	@RequestMapping(value = "/ajax/pageActUser", method = RequestMethod.GET)
	public String pageActUser(HttpServletRequest request, Model model,
			long actId, int page) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		PagerManager pager = new PagerManager(page, actUserMaxResult,
				userActService.countUserActByActId(context.getTpId(), actId));
		List<UserAct> userActList = userActService.listUserActByActId(
				context.getTpId(), actId, pager.getFirstResult(),
				pager.getMaxResult());
		List<ActUserView> actUserViewList = assembleActUserView(
				context.getUid(), userActList, true);
		model.addAttribute("pager", pager);
		model.addAttribute("act", actService.getActById(actId));
		model.addAttribute("actUserViewList", actUserViewList);
		model.addAttribute("pageFriend", false);
		return "app/act/act_user_list";
	}

	@RequestMapping(value = "/ajax/pageActFriend", method = RequestMethod.GET)
	public String pageActFriend(HttpServletRequest request, Model model,
			long actId, int page) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		List<Long> friendIds = friendService.getAppFriends(context.getUid());
		PagerManager pager = new PagerManager(page, actUserMaxResult,
				userActService.countFriendUserActByActId(friendIds, actId));
		List<UserAct> userActList = userActService.listFriendUserActByActId(
				friendIds, actId, pager.getFirstResult(), pager.getMaxResult());
		List<ActUserView> actUserViewList = assembleActUserView(
				context.getUid(), userActList, false);
		model.addAttribute("pager", pager);
		model.addAttribute("act", actService.getActById(actId));
		model.addAttribute("actUserViewList", actUserViewList);
		model.addAttribute("pageFriend", true);
		return "app/act/act_user_list";
	}

	@RequestMapping(value = "/ajax/inviteHer", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult inviteHer(HttpServletRequest request, Model model,
			long friendId, @RequestParam(defaultValue = "0") long actId)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		if (actId > 0) {
			msgMessageService.sendActMsg(context.getUid(), friendId,
					new ActMsg(actId, context.getUid(), ActMsg.MsgType.INVITE));
		}
		AjaxResult result = new AjaxResult();
		result.setSuccess(true);
		return result;
	}

	@RequestMapping(value = "/kindEditor/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> kindEditorUpload(HttpServletRequest request,
			MultipartFile imgFile) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		Map<String, String> map = new HashMap<String, String>();
		String url = "";
		if (imgFile != null && imgFile.getSize() > 0) {
			UUID uuid = UUID.randomUUID();
			String fileName = uuid.toString() + "."
					+ uploadImageService.getImgType(imgFile);
			try {
				url = uploadImageService.uploadEditorTempImg(context.getUid(),
						fileName, imgFile);
			} catch (UploadImageException e) {
				getError(e.getMessage());
			}
		} else {
			return getError(messageSource.getMessage(
					UploadImageException.UPLOAD_FILE_ISNULL, null,
					Locale.SIMPLIFIED_CHINESE));
		}
		map.put("error", "0");
		map.put("url", url);
		return map;
	}

	@RequestMapping(value = "/ajax/temp/addActImage", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, String>  addActImage(HttpServletRequest request, Model model,
			MultipartFile imgFile) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		Map<String, String> map = new HashMap<String, String>();
		String url = "";
		if (imgFile != null && imgFile.getSize() > 0) {
			UUID uuid = UUID.randomUUID();
			String fileName = uuid.toString() + "."
					+ uploadImageService.getImgType(imgFile);
			try {
				url = uploadImageService.uploadActTempImg(context.getUid(),
						fileName, imgFile);
			} catch (UploadImageException e) {
				getError(e.getMessage());
			}
		} else {
			return getError(messageSource.getMessage(
					UploadImageException.UPLOAD_FILE_ISNULL, null,
					Locale.SIMPLIFIED_CHINESE));
		}
		map.put("error", "0");
		map.put("url", url);
		return map;
	}

	@RequestMapping(value = "/web/ajax/addAct", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult addAct(HttpServletRequest request, Model model,
			RawAct rawAct) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		long uid=context.getUid();
		AjaxResult result = new AjaxResult();
		try {
			if(rawAct!=null){
				rawAct.setCreateUid(uid);
			}
			rawAct=rawActService.addRawAct(rawAct);
		} catch (Exception e) {
			result.setErrorCode(e.getMessage());
			result.setSuccess(false);
			return result;
		}
		result.setSuccess(true);
		return result;
	}

	private Map<String, String> getError(String message) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("error", "1");
		map.put("message", message);
		return map;
	}

	private List<ActUserView> assembleActUserView(long uid,
			List<UserAct> userActList, boolean needJudgeFriend) {
		List<ActUserView> actUserViewList = new ArrayList<ActUserView>();
		for (UserAct userAct : userActList) {
			actUserViewList.add(new ActUserView(profileService
					.getProfileCacheByUid(userAct.getUid()), userAct
					.getLastModifyTime(), needJudgeFriend ? friendService
					.isAppFriend(uid, userAct.getUid()) : true));
		}
		return actUserViewList;
	}
}
