package weibo4j.examples.trends;

import weibo4j.Trend;
import weibo4j.examples.Log;
import weibo4j.model.UserTrend;
import weibo4j.model.WeiboException;

public class TrendsFollow {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String access_token = args[0];
		Trend tm = new Trend(access_token);
		String trend_name = args[1];
		try {
			UserTrend ut = tm.trendsFollow(trend_name);
			Log.logInfo(ut.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
