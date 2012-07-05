package com.juzhai.core.cache;

import com.juzhai.notice.bean.NoticeType;

public class RedisKeyGenerator extends KeyGenerator {

	/**
	 * 所在城市
	 * 
	 * @param uid
	 * @return
	 */
	@Deprecated
	public static String genUserCityKey(long uid) {
		return genKey(uid, "city");
	}

	/**
	 * 好友列表(zset)
	 * 
	 * @param uid
	 * @return
	 */
	@Deprecated
	public static String genFriendsKey(long uid) {
		return genKey(uid, "friends");
	}

	/**
	 * 第三方未安装应用好友列表(value)
	 * 
	 * @param uid
	 * @return
	 */
	@Deprecated
	public static String genUnInstallFriendsKey(long uid) {
		return genKey(uid, "unInstallFriends");
	}

	/**
	 * 第三方安装的认识的人列表
	 * 
	 * @param uid
	 * @return
	 */
	@Deprecated
	public static String genInstallFollowsKey(long uid) {
		return genKey(uid, "installFollows");
	}

	/**
	 * 邮件队列
	 * 
	 * @return
	 */
	public static String genMailQueueKey(String name) {
		return name + ".mailQueue";
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

	/**
	 * 平台用户已安装用户列表
	 * 
	 * @param tpName
	 * @return
	 */
	@Deprecated
	public static String genTpInstallUsersKey(String tpName) {
		return tpName + ".installUsers";
	}

	/*-------------------------------web------------------------------------*/

	/**
	 * 感兴趣的人列表
	 * 
	 * @param uid
	 * @return
	 */
	public static String genInterestUsersKey(long uid) {
		return genKey(uid, "interestUsers");
	}

	/**
	 * 约的人列表
	 * 
	 * @param uid
	 * @return
	 */
	public static String genDatingUsersKey(long uid) {
		return genKey(uid, "datingUsers");
	}

	/**
	 * 通知数量
	 * 
	 * @param uid
	 * @param noticeType
	 * @return
	 */
	public static String genUserNoticeNumKey(long uid, NoticeType noticeType) {
		return genKey(uid, noticeType.getType() + "_noticeNum");
	}

	/**
	 * 订阅通知Key
	 * 
	 * @param uid
	 * @param noticeType
	 * @return
	 */
	public static String genUserSubNoticeKey(long uid, NoticeType noticeType) {
		return genKey(uid, noticeType.getType() + "_emailSub");
	}

	/**
	 * 对话内容列表
	 * 
	 * @param dialogId
	 * @return
	 */
	public static String genDialogContentsKey(long dialogId) {
		return genKey(dialogId, "dialogContentList");
	}

	/**
	 * 用户最新一条post
	 * 
	 * @param uid
	 * @return
	 */
	public static String genUserLatestPostKey(long uid) {
		return genKey(uid, "latestPost");
	}

	/**
	 * 响应的Post列表
	 * 
	 * @param uid
	 * @return
	 */
	public static String genResponsePostsKey(long uid) {
		return genKey(uid, "responsePosts");
	}

	/**
	 * 好主意使用者
	 * 
	 * @param ideaId
	 * @return
	 */
	public static String genIdeaUsersKey(long ideaId) {
		return genKey(ideaId, "ideaUsers");
	}

	/**
	 * 需要被通知的人列表
	 * 
	 * @return
	 */
	public static String genNoticeUsersKey() {
		return "noticeUserList";
	}

	/**
	 * 欢迎页idea数据
	 */
	public static String genIdeaWindowSortKey() {
		return "ideaWindowSortList";
	}

	/**
	 * 评论收件箱Key
	 * 
	 * @param uid
	 * @return
	 */
	public static String genCommentInboxKey(long uid) {
		return genKey(uid, "commentIndex");
	}

	/**
	 * 首页无内容推荐key
	 * 
	 * @return
	 */
	public static String genIndexRecommendPostKey() {
		return "indexRecommendPost";
	}

	/**
	 * 用户微博list key
	 * 
	 * @param uid
	 * @return
	 */
	public static String genUserStatusKey(long uid) {
		return genKey(uid, "userStatus");
	}

	/**
	 * 猜你喜欢的用户列表key
	 * 
	 * @param uid
	 * @return
	 */
	public static String genGuessYouLikeUsersKey(long uid) {
		return genKey(uid, "guessYouLikeUsers");
	}

	/**
	 * 解救小宅的列表Key
	 * 
	 * @param uid
	 * @return
	 */
	public static String genRescueUsersKey(long uid) {
		return genKey(uid, "rescueUsers");
	}

	/**
	 * 偶遇地点id
	 * 
	 * @return
	 */
	public static String genOccasionalId() {
		return "occasionalId";
	}

	/**
	 * 获取黑名单key
	 * 
	 * @param uid
	 * @return
	 */
	public static String genBlacklistKey(long uid) {
		return genKey(uid, "blacklistindex");
	}

	/**
	 * 优质用户列表key
	 * 
	 * @return
	 */
	public static String genHighQualityUsersKey() {
		return "highQualityUsers";
	}

	/**
	 * 持久化登录的token
	 * 
	 * @param uid
	 * @return
	 */
	public static String genPersistLoginTokenKey(long uid) {
		return genKey(uid, "persistLoginToken");
	}

	/**
	 * 欢迎页好主意推荐key
	 * 
	 * @return
	 */
	public static String genIndexRecommendIdeaKey() {
		return "indexRecommendIdea";
	}

	/**
	 * 来访者列表Key
	 * 
	 * @param uid
	 * @return
	 */
	public static String genVisitUsersKey(long uid) {
		return genKey(uid, "visitUsers");
	}

	/**
	 * 近期每个分类下最火的idea
	 * 
	 * @return
	 */
	public static String genRecentTopIdeasKey() {
		return "recentTopIdeas";
	}

	/**
	 * 感兴趣列表
	 * 
	 * @param uid
	 * @return
	 */
	public static String genInterestIdeasKey(long uid) {
		return genKey(uid, "interestIdeas");
	}

	/**
	 * 用户添加私信或留言数
	 * 
	 * @param uid
	 * @return
	 */
	public static String genSendCount(String function, long uid) {
		return genKey(uid, function + "SendCount");
	}

	/**
	 * Q+新用户key
	 * 
	 * @return
	 */
	public static String genQplusPushNewUserKey() {
		return "qplugPushNewUsers";
	}

	/**
	 * Q+老用户key
	 * 
	 * @return
	 */
	public static String genQplusPushOldUserKey() {
		return "qplugPushOldUsers";
	}

	/**
	 * 某个城市下的机器人用户
	 * 
	 * @param city
	 * @return
	 */
	public static String genRobotUserKey(long city) {
		return genKey(city, "robotUserKey");
	}

}
