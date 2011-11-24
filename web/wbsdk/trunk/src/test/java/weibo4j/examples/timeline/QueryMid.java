package weibo4j.examples.timeline;

import weibo4j.Timeline;
import weibo4j.examples.Log;
import weibo4j.model.WeiboException;

public class QueryMid {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String access_token = args[0];
		String id = args[1];
		Timeline tm = new Timeline(access_token);
		try {
			String mid = tm.QueryMid(1, id);
			Log.logInfo(mid);
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
