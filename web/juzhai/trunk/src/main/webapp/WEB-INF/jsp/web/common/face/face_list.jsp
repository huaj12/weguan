<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<ul>
	<c:forEach var="face" items="${faceList}">
		<li><img src="${jzr:static('/images/face/')}${face.pic}" title="${face.name}" alt="${face.name}" width="16" height="16" /></li>
	</c:forEach>
</ul>