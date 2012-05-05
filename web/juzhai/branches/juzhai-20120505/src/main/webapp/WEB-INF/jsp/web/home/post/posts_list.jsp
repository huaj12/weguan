<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
	<c:when test="${empty postViewList}">
		<div class="my_jz"><!--my_jz begin-->
			<div class="none">你还没有发布过拒宅哦</div>
		</div>
	</c:when>
	<c:otherwise>
		<div class="my_jz"><!--my_jz begin-->
			<jsp:include page="post_view.jsp" />
		</div>
		<div class="clear"></div>
		<c:choose>
			<c:when test="${profile.uid == context.uid}">
				<c:set var="uri" value="/home/posts" scope="page"/>
			</c:when>
			<c:otherwise>
				<c:set var="uri" value="/home/${profile.uid}/posts" scope="page"/>
			</c:otherwise>
		</c:choose>
		<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
			<c:param name="pager" value="${pager}"/>
			<c:param name="url" value="${uri}" />
		</c:import>
	</c:otherwise>
</c:choose>