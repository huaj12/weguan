<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<c:set var="contactType" value="${param.contactType}" />
<c:choose>
	<c:when test="${contactType == 1}">QQ</c:when>
	<c:when test="${contactType == 2}">MSN</c:when>
	<c:when test="${contactType == 3}">手机</c:when>
	<c:when test="${contactType == 4}">GTALK</c:when>
</c:choose>