<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="purposeType" value="${param.purposeType}" />
<c:choose>
	<c:when test="${purposeType == 0}">我想去</c:when>
	<c:when test="${purposeType == 1}">我想找伴儿去</c:when>
	<c:when test="${purposeType == 2}">我想找一个男生</c:when>
	<c:when test="${purposeType == 3}">我想找一个女生</c:when>
</c:choose>