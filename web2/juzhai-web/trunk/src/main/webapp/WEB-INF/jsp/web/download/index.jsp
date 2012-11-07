<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>拒宅网手机版  拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
<link href="${jzr:static('/css/phone.css')}" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="warp"><!--warp begin-->
		<div class="main"><!--main begin-->
				<div class="fix_top"><!--fix_top begin-->
					<c:if test="${empty isQplus||!isQplus}">
						<div class="beta"></div>
					</c:if>
					<div class="top"><!--top begin-->
						<h1><a href="http://www.51juzhai.com"></a></h1>
						<div class="menu"><!--menu begin-->
							<a href="/home" title="首页" <c:if test="${pageType=='home'}">class="selected"</c:if>>首页</a>
							<a href="/searchusers" title="找伴儿" <c:if test="${pageType=='zbe'}">class="selected"</c:if>>找伴儿</a>
							<a href="/showideas" title="出去玩" <c:if test="${pageType=='cqw'}">class="selected"</c:if>>出去玩</a>
							<a href="/rescueuser" title="解救小宅" <c:if test="${pageType=='rescue'}">class="selected"</c:if>>解救小宅</a>
						</div><!--menu end-->
						<div class="search"><!--search begin-->
							<form action="/searchposts" id="base-search-post-form" method="get">
							<div class="s_l"></div>
							<div class="s_m"><!--s_m begin-->
								<input name="queryString" id="base-search-post-input" type="text" init-tip="关键词找伴儿" value=""/>
							</div><!--s_m end-->
							<input type="submit" style="display: none">
							<div class="s_r"><a href="javascript:void(0);"></a></div>
							</form>
						</div><!--search end-->
						<c:if test="${context.uid > 0}">
							<div class="drop_menu my_set"><!--acc begin-->
								<p><a href="javascript:void(0);"></a></p>
								<div class="drop_menu_list" style="display:none;"><!--acc_list begin-->
									<a href="/profile/index">我的资料</a>
									<a href="/profile/index/face">我的头像</a>
									<a href="/profile/preference">拒宅偏好</a>
									<a href="/passport/account">账号密码</a>
									<a href="/home/blacklist/">屏蔽管理</a>
									<c:if test="${empty isQplus||!isQplus}">
										<a href="/authorize/show">授权设置</a>
									</c:if>
									<c:if test="${loginUser.gender==1}">
											<a href="/rescueboy">宅男自救器</a>
									</c:if>
									<!-- <a href="/profile/email">订阅设置</a> -->
									<a href="/logout">退出</a>
								</div><!--acc_list end-->
							</div>
							<!--acc end-->
							<div class="drop_menu my_mail" <c:if test="${messageHide}">style="display: none"</c:if>><!--acc begin-->
								<div id="messageSelect">
									<div class="mail_num" style="display: none"><em class="l"></em><span></span><em class="r"></em></div>
									<p><a href="javascript:void(0);"></a></p>
									<div class="drop_menu_list2" style="display: none" ><!--acc_list begin-->
										<span id="notice-float-5"><a href="/home/dialog/1">私信</a><em></em></span>
										<span id="notice-float-6"><a href="/home/comment/inbox/1">留言</a><em></em></span>
										<span id="notice-float-7"><a href="/home/visitors#visitor-position">新访客</a><em></em></span>
									</div><!--acc_list end-->
								</div>
							</div>
							<!--acc end-->
							<div class="my_iphone" ><!--my_iphone begin-->
								<p></p>
								<a href="/dl/index"></a>
							</div>
							<!--my_iphone end-->
							<div class="my_face_login"><a href="/home/${loginUser.uid}" title="<c:out value="${loginUser.nickname}" />"><img src="${jzr:userLogo(loginUser.uid,loginUser.logoPic,80)}" height="20" width="20" /><p><c:out value="${jzu:truncate(loginUser.nickname,12,'')}"/></p></a></div>
						</c:if>
						<c:if test="${context.uid<=0}">
							<div class="welcome_page_login"><a href="/login" title="登录">登录</a><a href="/passport/register" title="注册">注册</a><a href="/dl/index" title="手机版">手机版</a></div>
						</c:if>
					</div><!--top end-->
				</div><!--fix_top end-->
			<div class="back_top" style="display: none;"><!--back_top begin-->
				<p></p>
				<span><a href="javascript:void(0);"></a></span>
				<p></p>
			</div><!--back_top end-->
			<div class="suggestion"><!--suggestion begin-->
				<span>
					<a href="javascript:void(0);" target-uid="2" target-nickname="拒宅网" class="feed-back"></a>
				</span>
			</div><!--suggestion end-->
			<div class="iphone"><!--iphone begin-->
				<div class="title"></div>
				<div class="detail"><!--detail begin-->
					<span>随时随地查看最新的拒宅信息</span>
					<span>一键响应你感兴趣的拒宅</span>
					<span>及时的拒宅消息通知</span>
					<span>跟小宅们直接对话，相约拒宅</span>
				</div><!--detail end-->
				<div class="phone_bg"><!--phone_bg begin-->
					<p><span class="pic_gd"><img src="${jzr:static('/images/web2/ipone_pic1.jpg')}"></img></span></p>
				</div><!--phone_bg end-->
				<div class="ewm"><!--ewm begin-->
					<p><img src="${jzr:static('/images/web2/ewm.png')}" width="78" height="78"></img></p>
				</div><!--ewm end-->
				<div class="download_btns">
					<a href="https://itunes.apple.com/cn/app/id563109200?ls=1&mt=8" target="_blank" class="ip"></a>
					<a href="http://www.51juzhai.com/dl/android" target="_blank" class="ad"></a>
				</div>
				<div class="iphone_page"><!--iphone_page begin-->
				</div><!--iphone_page end-->
			</div><!--iphone end-->
			<div class="wel_copy iphone_mt">
							拒宅网©2012&nbsp;<a href="http://www.miibeian.gov.cn/" target="_blank">沪ICP备11031778号</a><a href="http://weibo.com/51juzhai" target="_blank">官方微博</a><a href="/about/us" target="_blank">关于我们</a><a href="/searchusers" target="_blank">找伴儿</a><a href="/showideas" target="_blank">出去玩</a><br />
							友情链接：<a href="http://www.douban.com/" target="_blank">豆瓣</a><a href="http://www.xuejineng.cn/" target="_blank">学技能</a><a href="http://app.tongbu.com/" target="_blank">同步助手</a><a href="http://www.party021.com" target="_blank">上海派对网</a><a href="http://www.365ddt.com/" target="_blank">店店通</a><a href="http://www.25pp.com/" target="_blank">iPhone游戏</a><a href="http://www.gzhong.cn/" target="_blank">工众网</a>
			</div>
			<jsp:include page="/WEB-INF/jsp/web/common/count.jsp" />
		</div><!--main end-->
	</div><!--warp end-->
	<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
</body>
</html>
