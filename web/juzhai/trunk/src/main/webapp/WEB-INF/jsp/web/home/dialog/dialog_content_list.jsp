<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="repy_list"><!--repy_list begin-->
	<div class="repy_title">我和<a href="/home/${targetProfile.uid}"><c:out value="${targetProfile.nickname}" /></a>之间有<em>${pager.totalResults}</em>条私信</div>
	<div class="repy_list_body"><!--repy_list_body begin-->
		<c:forEach var="dialogContentView" items="${dialogContentViewList}">
			<c:set var="dialogContentView" value="${dialogContentView}" scope="request" />
			<jsp:include page="dialog_content_fragment.jsp" />
		</c:forEach>
	</div><!--repy_list_body end-->
	<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
		<c:param name="pager" value="${pager}"/>
		<c:param name="url" value="/home/dialogContent/${targetProfile.uid}" />
	</c:import>
</div><!--repy_list end-->