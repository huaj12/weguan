<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="minutes" value="${jzu:beforeMinutes(date)}" />
<c:set var="days" value="${jzu:beforeDays(date)}" />
	<c:choose>
		<c:when test="${minutes >= 0 && minutes < 30}"><em class="on">当前在线</em></c:when>
		<c:when test="${minutes > 0 && minutes / 60 < 24}"><em class="just">今日来访</em></c:when>
		<c:when test="${days>=1 && days < 10}"><em class="befor">近期来访</em></c:when>
		<c:otherwise></c:otherwise>
	</c:choose>