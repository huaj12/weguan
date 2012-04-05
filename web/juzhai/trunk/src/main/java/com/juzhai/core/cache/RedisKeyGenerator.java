package com.juzhai.core.cache;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.juzhai.home.bean.ReadFeedType;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.notice.bean.NoticeType;
import com.juzhai.passport.InitData;
import com.juzhai.passport.model.Thirdparty;

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
	 * 好友列表(zset)
	 * 
	 * @param uid
	 * @return
	 */
	public static String genFriendsKey(long uid) {
		return genKey(uid, "friends");
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
	 * 第三方安装的认识的人列表
	 * 
	 * @param uid
	 * @return
	 */
	public static String genInstallFollowsKey(long uid) {
		return genKey(uid, "installFollows");
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

	/**
	 * 活跃用户的Uid ZSET
	 * 
	 * @return
	 */
	public static String genActivistsKey(long tpId) {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		String tpName = tp == null ? StringUtils.EMPTY : tp.getName();
		return tpName + "_activists";
	}

	/**
	 * 活跃用户的Uid ZSET
	 * 
	 * @return
	 */
	public static String genActivistsKey(String tpName) {
		return tpName == null ? StringUtils.EMPTY : tpName + "_activists";
	}

	/**
	 * 用户最新的Act
	 * 
	 * @param uid
	 * @return
	 */
	public static String genUserNewestActKey(long uid) {
		return genKey(uid, "userNewestAct");
	}

	/**
	 * 排行榜
	 * 
	 * @param date
	 * @return
	 */
	public static String genActDayRankKey(Date date) {
		return "actRank_" + DateUtils.truncate(date, Calendar.DATE).getTime();
	}

	/**
	 * 各个平台下的项目流行度
	 * 
	 * @param actId
	 * @param tpId
	 * @return
	 */
	public static String genTpActPopularityKey(long actId, long tpId) {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		String tpName = tp == null ? StringUtils.EMPTY : tp.getName();
		return genKey(actId, tpName + "_actPopularity");
	}

	/**
	 * 各个平台下的项目流行度
	 * 
	 * @param actId
	 * @param tpName
	 * @return
	 */
	public static String genTpActPopularityKey(long actId, String tpName) {
		return genKey(actId, tpName == null ? StringUtils.EMPTY : tpName
				+ "_actPopularity");
	}

	/**
	 * 平台用户已安装用户列表
	 * 
	 * @param tpName
	 * @return
	 */
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
}
