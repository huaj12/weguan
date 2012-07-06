package com.qq.oauth2;

import java.io.IOException;

import com.qq.json.JSONException;
import com.qq.json.JSONObject;
import com.qq.oauth2.bean.UserInfoBean;

public class User extends QQ {
	private static final long serialVersionUID = 7302468551312414688L;

	public User(String token, String appkey, String sercret) {
		super(token, appkey, sercret, null);
	}

	public UserInfoBean getUserInfo(String uid) throws IOException,
			JSONException {
		StringBuilder userInfoUrl = new StringBuilder();
		userInfoUrl.append(baseURL + "user/get_user_info");
		userInfoUrl.append("?oauth_consumer_key=" + getAppkey());
		userInfoUrl.append("&access_token=" + getToken());
		userInfoUrl.append("&openid=" + uid);
		return this.jsonToBean(get(userInfoUrl.toString()));
	}

	private UserInfoBean jsonToBean(String jsonData) throws JSONException {
		UserInfoBean resultBean = new UserInfoBean();
		JSONObject jsonObjRoot = new JSONObject(jsonData);
		if (jsonObjRoot.getInt("ret") != 0) {
			resultBean.setError(true);
			resultBean.setErrorCode(jsonObjRoot.get("ret").toString());
			resultBean.setErrorMsg(jsonObjRoot.getString("msg"));
		} else {
			// 昵称
			resultBean.setNickName(jsonObjRoot.getString("nickname"));
			// 头像URL
			resultBean.setAvatarSmall(jsonObjRoot.getString("figureurl"));
			// 头像URL
			resultBean.setAvatarMiddle(jsonObjRoot.getString("figureurl_1"));
			// 头像URL
			resultBean.setAvatarLarge(jsonObjRoot.getString("figureurl_2"));
			// 性别
			resultBean.setGender(jsonObjRoot.getString("gender"));
			// 是否为黄钻
			resultBean.setIsVip(jsonObjRoot.getString("vip"));
			// 黄钻等级
			resultBean.setLevel(jsonObjRoot.getString("level"));
		}
		return resultBean;
	}
}
