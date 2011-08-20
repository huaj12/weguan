package com.juzhai.core.cache;

import com.juzhai.act.bean.ActDealType;

public class RedisKeyGenerator extends KeyGenerator {

	/**
	 * 所在城市
	 * 
	 * @param uid
	 * @return
	 */
	public static String genUserCityKey(long uid) {
		return genKey(uid, "city");
	}

	/**
	 * 还有列表(zset)
	 * 
	 * @param uid
	 * @return
	 */
	public static String genFriendsKey(long uid) {
		return genKey(uid, "friends");
	}

	/**
	 * 第三方好友列表(value)
	 * 
	 * @param uid
	 * @return
	 */
	public static String genTpFriendsKey(long uid) {
		return genKey(uid, "tpFriends");
	}

	/**
	 * 我的Act列表(zset)
	 * 
	 * @param uid
	 * @return
	 */
	public static String genMyActsKey(long uid) {
		return genKey(uid, "myActs");
	}

	/**
	 * 收件箱列表(zset)
	 * 
	 * @param uid
	 * @return
	 */
	public static String genInboxActsKey(long uid) {
		return genKey(uid, "inboxActs");
	}

	/**
	 * 已处理Act列表(list)
	 * 
	 * @param uid
	 * @return
	 */
	public static String genDealedActsKey(long uid, ActDealType type) {
		return genKey(uid, type.name());
	}
}
