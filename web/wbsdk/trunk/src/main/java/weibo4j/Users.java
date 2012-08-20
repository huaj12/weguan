package weibo4j;

import weibo4j.model.PostParameter;
import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.util.WeiboConfig;

public class Users extends Weibo {

	private static final long serialVersionUID = -3904664827509335778L;

	public Users(String token) {
		super(token);
	}

	public Users(String token, String tokenSecret, String appkey,
			String appSecret) {
		super(token, tokenSecret, appkey, appSecret);
	}

	/*----------------------------用户接口----------------------------------------*/
	/**
	 * 根据用户ID获取用户信息
	 * 
	 * @param uid
	 *            需要查询的用户ID
	 * @return User
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see <a href="http://open.weibo.com/wiki/2/users/show">users/show</a>
	 * @since JDK 1.5
	 */
	public User showUserById(String uid) throws WeiboException {
		return new User(client.get(
				WeiboConfig.getValue("baseURL") + "users/show.json",
				new PostParameter[] { new PostParameter("uid", uid) })
				.asJSONObject());
	}

	/**
	 * 根据用户ID获取用户信息
	 * 
	 * @param screen_name
	 *            用户昵称
	 * @return User
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see <a href="http://open.weibo.com/wiki/2/users/show">users/show</a>
	 * @since JDK 1.5
	 */
	public User showUser(String screen_name) throws WeiboException {
		return new User(client.get(
				WeiboConfig.getValue("baseURL") + "users/show.json",
				new PostParameter[] { new PostParameter("screen_name",
						screen_name) }).asJSONObject());
	}

	/**
	 * 通过个性化域名获取用户资料以及用户最新的一条微博
	 * 
	 * @param domain
	 *            需要查询的个性化域名。
	 * @return User
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see <a
	 *      href="http://open.weibo.com/wiki/2/users/domain_show">users/domain_show</a>
	 * @since JDK 1.5
	 */
	public User showUserByDomain(String domain) throws WeiboException {
		return new User(client.get(
				WeiboConfig.getValue("baseURL") + "users/domain_show.json",
				new PostParameter[] { new PostParameter("domain", domain) })
				.asJSONObject());
	}
}
