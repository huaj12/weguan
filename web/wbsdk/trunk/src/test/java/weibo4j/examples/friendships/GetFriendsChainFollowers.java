package weibo4j.examples.friendships;

import java.util.List;

import weibo4j.Friendships;
import weibo4j.examples.Log;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

public class GetFriendsChainFollowers {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String access_token = args[0];
		String uid = args[1];
		Friendships fm = new Friendships(access_token);
		try {
			List<User> users = fm.getFriendsChainFollowers(uid);
			for (User s : users) {
				Log.logInfo(s.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
