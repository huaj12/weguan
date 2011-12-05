package com.renren.api.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.renren.api.client.bean.AccessToken;
import com.renren.api.client.services.AdminService;
import com.renren.api.client.services.ConnectService;
import com.renren.api.client.services.FriendsService;
import com.renren.api.client.services.InvitationsService;
import com.renren.api.client.services.NotificationsService;
import com.renren.api.client.services.PageService;
import com.renren.api.client.services.PayService;
import com.renren.api.client.services.PhotoService;
import com.renren.api.client.services.UserService;
import com.renren.api.client.services.impl.AdminServiceImpl;
import com.renren.api.client.services.impl.ConnectServiceImpl;
import com.renren.api.client.services.impl.FriendsServiceImpl;
import com.renren.api.client.services.impl.InvitationsServiceImpl;
import com.renren.api.client.services.impl.NotificationsServiceImpl;
import com.renren.api.client.services.impl.PageServiceImpl;
import com.renren.api.client.services.impl.PayServiceImpl;
import com.renren.api.client.services.impl.PhotoServiceImpl;
import com.renren.api.client.services.impl.UserServiceImpl;
import com.renren.api.client.utils.HttpURLUtils;

/**
 * 注意：在构造第一个RenrenApiClient实例之前，
 * 必须先初始化RenrenApiConfig的renrenApiKey和renrenApiSecret静态属性。
 * 
 * @author 李勇(yong.li@opi-corp.com) 2011-2-16
 */
public class RenrenApiClient {

	private static final long serialVersionUID = -1193420595178024710L;

	private RenrenApiInvoker renrenApiInvoker;

	private RenrenApiConfig apiConfig = new RenrenApiConfig();

	private FriendsService friendsService;

	private UserService userService;

	private AdminService adminService;

	private ConnectService connectService;

	private InvitationsService invitationsService;

	private NotificationsService notificationsService;

	private PageService pageService;

	private PayService payService;

	private PhotoService photoService;

	/**
	 * 如果sessionKey为空，那么只能调用不需要sessionKey的接口。
	 * 
	 * @param sessionKey
	 */
	public RenrenApiClient(String sessionKey) {
		this(sessionKey, false);
	}

	public RenrenApiClient(String renrenApiKey, String renrenApiSecret) {
		this.apiConfig.renrenApiKey = renrenApiKey;
		this.apiConfig.renrenApiSecret = renrenApiSecret;
	}

	public void renrenApiOauth(String renrenApiKey, String renrenApiSecret) {
		this.apiConfig.renrenApiKey = renrenApiKey;
		this.apiConfig.renrenApiSecret = renrenApiSecret;
	}

	/**
	 * 
	 * @param token
	 *            访问标识
	 * @param isAccessToken
	 *            ture:token为accessToken, false:sessionKey
	 */
	public RenrenApiClient(String token, boolean isAccessToken) {
		this.renrenApiInvoker = new RenrenApiInvoker(token, isAccessToken,
				apiConfig);
		this.initService();
	}

	private void initService() {
		this.friendsService = new FriendsServiceImpl(this.renrenApiInvoker);
		this.userService = new UserServiceImpl(this.renrenApiInvoker);
		this.adminService = new AdminServiceImpl(this.renrenApiInvoker);
		this.connectService = new ConnectServiceImpl(this.renrenApiInvoker);
		this.invitationsService = new InvitationsServiceImpl(
				this.renrenApiInvoker);
		this.notificationsService = new NotificationsServiceImpl(
				this.renrenApiInvoker);
		this.pageService = new PageServiceImpl(this.renrenApiInvoker);
		this.payService = new PayServiceImpl(this.renrenApiInvoker);
		this.photoService = new PhotoServiceImpl(this.renrenApiInvoker);
	}

	public static AccessToken getOAuthAccessTokenFromCode(String code,
			String appkey, String appSecret, String redirect_uri) {
		AccessToken accessToken = null;
		JSONObject tokenJson = null;
		try {
			String rrOAuthTokenEndpoint = "https://graph.renren.com/oauth/token";
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("client_id", appkey);
			parameters.put("client_secret", appSecret);
			parameters.put("redirect_uri", redirect_uri);// 这个redirect_uri要和之前传给authorization
															// endpoint的值一样
			parameters.put("grant_type", "authorization_code");
			parameters.put("code", code);
			String tokenResult = HttpURLUtils.doPost(rrOAuthTokenEndpoint,
					parameters);
			tokenJson = (JSONObject) JSONValue.parse(tokenResult);
			accessToken = new AccessToken(tokenJson);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage() + "" + tokenJson, e);
		}
		return accessToken;
	}

	public static String getAuthorizeURLforCode(String appId,
			String redirect_uri) throws UnsupportedEncodingException {
		String rrOAuthTokenEndpoint = "https://graph.renren.com/oauth/authorize?";
		return rrOAuthTokenEndpoint + "client_id=" + appId
				+ "&response_type=code&redirect_uri="
				+ URLEncoder.encode(redirect_uri, "UTF-8") + "&display=page";
	}

	public RenrenApiInvoker getRenrenApiInvoker() {
		return renrenApiInvoker;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public FriendsService getFriendsService() {
		return friendsService;
	}

	public UserService getUserService() {
		return userService;
	}

	public ConnectService getConnectService() {
		return connectService;
	}

	public InvitationsService getInvitationsService() {
		return invitationsService;
	}

	public NotificationsService getNotificationsService() {
		return notificationsService;
	}

	public PageService getPageService() {
		return pageService;
	}

	public PayService getPayService() {
		return payService;
	}

	public PhotoService getPhotoService() {
		return photoService;
	}
}
