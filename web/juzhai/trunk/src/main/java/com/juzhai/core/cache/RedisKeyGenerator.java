package com.juzhai.core.cache;

import com.juzhai.home.bean.ReadFeedType;
import com.juzhai.msg.bean.ActMsg.MsgType;

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
	public static String genReadFeedsKey(ReadFeedType type) {
		return "readFeed_" + type.name();
	}

	/**
	 * 已处理Act列表(list)
	 * 
	 * @param uid
	 * @return
	 */
	public static String genNillActsKey(long uid) {
		return genKey(uid, "nillActs");
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

	public static String genUnReadMsgCountKey(long uid, String className) {
		return genKey(uid, "unreadcount" + className);
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

	/**
	 * Act 近义词
	 * 
	 * @param actId
	 * @return
	 */
	public static String genActSynonymKey(long actId) {
		return genKey(actId, "synonym");
	}

	/**
	 * 已经做过题目的列表
	 * 
	 * @param userId
	 * @return
	 */
	public static String genQuestionUsersKey(long userId) {
		return genKey(userId, "question");
	}

	/**
	 * 存放所有记录了已做题目的Key的key
	 * 
	 * @return
	 */
	public static String genQuestionUserKeysKey() {
		return "questionUserKeys";
	}

	/**
	 * 用户数据用来DES加密的密钥
	 * 
	 * @param userId
	 * @return
	 */
	public static String genUserSecretKey(long userId) {
		return genKey(userId, "secretKey");
	}

	/**
	 * 打开邮件的统计
	 * 
	 * @return
	 */
	public static String genOpenEmailStatKey() {
		return "openEmailStat";
	}

	public static String genLazyMessageKey(long uid, long receiverId,
			MsgType type, String className) {
		return genKey(uid, receiverId, type, "lazy" + className);
	}

	/**
	 * 延迟发送的msg
	 * 
	 * @return
	 */
	public static String genLazyMsgKey() {
		return "lazyMsgKey";
	}

	/**
	 * 延迟发送的msg
	 * 
	 * @return
	 */
	public static String genMergerMsgKey() {
		return "mergerMsgKey";
	}

	/**
	 * Act 屏蔽
	 * 
	 * @return
	 */
	public static String genActShieldKey() {
		return "actShield";
	}
}
