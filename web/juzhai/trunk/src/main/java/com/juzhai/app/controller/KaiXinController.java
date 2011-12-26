package com.juzhai.app.controller;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.app.bean.TpMessageKey;
import com.juzhai.core.SystemConfig;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.web.jstl.JzCoreFunction;
import com.juzhai.core.web.jstl.JzResourceFunction;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.msg.service.IMsgMessageService;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.platform.utils.AppPlatformUtils;

@Controller
public class KaiXinController extends BaseController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IMsgMessageService msgMessageService;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private IUserActService userActService;
	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private IActService actService;
	@Value("${kaixin.feed.redirect.uri}")
	private String feedRedirectUri;
	@Value("${kaixin.sysnew.redirect.uri}")
	private String sysnewRedirectUri;
	@Value("${kaixin.request.redirect.uri}")
	private String requestRedirectUri;
	@Value("${show.feed.count}")
	private int feedCount = 3;

	// @RequestMapping(value = { "/kaixinFeed2" }, method = RequestMethod.GET)
	public String kaixinFeed2(HttpServletRequest request,
			HttpServletResponse response, Model model, Long actId) {
		PrintWriter out = null;

		try {

			UserContext context = checkLoginForApp(request);
			Thirdparty tp = InitData.TP_MAP.get(context.getTpId());
			String text = "";
			String word = "";
			if (actId == null)
				actId = 0l;
			Act act = actService.getActById(actId);
			if (act == null) {
				text = messageSource.getMessage(TpMessageKey.FEED_TEXT_DEFAULT,
						null, Locale.SIMPLIFIED_CHINESE);
				word = messageSource.getMessage(TpMessageKey.FEED_WORD_DEFAULT,
						null, Locale.SIMPLIFIED_CHINESE);
			} else {
				int count = userActService.countUserActByActId(
						context.getTpId(), actId);
				if (count > feedCount) {
					text = messageSource.getMessage(TpMessageKey.FEED_TEXT,
							new Object[] { act.getName(), count - 1 },
							Locale.SIMPLIFIED_CHINESE);
				} else {
					text = messageSource.getMessage(
							TpMessageKey.FEED_TEXT_COUNT_DEFAULT,
							new Object[] { act.getName() },
							Locale.SIMPLIFIED_CHINESE);
				}
				word = messageSource.getMessage(TpMessageKey.FEED_WORD, null,
						Locale.SIMPLIFIED_CHINESE);
			}
			text = URLEncoder.encode(text, "UTF-8");
			String linktext = messageSource
					.getMessage(TpMessageKey.FEED_LINKTEXT, null,
							Locale.SIMPLIFIED_CHINESE);
			String link = tp.getAppUrl();
			String feedRedirect_uri = SystemConfig.getDomain(tp == null ? null
					: tp.getName())
					+ feedRedirectUri
					+ "?tpId="
					+ context.getTpId();
			String logo = "";
			if (act != null) {
				logo = act.getLogo();
			}
			String picurl = JzCoreFunction.actLogo(actId, logo, 120);
			response.setContentType("text/plain");
			out = response.getWriter();
			String url = "http://api.kaixin001.com/dialog/feed?display=iframe&redirect_uri="
					+ feedRedirect_uri
					+ "&linktext="
					+ linktext
					+ "&link="
					+ link
					+ "&text="
					+ text
					+ "&app_id="
					+ tp.getAppId()
					+ "&need_redirect=0&picurl=" + picurl + "&word=" + word;
			out.println(url);
		} catch (Exception e) {
			log.error("kaixin send feed is error", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	@RequestMapping(value = { "/kaixinFeed" }, method = RequestMethod.GET)
	public String kaixinFeed(HttpServletRequest request,
			HttpServletResponse response, Model model, Long actId) {
		PrintWriter out = null;

		try {
			UserContext context = checkLoginForApp(request);
			Thirdparty tp = InitData.TP_MAP.get(context.getTpId());
			String text = "";
			String word = "";
			if (actId == null)
				actId = 0l;
			Act act = actService.getActById(actId);
			if (act == null) {
				text = messageSource.getMessage(TpMessageKey.FEED_TEXT_DEFAULT,
						null, Locale.SIMPLIFIED_CHINESE);
				word = messageSource.getMessage(TpMessageKey.FEED_WORD_DEFAULT,
						null, Locale.SIMPLIFIED_CHINESE);
			} else {
				int count = userActService.countUserActByActId(
						context.getTpId(), actId);
				if (count > feedCount) {
					text = messageSource.getMessage(TpMessageKey.FEED_TEXT,
							new Object[] { act.getName(), count - 1 },
							Locale.SIMPLIFIED_CHINESE);
				} else {
					text = messageSource.getMessage(
							TpMessageKey.FEED_TEXT_COUNT_DEFAULT,
							new Object[] { act.getName() },
							Locale.SIMPLIFIED_CHINESE);
				}
				word = messageSource.getMessage(TpMessageKey.FEED_WORD, null,
						Locale.SIMPLIFIED_CHINESE);
			}
			String linktext = messageSource
					.getMessage(TpMessageKey.FEED_LINKTEXT, null,
							Locale.SIMPLIFIED_CHINESE);
			String link = tp.getAppUrl();
			String logo = "";
			if (act != null) {
				logo = act.getLogo();
			}
			String picurl = JzResourceFunction.actLogo(actId, logo, 120);
			// 拼凑参数
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("method", "actions.sendNewsFeed");
			paramMap.put("text", text);
			paramMap.put("linktext", linktext);
			paramMap.put("link", link);
			paramMap.put("pic", picurl);
			AuthInfo authInfo = tpUserAuthService.getAuthInfo(context.getUid(),
					context.getTpId());
			String query = AppPlatformUtils.buildQuery(paramMap,
					authInfo.getAppKey(), authInfo.getAppSecret(),
					authInfo.getSessionKey(), "1.2");
			response.setContentType("text/plain");
			out = response.getWriter();
			out.println(AppPlatformUtils.urlBase64Encode(query));
		} catch (Exception e) {
			log.error("kaixin send feed is error", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	@RequestMapping(value = { "/kaixinRequest" }, method = RequestMethod.GET)
	public String kaixinRequest(HttpServletRequest request,
			HttpServletResponse response, Model model, String name) {
		PrintWriter out = null;
		try {
			UserContext context = checkLoginForApp(request);
			Thirdparty tp = InitData.TP_MAP.get(context.getTpId());
			String text = "";
			if (StringUtils.isEmpty(name)) {
				text = messageSource.getMessage(
						TpMessageKey.INVITE_TEXT_DEFAULT, null,
						Locale.SIMPLIFIED_CHINESE);
			} else {
				text = messageSource.getMessage(TpMessageKey.INVITE_TEXT,
						new Object[] { name }, Locale.SIMPLIFIED_CHINESE);
			}
			String requestRedirect_uri = SystemConfig
					.getDomain(tp == null ? null : tp.getName())
					+ requestRedirectUri + "?tpId=" + context.getTpId();
			response.setContentType("text/plain");
			out = response.getWriter();
			out.println("http://api.kaixin001.com/dialog/invitation?display=iframe&text="
					+ text
					+ "&app_id="
					+ tp.getAppId()
					+ "&redirect_uri="
					+ requestRedirect_uri + "&need_redirect=0");
		} catch (Exception e) {
			log.error("kaixin send Request is error", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	@RequestMapping(value = { "/dialogSysnews" }, method = RequestMethod.GET)
	public String dialogSysnews(HttpServletRequest request,
			HttpServletResponse response, Model model, Long actId) {
		PrintWriter out = null;
		try {
			if (actId == null)
				actId = 0l;
			Act act = actService.getActById(actId);
			if (act == null) {
				log.error("dialogSysnews actId is null");
				return null;
			}
			UserContext context = checkLoginForApp(request);
			Thirdparty tp = InitData.TP_MAP.get(context.getTpId());
			String text = messageSource.getMessage(
					TpMessageKey.SYSNEW_DIALOG_TEXT, null,
					Locale.SIMPLIFIED_CHINESE);
			String linktext = messageSource
					.getMessage(TpMessageKey.FEED_LINKTEXT, null,
							Locale.SIMPLIFIED_CHINESE);
			String link = tp.getAppUrl() + "?goUri=/msg/showRead";
			String sysnewRedirect_uri = SystemConfig
					.getDomain(tp == null ? null : tp.getName())
					+ sysnewRedirectUri
					+ "?name="
					+ act.getId()
					+ "-"
					+ context.getTpId();
			String word = messageSource.getMessage(
					TpMessageKey.INVITE_FRIEND_WORD, null,
					Locale.SIMPLIFIED_CHINESE);
			String picurl = JzResourceFunction.actLogo(act.getId(), act.getLogo(),
					120);
			response.setContentType("text/plain");
			out = response.getWriter();
			out.println("http://api.kaixin001.com/dialog/sysnews?display=iframe&linktext="
					+ linktext
					+ "&text="
					+ text
					+ "&link="
					+ link
					+ "&app_id="
					+ tp.getAppId()
					+ "&redirect_uri="
					+ sysnewRedirect_uri
					+ "&picurl=" + picurl + "&need_redirect=0&word=" + word);
		} catch (Exception e) {
			log.error("kaixin dialogSysnews is error", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	@RequestMapping(value = "kaixinSysnewsCallBack", method = RequestMethod.GET)
	public String dialogSysnewsCallBack(HttpServletRequest request,
			Model model, String uid, String fuids, String name, Integer num) {
		String queryString = request.getQueryString();
		try {
			long tpId = 0;
			long actId = 0;
			if (!name.isEmpty()) {
				try {
					String[] str = name.split("-");
					tpId = Long.valueOf(str[1]);
					actId = Long.valueOf(str[0]);
				} catch (Exception e) {
				}
			}

			Thirdparty tp = com.juzhai.passport.InitData.TP_MAP.get(tpId);
			if (tp == null || actId == 0) {
				log.error("dialogSysnewsCallBack prams is null tpId=" + tpId
						+ "  actId=" + actId);
				return null;
			}
			// 验证签名
			if (AppPlatformUtils.checkSignFromQuery(queryString,
					tp.getAppSecret())) {
				TpUser sendUser = tpUserService.getTpUserByTpIdAndIdentity(
						tpId, uid);
				if (sendUser == null) {
					log.error("dialogSysnewsCallBack uid is not bind");
					return null;
				}
				// ActMsg actMsg = new ActMsg(actId, sendUser.getUid(),
				// MsgType.INVITE);
				// String[] fids = fuids.split(",");
				// for (String fid : fids) {
				// TpUser receiverUser = tpUserService
				// .getTpUserByTpIdAndIdentity(tpId, fid);
				// if (receiverUser != null && receiverUser.getUid() > 0) {
				// msgMessageService.sendActMsg(sendUser.getUid(),
				// receiverUser.getUid(), actMsg);
				// } else {
				// msgMessageService.sendActMsg(sendUser.getUid(), tpId,
				// fid, actMsg);
				// }
				// }
				// 有内容加积分
				// if(num==null){
				// num=1;
				// }
				// accountService.profitPoint(sendUser.getUid(),
				// ProfitAction.INVITE_FRIEND, num);
			} else {
				log.error("inviteCallBack sig is error");
			}
		} catch (Exception e) {
			log.error("inviteCallBack is error queryString=" + queryString, e);
		}
		return null;
	}

	// 拒宅无内容邀请回调
	@RequestMapping(value = "kaxinRequestCallBack", method = RequestMethod.GET)
	public String requestCallBack(HttpServletRequest request, Long tpId,
			String uid, Integer num) {
		String queryString = request.getQueryString();
		try {
			Thirdparty tp = com.juzhai.passport.InitData.TP_MAP.get(tpId);
			if (tp == null) {
				log.error("requestBack prams is null tpId=" + tpId);
				return null;
			}
			if (AppPlatformUtils.checkSignFromQuery(queryString,
					tp.getAppSecret())) {
				TpUser sendUser = tpUserService.getTpUserByTpIdAndIdentity(
						tpId, uid);
				if (sendUser == null) {
					log.error("requestBack uid is not bind");
					return null;
				}
				// // 加积分
				// if (num == null) {
				// num = 1;
				// }
				// accountService.profitPoint(sendUser.getUid(),
				// ProfitAction.INVITE_FRIEND, num);
			} else {
				log.error("requestBack sig is error ");
			}
		} catch (Exception e) {
			log.error("requestBack  is error queryString=" + queryString, e);
		}
		return null;
	}

	// 拒宅召集令回调
	@RequestMapping(value = "kaixinFeedCallBack", method = RequestMethod.GET)
	public String feedCallBack(HttpServletRequest request, Long tpId, String uid) {
		// 验证签名
		String queryString = request.getQueryString();
		try {
			Thirdparty tp = com.juzhai.passport.InitData.TP_MAP.get(tpId);
			if (tp == null) {
				log.error("feedCallBack prams is null tpId=" + tpId);
				return null;
			}
			if (AppPlatformUtils.checkSignFromQuery(queryString,
					tp.getAppSecret())) {
				TpUser sendUser = tpUserService.getTpUserByTpIdAndIdentity(
						tpId, uid);
				if (sendUser == null) {
					log.error("feedCallBack uid is not bind");
					return null;
				}
				// // 加积分
				// accountService.profitPoint(sendUser.getUid(),
				// ProfitAction.TP_FEED);
			} else {
				log.error("feedCallBack sig is error ");
			}
		} catch (Exception e) {
			log.error("feedCallBack is error queryString=" + queryString, e);
		}
		return null;
	}
}
