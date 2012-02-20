package com.juzhai.core.cache;

import com.juzhai.msg.bean.ActMsg.MsgType;

public class MemcachedKeyGenerator extends KeyGenerator {

	/**
	 * 授权信息
	 * 
	 * @param uid
	 * @return
	 */
	public static String genAuthInfoKey(long uid) {
		return genKey(uid, "authInfo");
	}

	/**
	 * 是否缓存或者过期好友的标志
	 * 
	 * @param uid
	 * @return
	 */
	public static String genCachedFriendsKey(long uid) {
		return genKey(uid, "cachedFriends");
	}

	/**
	 * 用户基本信息缓存
	 * 
	 * @param uid
	 * @return
	 */
	public static String genProfileCacheKey(long uid) {
		return genKey(uid, "profileCache");
	}

	/**
	 * Act缓存
	 * 
	 * @param actId
	 * @return
	 */
	public static String genActCacheKey(long actId) {
		return genKey(actId, "actCache");
	}

	/**
	 * 最后一次推送时间
	 * 
	 * @param senderId
	 * @param receiverId
	 * @return
	 */
	public static String genLastPushTimeKey(long senderId, long receiverId) {
		return genKey(senderId, "lastPush_" + receiverId);
	}

	/**
	 * 推送惩罚连续次数
	 * 
	 * @param senderId
	 * @param receiverId
	 * @return
	 */
	public static String genPushPunishTimesKey(long senderId, long receiverId) {
		return genKey(senderId, "punishTimes_" + receiverId);
	}

	/**
	 * 是否第三方通知Key
	 * 
	 * @param uid
	 * @return
	 */
	public static String genSetupTpAdviseKey(long uid) {
		return genKey(uid, "tpAdvise");
	}

	/**
	 * 在限额时间内，收到的第三方邀请信息的数量
	 * 
	 * @return
	 */
	public static String genTpMsgReceiveCnt(long uid, MsgType msgType) {
		return genKey(uid, "tpMsgReceiveCnt_" + msgType.name());
	}

	/*------------------------web----------------------------*/

	/**
	 * 用户是否在线
	 * 
	 * @param uid
	 * @return
	 */
	public static String genUserOnlineKey(long uid) {
		return genKey(uid, "online");
	}

	/**
	 * 空闲时间列表
	 * 
	 * @param uid
	 * @return
	 */
	public static String genUserFreeDateListKey(long uid) {
		return genKey(uid, "userFreeDateList");
	}

	/**
	 * 对话内容对象
	 * 
	 * @param dialogContentId
	 * @return
	 */
	public static String genDialogContentKey(long dialogContentId) {
		return genKey(dialogContentId, "dialogContent");
	}

	/**
	 * 禁止用户post
	 * 
	 * @param uid
	 * @return
	 */
	public static String genPostForbidKey(long uid) {
		return genKey(uid, "postForbid");
	}

	/**
	 * 缓存用户微博
	 */
	public static String genUserWeiboKey(long uid) {
		return genKey(uid, "userWeibo");
	}

	/**
	 * 用户总的被响应数
	 * 
	 * @param uid
	 * @return
	 */
	public static String genAllResponseCnt(long uid) {
		return genKey(uid, "allResponseCnt");
	}

	/**
	 * post缓存
	 * 
	 * @param actId
	 * @return
	 */
	public static String genPostCacheKey(long postId) {
		return genKey(postId, "postCache");
	}
}
