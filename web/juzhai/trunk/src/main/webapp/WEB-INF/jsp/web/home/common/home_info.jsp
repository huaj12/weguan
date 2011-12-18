<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="head_area"><!--head_area begin-->
	<div class="face"><a href="/home/${profile.uid}"><img src="${profile.logoPic}" /></a></div>
	<div class="user_infor"><!--user_infor begin-->
		<h2><a href="/home/${profile.uid}"><c:out value="${profile.nickname}" /></a></h2>
		<c:if test="${online != null}">
			<c:choose>
				<c:when test="${online}"><div class="online">当前在线</div></c:when>
				<c:otherwise><div class="offline">当前不在线</div></c:otherwise>
			</c:choose>
		</c:if>
		<div class="clear"></div>
		<c:set var="cityName" value="${jzd:cityName(profile.city)}" />
		<c:set var="townName" value="${jzd:townName(profile.town)}" />
		<c:if test="${not empty cityName || not empty townName}">
			<div class="city"><c:if test="${not empty cityName}">${cityName}</c:if><c:if test="${not empty townName}">${townName}</c:if></div>
		</c:if>
		<c:set var="age" value="${jzu:age(profile.birthYear)}" />
		<c:set var="constellationName" value="${jzd:constellationName(profile.constellationId)}" />
		<c:if test="${age>=0 || not empty constellationName || not empty profile.profession}">
			<div class="ziye"><c:if test="${age>=0}"><span>${age}岁</span></c:if><c:if test="${not empty constellationName}"><span>${constellationName}</span></c:if><c:if test="${not empty profile.profession}"><span>${profile.profession}</span></c:if></div>
		</c:if>
		<c:if test="${not empty profile.feature}">
			<div class="tedian"><c:out value="${profile.feature}" /></div>
		</c:if>
		<div class="dated" datingid="${datingView.dating.id}" uid="${profile.uid}" <c:if test="${profile.uid==context.uid || datingView==null}">style="display:none;"</c:if>><!--dated begin-->
			<div class="datefor"><span></span><p>已约ta&nbsp;<font><c:out value="${datingView.act.name}" /></font></p><span></span></div>
			<a href="javascript:void(0);" class="modifyDating">修改</a><a href="javascript:void(0);" class="removeDating">取消</a>
		</div><!--dated end-->
	</div><!--user_infor end-->
	<c:choose>
		<c:when test="${profile.uid==context.uid}">
			<div class="user_btn"><!--user_btn begin--><a href="#">个人设置</a></div><!--user_btn end-->
		</c:when>
		<c:otherwise>
			<div class="ta_user_btn"><!--user_btn begin-->
				<div class="cancel_like" <c:if test="${!isInterest}">style="display:none;"</c:if>><p>已感兴趣</p><a href="javascript:void(0);" class="delete" uid="${profile.uid}"></a></div>
				<a href="javascript:void(0);" class="like" uid="${profile.uid}" <c:if test="${isInterest}">style="display:none;"</c:if>>感兴趣</a>	
				<a href="javascript:void(0);" class="date" <c:if test="${datingView!=null}">style="display:none;"</c:if> uid="${profile.uid}">约ta</a>
			</div><!--user_btn end-->
		</c:otherwise>
	</c:choose>
</div><!--head_area end-->