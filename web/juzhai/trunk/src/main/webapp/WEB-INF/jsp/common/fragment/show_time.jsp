<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="minutes" value="${jz:beforeMinutes(date)}" />
<c:set var="days" value="${jz:beforeDays(date)}" />
<c:choose>
	<c:when test="${minutes >= 0 && minutes < 60}">更新于${minutes}分钟前</c:when>
	<c:when test="${minutes > 0 && minutes / 60 < 24}">更新于<fmt:parseNumber value="${minutes / 60}" integerOnly="true"/>小时前</c:when>
	<c:when test="${days == 1}">更新于昨天</c:when>
	<c:when test="${days == 2}">更新于前天</c:when>
	<c:otherwise>更新于<fmt:formatDate value="${date}" pattern="yyyy.MM.dd"/></c:otherwise>
</c:choose>