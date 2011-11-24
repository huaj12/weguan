package weibo4j.examples.tags;

import weibo4j.Tags;
import weibo4j.examples.Log;
import weibo4j.model.WeiboException;

public class DestroyTag {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String access_token = args[0];
		Tags tm = new Tags(access_token);
		boolean result = false;
		int tag_id = Integer.parseInt(args[2]);
		try {
			result = tm.destoryTag(tag_id);
			Log.logInfo(String.valueOf(result));
		} catch (WeiboException e) {

			e.printStackTrace();
		}
	}

}
