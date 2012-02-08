<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="seconds" value="${jzu:beforeSeconds(date)}" />
<c:set var="minutes" value="${jzu:beforeMinutes(date)}" />
<c:set var="days" value="${jzu:beforeDays(date)}" />
<c:choose><c:when test="${seconds >= 0 && seconds < 60}">${seconds}秒前</c:when><c:when test="${minutes >= 0 && minutes < 60}">${minutes}分钟前</c:when><c:when test="${minutes > 0 && minutes / 60 < 24}"><fmt:parseNumber value="${minutes / 60}" integerOnly="true"/>小时前</c:when><c:when test="${days == 1}">昨天</c:when><c:when test="${days == 2}">前天</c:when><c:otherwise><fmt:formatDate value="${date}" pattern="yyyy.MM.dd"/></c:otherwise></c:choose>