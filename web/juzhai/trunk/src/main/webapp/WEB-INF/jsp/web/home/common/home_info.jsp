<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="head_area"><!--head_area begin-->
	<div class="face"><a href="#"><img src="${profile.logoPic}" /></a></div>
	<div class="user_infor"><!--user_infor begin-->
		<h2>${profile.nickname}</h2>
		<%-- <div class="online">当前在线</div>
		<div class="offline">当前不在线</div> --%>
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
	</div><!--user_infor end-->
	<div class="user_btn"><!--user_btn begin--><a href="#">个人设置</a></div><!--user_btn end-->
</div><!--head_area end-->