<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
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
<jsp:include page="/WEB-INF/jsp/web/common/back_top.jsp" />
<div class="suggestion"><!--suggestion begin-->
	<span>
		<a href="javascript:void(0);" target-uid="2" target-nickname="拒宅网" class="feed-back"></a>
	</span>
</div><!--suggestion end-->
