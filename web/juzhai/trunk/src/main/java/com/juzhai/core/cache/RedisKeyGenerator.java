package com.juzhai.core.cache;

public class RedisKeyGenerator extends KeyGenerator {

	public static String genUserCityKey(long uid) {
		return genKey(uid, "city");
	}

	public static String genFriendsKey(long uid) {
		return genKey(uid, "friends");
	}

	public static String genNonAppFriendsKey(long uid) {
		return genKey(uid, "nonAppFriends");
	}

	public static String genMyActsKey(long uid) {
		return genKey(uid, "myActs");
	}

	public static String genInboxActsKey(long uid) {
		return genKey(uid, "inboxActs");
	}
}
