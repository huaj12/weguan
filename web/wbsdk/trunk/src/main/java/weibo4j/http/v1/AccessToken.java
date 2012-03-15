package weibo4j.http.v1;

import weibo4j.http.Response;
import weibo4j.model.WeiboException;

public class AccessToken extends OAuthToken {
	private static final long serialVersionUID = -8344528374458826291L;
	private String screenName;
	private long userId;

	AccessToken(Response res) throws WeiboException {
		this(res.asString());
	}

	// for test unit
	AccessToken(String str) {
		super(str);
		screenName = getParameter("screen_name");
		String sUserId = getParameter("user_id");
		if (sUserId != null)
			userId = Long.parseLong(sUserId);

	}

	public AccessToken(String token, String tokenSecret) {
		super(token, tokenSecret);
	}

	/**
	 * 
	 * @return screen name
	 * @since Weibo4J 1.2.1
	 */

	public String getScreenName() {
		return screenName;
	}

	/**
	 * 
	 * @return user id
	 * @since Weibo4J 1.2.1
	 */

	public long getUserId() {
		return userId;
	}

}