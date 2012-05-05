<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<div class="project_con"><!--project_con begin-->
	<c:choose>
		<c:when test="${empty actDetail.detail}">
			<div class="none">目前还没有关于<c:out value="${act.name}" />的介绍！</div>
		</c:when>
		<c:otherwise>${actDetail.detail}</c:otherwise>
	</c:choose>
</div><!--project_con end-->
