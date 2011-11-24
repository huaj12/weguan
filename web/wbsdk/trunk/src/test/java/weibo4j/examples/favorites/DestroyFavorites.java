package weibo4j.examples.favorites;

import weibo4j.Favorite;
import weibo4j.examples.Log;
import weibo4j.model.Favorites;
import weibo4j.model.WeiboException;

public class DestroyFavorites {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String access_token = args[0];
		Favorite fm = new Favorite(access_token);
		String uid = args[1];
		try {
			Favorites favors = fm.destroyFavorites(uid);
			Log.logInfo(favors.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
