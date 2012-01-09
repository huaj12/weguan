<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="fix_top">
	<div class="top"><!--top begin-->
		<h1></h1>
		<div class="area"><!--area begin-->
			<c:choose>
				<c:when test="${channelId==0}">
					<p>全国</p>
				</c:when>
				<c:otherwise>
					<p>${jzd:cityName(channelId)}</p>
				</c:otherwise>
			</c:choose>
			<div class="area_list" style="display:none;">
				<!--about_list begin-->
				<a href="javascript:void(0);" cityid="2">上海</a>
				<a href="javascript:void(0);" cityid="1">北京</a>
				<a href="javascript:void(0);" cityid="0">全国</a>
			</div><!--about_list end-->
		</div><!--area end-->
		<div class="menu"><!--menu begin-->
			<a href="/showUsers" title="找伴儿" <c:if test="${pageType=='zbe'}">class="selceted"</c:if>>找伴儿</a> <a href="/showActs" title="出去玩" <c:if test="${pageType=='cqw'}">class="selceted"</c:if>>出去玩</a>
		</div><!--menu end-->
		<c:if test="${context.uid>0}">
			<div class="acc"><!--acc begin-->
				<p class=""><a href="javascript:void(0);">帐号</a></p>
				<div class="acc_list" style="display:none;"><!--acc_list begin-->
					<a href="/profile/index">我的资料</a>
					<a href="/profile/index/face">个人形象</a>
					<a href="/profile/email">订阅设置</a>
					<a href="/logout">退出</a>
				</div><!--acc_list end-->
			</div><!--acc end-->
			<div class="my_message"><!--my_message begin-->
				<div id="messageSelect">
					<p class=""><a href="javascript:void(0);">消息</a></p>
					<div class="my_message_show" style="display: none; z-index: 999"><!--use_set_center begin-->
						<span id="notice1"><a href="/home/interestMes/1">敲过我门的人</a><em></em></span>
						<span id="notice5"><a href="/notice/sysNotices">私信</a><em></em></span>
						<span id="notice2"><a href="/home/datingMes/1">约我的人</a><em></em></span>
						<span id="notice3"><a href="/home/datings/accept/1">接受我约的人</a><em></em></span>
						<span id="notice4"><a href="/notice/sysNotices">系统通知</a><em></em></span>
					</div><!--use_set_center end-->
				</div>
				<div class="my_message_show" style="z-index: 1"><!--use_set_center begin-->
					<span id="notice1" style="display: none;"><a href="/home/interestMes/1">敲过我门的人</a><em></em></span>
					<span id="notice5" style="display: none;"><a href="/home/dialog/1">私信</a><em></em></span>
					<span id="notice2" style="display: none;"><a href="/home/datingMes/1">约我的人</a><em></em></span>
					<span id="notice3" style="display: none;"><a href="/home/datings/accept/1">接受我约的人</a><em></em></span>
					<span id="notice4" style="display: none;"><a href="/notice/sysNotices">系统通知</a><em></em></span>
				</div><!--use_set_center end-->
			</div><!--my_message end-->
		</c:if>
		<div class="user_area"><!--user_area begin-->
			<c:choose>
				<c:when test="${context.uid<=0}">
					<div class="unlogin"><a href="javascript:void(0);">加入拒宅</a></div>
				</c:when>
				<c:otherwise>
					<div class="login"><!--login begin-->
						<div class="user_box"><!--user_box begin-->
							<p><img src="${jzr:userLogo(loginUser.uid,loginUser.logoPic,80)}" height="20" width="20" /></p>
							<a href="/home" class="user <c:choose><c:when test='${loginUser.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><c:out value="${loginUser.nickname}" /></a>
						</div><!--user_box end-->
					</div><!--login end-->
				</c:otherwise>
			</c:choose>
		</div><!--user_area end-->
		<div class="search"><!--search begin-->
			<div class="s_l"></div>
			<div class="s_m"><!--s_m begin-->
				<input name="" type="text"  value="输入拒宅项目,如:打台球"/>
			</div><!--s_m end-->
			<div class="s_r"><a href="#"></a></div>
			<div class="xl_menu" style="display: none;"><!--xl_menu begin-->
				<a href="#">打篮球</a>
				<a href="#">打台球</a>
				<a href="#">打电动</a>
				<a href="#">打篮球</a>
			</div><!--xl_menu end-->
		</div><!--search end-->
	</div><!--top end-->
</div>