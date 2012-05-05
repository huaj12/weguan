<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<p user-total="${userCount}" post-total="${postCount}" interact-total="${interactCount}">
	已有&nbsp;&nbsp;<c:forEach var="userNum" items="${userNumList}"><b>${userNum}</b></c:forEach>&nbsp;&nbsp;人加入&nbsp;&nbsp;
	,&nbsp;&nbsp;发布了&nbsp;&nbsp;<c:forEach var="postNum" items="${postNumList}"><b>${postNum}</b></c:forEach>&nbsp;&nbsp;条拒宅&nbsp;&nbsp;
	,&nbsp;&nbsp;收到了&nbsp;&nbsp;<c:forEach var="interactNum" items="${interactNumList}"><b>${interactNum}</b></c:forEach>&nbsp;&nbsp;个回应
</p>
