package weibo4j.examples.account;

import weibo4j.Account;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;

public class GetUid {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String access_token = args[0];
		Account am = new Account(access_token);
		try {
			JSONObject json = am.getUid();
			System.out.println(json.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
