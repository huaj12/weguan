<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<c:set var="reportContentType" value="${param.reportContentType}" />
<c:choose>
	<c:when test="${reportContentType == 1}">私信</c:when>
	<c:when test="${reportContentType == 2}">留言</c:when>
	<c:when test="${reportContentType == 3}">用户</c:when>
</c:choose>