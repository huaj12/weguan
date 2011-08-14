package com.juzhai.core.cache;

public class MemcachedKeyGenerator extends KeyGenerator {

	public static String genAuthInfoKey(long uid) {
		return genKey(uid, "authInfo");
	}

	public static String genCachedFriendsKey(long uid) {
		return genKey(uid, "cachedFriends");
	}

}
