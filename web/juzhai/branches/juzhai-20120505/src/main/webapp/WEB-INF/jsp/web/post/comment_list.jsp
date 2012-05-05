<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="repy_list_s2 <c:if test='${not empty postCommentViewList}'>bd_line</c:if>"><!--repy_list_s2 begin-->
	<ul>
		<c:forEach var="postCommentView" items="${postCommentViewList}">
			<c:set var="postCommentView" value="${postCommentView}" scope="request" />
			<jsp:include page="comment_fragment.jsp" />
		</c:forEach>
	</ul>
	<c:if test="${not empty postCommentViewList && pager == null}">
		<div class="more"><a href="/post/${postId}">查看更多</a></div>
	</c:if>
</div><!--repy_list_s2 end-->
<c:if test="${pager != null}">
	<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
		<c:param name="pager" value="${pager}"/>
		<c:param name="url" value="/post/${post.id}/comment" />
	</c:import>
</c:if>