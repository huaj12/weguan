package weibo4j.examples.user;

import java.util.List;

import weibo4j.Friendships;
import weibo4j.Timeline;
import weibo4j.Users;
import weibo4j.examples.Log;
import weibo4j.model.Status;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

public class ShowUser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String access_token ="2.004HpPVCbmClxD49014e1c770WOXNl";
		String uid = "2294103501";
		Users um = new Users(access_token);
//		Timeline timeline = new Timeline(uid);
		try {
//			List<Status> status = timeline.getUserTimeline(uid, "",
//					1, null, 0, 1);
			User user = um.showUserById(uid);
			String str[]=user.getLocation().split(" ");
			for(String s:str){
			System.out.println(s);
			}
//			Friendships fm = new Friendships(access_token);
//			List<User> users = fm.getFriendsBilateral(uid);
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
