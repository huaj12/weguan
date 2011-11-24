package weibo4j.examples.account;

import java.util.List;

import weibo4j.Account;
import weibo4j.examples.Log;
import weibo4j.model.School;
import weibo4j.model.WeiboException;

public class GetAccountPrpfileSchoolList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String access_token = args[0];// 输入授权后的AccessToken
		Account am = new Account(access_token);
		try {
			List<School> schools = am.getAccountPrpfileSchoolList();
			for (School school : schools) {
				Log.logInfo(school.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
