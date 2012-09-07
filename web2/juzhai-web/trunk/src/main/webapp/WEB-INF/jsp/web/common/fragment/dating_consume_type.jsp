<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<c:set var="consumeType" value="${param.consumeType}" />
<c:choose>
	<c:when test="${consumeType == 1}">我请客</c:when>
	<c:when test="${consumeType == 2}">AA制</c:when>
	<c:when test="${consumeType == 3}">求请客</c:when>
	<c:when test="${consumeType == 4}">不用花钱</c:when>
</c:choose>