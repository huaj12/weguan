package weibo4j.examples.friendships;

import java.util.List;

import weibo4j.Friendships;
import weibo4j.examples.Log;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

public class GetRemark {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String access_token = args[0];
		String uids = args[1];
		Friendships fm = new Friendships(access_token);
		try {
			List<User> user = fm.getRemark(uids);
			for (User u : user) {
				System.out.println(user.toString());
				Log.logInfo(u.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}
