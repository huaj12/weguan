package weibo4j.examples.comment;

import java.util.List;

import weibo4j.Comments;
import weibo4j.examples.Log;
import weibo4j.model.Comment;
import weibo4j.model.WeiboException;

public class GetCommentById {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String access_token = args[0];
		String id = args[1];
		Comments cm = new Comments(access_token);
		try {
			List<Comment> comment = cm.getCommentById(id);
			for (Comment c : comment) {
				Log.logInfo(c.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
