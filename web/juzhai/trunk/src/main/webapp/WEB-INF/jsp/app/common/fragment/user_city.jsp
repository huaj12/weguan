<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<c:set var="cityId" value="${param.cityId}" />
ta在<c:choose><c:when test="${cityId > 0}">${jz:cityName(cityId)}</c:when><c:otherwise>地球</c:otherwise></c:choose>