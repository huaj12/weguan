<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<c:set var="reportType" value="${param.reportType}" />
<c:choose>
	<c:when test="${reportType == 0}">其他</c:when>
	<c:when test="${reportType == 1}">色情</c:when>
	<c:when test="${reportType == 2}">人身攻击</c:when>
	<c:when test="${reportType == 3}">垃圾广告</c:when>
</c:choose>