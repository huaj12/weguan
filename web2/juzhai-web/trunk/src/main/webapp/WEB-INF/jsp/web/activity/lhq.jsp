<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>拒宅零花钱</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="huodong"><!--welcome begin-->
					<div class="title_tmz"><!--title begin-->
						<h2>拒宅零花钱</h2>
						<p>邀请身边的朋友脱宅，赢取<font>168元</font>拒宅活动经费</p>
					</div><!--title end-->
					<div class="huodong_t"></div>
					<div class="huodong_m"><!--huodong_m begin-->
						<div class="tmz"><!--tmz begin-->
							<div class="tmz_left"><!--tmz_left begin-->
								<div class="banner"></div>
								<c:choose>
									<c:when test="${context.uid <= 0}">
										<div class="tmz_face"><p>你还未加入加入拒宅网，暂不能参加</p><a href="/login?turnTo=/activity/lhq">立即加入&gt;&gt;</a></div>
									</c:when>
									<c:when test="${empty loginUser.logoPic}">
										<div class="tmz_face"><p>你还未通过头像审核，暂不能参加本次活动哦。</p><a href="/profile/index/face">去上传头像&gt;&gt;</a></div>
									</c:when>
									<c:otherwise>
										<div class="tmz_fz"><!--tmz_fz begin-->
											<b>让好友通过下面的链接加入并通过头像审核即可参与抽奖：</b>
											<div class="text_area"><textarea readonly="readonly">刚发现一个小清新的脱宅网站&nbsp;——&nbsp;拒宅网&nbsp;http://www.51juzhai.com/activity/invite/${token}</textarea></div>
										</div><!--tmz_fz end-->
										<div class="btn"><div class="icon"></div><a href="javascript:void(0);">复制</a><p>通过qq，msn，微博等发给朋友</p></div>
										<div class="clear"></div>
										<div class="my_tmz"><!--my_tmz begin-->
											<em>我已邀请了${fn:length(userList)}个人，其中${logoVerifyCount}人通过头像审核</em>
											<c:forEach var="user" items="${userList}">
												<p><img src="${jzr:userLogo(user.uid,user.logoPic,80)}" alt="<c:out value='${user.nickname}' />" title="<c:out value='${user.nickname}' />" width="80" height="80"/></p>
											</c:forEach>
										</div><!--my_tmz end-->
									</c:otherwise>
								</c:choose>
							</div><!--tmz_left end-->
							<div class="tmz_right"><!--tmz_right right-->
								<b>活动规则：</b>
								<p>邀请朋友加入拒宅网并上传真实头像，即可获得一次抽奖机会。邀请的人越多获，中奖概率越大。</p>
								<b>活动时间：</b>
								<p>开奖时间 2012年4月13日 00:00</p>
								<b>奖项设置与发放：</b>
								<p>
									中奖者1名，可一次性获得168元的拒宅经费<br />
									奖金通过支付宝或网银支付<br />
									我们会通过私信确认具体获奖事宜，请获奖者留意
								</p>
								<b>补充说明：</b>
								<p>
									仅限人类参加，机器人或机构参加取消资格。<br />
									获奖者领取奖金后，我们会在网站上公示获奖人。<br />
									通过你的邀请链接注册的人才算你邀请进来的。<br />
									邀请进来的人未通过头像审核不算成功邀请。
								</p>
							</div><!--tmz_right end-->
						</div><!--tmz end-->
					</div><!--huodong_m end-->
					<div class="clear"></div>
					<div class="huodong_b"></div>
				</div><!--welcome end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/jquery/jquery.zclip.min.js')}"></script>
			<script type="text/javascript" src="${jzr:static('/js/web/activity.js')}"></script>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>