<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="tpName" value="${jzd:tpName(context.tpId)}" />
<c:if test="${!empty tpName}">
	<c:set value="/js/core/connect/${tpName}/${tpName}.js"
		var="s"></c:set>
	<script type="text/javascript" src="${jz:static(s)}"></script>
</c:if>

