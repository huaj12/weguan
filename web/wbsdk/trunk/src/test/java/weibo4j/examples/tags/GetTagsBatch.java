package weibo4j.examples.tags;

import java.util.List;

import weibo4j.Tags;
import weibo4j.examples.Log;
import weibo4j.model.Tag;
import weibo4j.model.WeiboException;

public class GetTagsBatch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String access_token = args[0];
		Tags tm = new Tags(access_token);
		List<Tag> tags = null;
		String uids = args[1];
		try {
			;
			tags = tm.getTagsBatch(uids);
			for (Tag tag : tags) {
				Log.logInfo(tag.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}
