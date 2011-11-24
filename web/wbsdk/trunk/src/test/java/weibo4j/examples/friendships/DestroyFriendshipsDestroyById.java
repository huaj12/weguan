package weibo4j.examples.friendships;

import weibo4j.Friendships;
import weibo4j.examples.Log;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

public class DestroyFriendshipsDestroyById {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String access_token = args[0];
		String uid = args[1];
		Friendships fm = new Friendships(access_token);
		try {
			User fv = fm.destroyFriendshipsDestroyById(uid);
			Log.logInfo(fv.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
