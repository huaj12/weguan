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
	@Deprecated
	public static String genTpFriendsKey(long uid) {
		return genKey(uid, "tpFriends");
	}

	/**
	 * 第三方未安装应用好友列表(value)
	 * 
	 * @param uid
	 * @return
	 */
	public static String genUnInstallFriendsKey(long uid) {
		return genKey(uid, "unInstallFriends");
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

	/**
	 * 未读消息列表(list)
	 * 
	 * @param uid
	 * @return
	 */
	public static String genUnreadMsgsKey(long uid, String className) {
		return genKey(uid, "unread" + className);
	}

	/**
	 * 已读消息列表(list)
	 * 
	 * @param uid
	 * @return
	 */
	public static String genReadMsgsKey(long uid, String className) {
		return genKey(uid, "read" + className);
	}

	/**
	 * 预存消息(list)
	 * 
	 * @param tpIdentity
	 * @param tpId
	 * @return
	 */
	public static String genPrestoreMsgsKey(String tpIdeneity, long tpId,
			String className) {
		return genKey(tpIdeneity, tpId, "prestore" + className);
	}

	/**
	 * 邮件队列
	 * 
	 * @return
	 */
	public static String genMailQueueKey() {
		return "mailQueue";
	}
}
