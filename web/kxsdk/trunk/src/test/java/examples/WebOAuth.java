package examples;

import java.util.List;
import kx4j.AppStatus;
import kx4j.UIDs;
import kx4j.InvitedUIDs;
import kx4j.User;
import kx4j.KxSDK;
import kx4j.KxException;
import kx4j.http.AccessToken;
import kx4j.http.RequestToken;

public class WebOAuth {

	public static RequestToken request(String backUrl) {
		try {
			System.setProperty("kx4j.oauth.consumerKey", KxSDK.CONSUMER_KEY);
			System.setProperty("kx4j.oauth.consumerSecret",KxSDK.CONSUMER_SECRET);
                        
			KxSDK kxSDK = new KxSDK();
                        
			RequestToken requestToken = kxSDK.getOAuthRequestToken(backUrl);
                        
			System.out.println("Got request token.");
			System.out.println("Request token: " + requestToken.getToken());
			System.out.println("Request token secret: "
					+ requestToken.getTokenSecret());
			return requestToken;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static AccessToken requstAccessToken(RequestToken requestToken,
			String verifier) {
		try {
			System.setProperty("kx4j.oauth.consumerKey", KxSDK.CONSUMER_KEY);
			System.setProperty("kx4j.oauth.consumerSecret",
					KxSDK.CONSUMER_SECRET);

			KxSDK kxSDK = new KxSDK();
			AccessToken accessToken = kxSDK.getOAuthAccessToken(requestToken
					.getToken(), requestToken.getTokenSecret(), verifier);

			System.out.println("Got access token.");
			System.out.println("access token: " + accessToken.getToken());
			System.out.println("access token secret: "
					+ accessToken.getTokenSecret());
			return accessToken;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
        
        public static User getMyInfo(AccessToken access, String fields) {
                User user =  null;
		try {
			KxSDK kxSDK = new KxSDK();
			kxSDK.setToken(access.getToken(), access.getTokenSecret());
                        user = kxSDK.getMyInfo(fields);
			System.out.println("Successfully get My Info: ["
			                + user.getUid() + "].");
		} catch (KxException e) {
			e.printStackTrace();
		}
                
                return user;
	}
        
        public static List<User> getUsers(AccessToken access, String uids, String fields, int start, int num) {
                List<User> userList =  null;
		try {
			KxSDK kxSDK = new KxSDK();
			kxSDK.setToken(access.getToken(), access.getTokenSecret());
                        userList = kxSDK.getUsers(uids, fields, start, num);
			System.out.println("Successfully get My Info: ["
			                + userList.size() + "].");
		} catch (KxException e) {
			e.printStackTrace();
		}
                
                return userList;
	}
        
        public static List<User> getFriends(AccessToken access, String fields, int start, int num) {
                List<User> userList =  null;
		try {
			KxSDK kxSDK = new KxSDK();
			kxSDK.setToken(access.getToken(), access.getTokenSecret());
                        userList = kxSDK.getFriends(fields, start, num);
			System.out.println("Successfully get My Info: ["
			                + userList.size() + "].");
		} catch (KxException e) {
			e.printStackTrace();
		}
                
                return userList;
	}
        
        public static int getRelationShip(AccessToken access, long uid1, long uid2) {
                int relationship = -1;
		try {
			KxSDK kxSDK = new KxSDK();
			kxSDK.setToken(access.getToken(), access.getTokenSecret());
                        relationship = kxSDK.getRelationShip(uid1, uid2);
			System.out.println("Successfully get My Info: ["
			                + relationship + "].");
		} catch (KxException e) {
			e.printStackTrace();
		}
                
                return relationship;
	}
        
         public static List<AppStatus> getAppStatus(AccessToken access, String uids, int start, int num) {
                List<AppStatus> appStatuses =  null;
		try {
			KxSDK kxSDK = new KxSDK();
			kxSDK.setToken(access.getToken(), access.getTokenSecret());
                        appStatuses = kxSDK.getAppStatus(uids, start, num);
			System.out.println("Successfully get My Info: ["
			                + appStatuses.size() + "].");
		} catch (KxException e) {
			e.printStackTrace();
		}
                
                return appStatuses;
	}
         
         public static UIDs getAppFriendUids(AccessToken access, int start, int num) {
                UIDs uidsObj =  null;
		try {
			KxSDK kxSDK = new KxSDK();
			kxSDK.setToken(access.getToken(), access.getTokenSecret());
                        uidsObj = kxSDK.getAppFriendUids(start, num);
			System.out.println("Successfully get My Info: ["
			                + uidsObj.toString() + "].");
		} catch (KxException e) {
			e.printStackTrace();
		}
                
                return uidsObj;
	}
         
         public static InvitedUIDs getAppInvitedUids(AccessToken access, long uid, int start, int num) {
                InvitedUIDs intvitedUidsObj =  null;
		try {
			KxSDK kxSDK = new KxSDK();
			kxSDK.setToken(access.getToken(), access.getTokenSecret());
                        intvitedUidsObj = kxSDK.getAppInvitedUids(uid, start, num);
                        if(intvitedUidsObj == null) {
                            System.out.println("no data!");
                        } else {
                            System.out.println("Successfully get My Info: ["
			                + intvitedUidsObj.toString() + "].");
                        }
		} catch (KxException e) {
			e.printStackTrace();
		}
                
                return intvitedUidsObj;
	}
}
