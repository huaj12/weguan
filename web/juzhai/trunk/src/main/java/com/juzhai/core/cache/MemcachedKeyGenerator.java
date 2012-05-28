package com.juzhai.core.cache;

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
	@Deprecated
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
	@Deprecated
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
	 * 用户激活邮件发送key
	 * 
	 * @param uid
	 * @return
	 */
	public static String genActiveMailSentKey(long uid) {
		return genKey(uid, "activeMailSent");
	}

	/**
	 * 用户重置邮件发送key
	 * 
	 * @param uid
	 * @return
	 */
	public static String genResetMailSentKey(long uid) {
		return genKey(uid, "resetMailSent");
	}

	/**
	 * 登录错误次数
	 * 
	 * @param ip
	 * @return
	 */
	public static String genLoginCountKey(String ip) {
		return ip + CACHE_KEY_SEPARATOR + "loginCount";
	}

	/**
	 * 用户创建好主意次数
	 * 
	 * @param uid
	 * @return
	 */
	// TODO (done) 这个限制只是用作分享，还是包括手动推荐好主意？(包括手动推荐也有次数限制)
	public static String genCreateIdeaCountKey(long uid) {
		// TODO (done) genKey方法不能用？
		return genKey(uid, "createIdeaCount");
	}
}
