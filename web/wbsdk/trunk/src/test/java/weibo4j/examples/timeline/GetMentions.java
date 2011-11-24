package weibo4j.examples.timeline;

import java.util.List;

import weibo4j.Timeline;
import weibo4j.examples.Log;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

public class GetMentions {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String access_token = args[0];
		Timeline tm = new Timeline(access_token);
		try {
			List<Status> status = tm.getMentions();
			for (Status s : status) {
				Log.logInfo(s.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
