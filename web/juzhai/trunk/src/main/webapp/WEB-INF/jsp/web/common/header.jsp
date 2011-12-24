<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<a href="#" title="" class="selceted">找伴儿</a> <a href="#" title="">出去玩</a>
	</div><!--menu end-->
	<div class="about"><!--about begin-->
		<p>关于</p>
		<div class="about_list" style="display:none;"><!--about_list begin-->
			<a href="#">关于我们</a> <a href="#">安全拒宅</a> <a href="#">系统通知</a> <a
				href="#">微博拒宅器</a> <a href="#">人人拒宅器</a> <a href="#">开心拒宅器</a> <a
				href="#">安卓拒宅器</a>
		</div>
		<!--about_list end-->
	</div><!--about end-->
	<div class="user_area"><!--user_area begin-->
		<div class="unlogin" style="display: none;">
			<a href="#">加入拒宅</a>
		</div>
		<div class="login"><!--login begin-->
			<div class="user_box"><!--user_box begin-->
				<c:choose>
					<c:when test="${context.uid<=0}">
						<a href="javascript:void(0);" class="esc">登录</a>
					</c:when>
					<c:otherwise>
						<p><img src="${jzr:userLogo(loginUser.uid,loginUser.logoPic,80)}" height="20" width="20"/></p>
						<a href="/home" class="user <c:choose><c:when test='${loginUser.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><c:out value="${loginUser.nickname}" /></a><a href="/profile/index" class="set">设置</a>
						<a href="/logout"  class="esc">退出</a>
					</c:otherwise>
				</c:choose>
			</div><!--user_box end-->
			<div class="user_message" style="display:none;"><!--user_message begin-->
				<p>
					有<a href="#">12</a>人对你感兴趣<br /> 有<a href="#">17</a>个人想约你<br />
					有<a href="#">1</a>个关于你的系统通知<br /> 有<a href="#">2</a>人接受了你的邀请<br />
				</p>
			</div><!--user_message end-->
		</div><!--login end-->
	</div><!--user_area end-->
	<div class="search"><!--search begin-->
		<div class="s_l"></div>
		<div class="s_m">
			<!--s_m begin-->
			<input name="" type="text" value="输入拒宅项目,如:打台球" />
		</div><!--s_m end-->
		<div class="s_r">
			<a href="#"></a>
		</div>
		<div class="xl_menu" style="display:none;"><!--xl_menu begin-->
			<a href="#">打篮球</a> <a href="#">打台球</a> <a href="#">打电动</a> <a
				href="#">打篮球</a>
		</div><!--xl_menu end-->
	</div><!--search end-->
</div><!--top end-->