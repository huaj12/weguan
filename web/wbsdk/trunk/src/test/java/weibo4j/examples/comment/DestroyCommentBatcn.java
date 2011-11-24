package weibo4j.examples.comment;

import java.util.List;

import weibo4j.Comments;
import weibo4j.examples.Log;
import weibo4j.model.Comment;
import weibo4j.model.WeiboException;

public class DestroyCommentBatcn {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String access_token = args[0];
		String ids = args[1];
		Comments cm = new Comments(access_token);
		try {
			List<Comment> com = cm.destoryCommentBatch(ids);
			Log.logInfo(com.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
