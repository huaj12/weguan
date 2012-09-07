<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
	<c:when test="${postCommentView!=null}">
		<c:set value="${loginUser.uid != postCommentView.postComment.createUid}" var="isNotMe"></c:set>
		<li <c:if test="${postCommentViewList==null}">style="display:none;"</c:if> class="mouseHover <c:choose><c:when test="${postCommentView.createUser.gender == 1}">boy</c:when><c:otherwise>girl</c:otherwise></c:choose>">
			<p><a href="/home/${postCommentView.createUser.uid}"><img src="${jzr:userLogo(postCommentView.createUser.uid,postCommentView.createUser.logoPic,80)}" width="50" height="50" /></a></p>
			<span><a href="/home/${postCommentView.createUser.uid}"><c:out value="${postCommentView.createUser.nickname}" />:</a><c:if test="${null != postCommentView.parentUser}"><i>回复<c:out value="${postCommentView.parentUser.nickname}" />:</i></c:if>${jzu:convertFace(postCommentView.postComment.content)}</span>
			<em><b><c:set var="date" value="${postCommentView.postComment.createTime}" scope="request" /><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" /></b>
			<div class="jubao"><c:if test="${isNotMe}"><a href="javascript:void(0);" id="report-conment" uid="<c:out value='${postCommentView.postComment.createUid}' />" content="<c:out value='${postCommentView.postComment.content}' />" post-id="${postCommentView.postComment.postId}" >举报</a></c:if></div>
			<strong><c:if test="${isNotMe}"><a href="javascript:void(0);" nickname="<c:out value='${postCommentView.createUser.nickname}' />" content="<c:out value="${jzu:truncate(postCommentView.postComment.content,40,'...')}"/>" post-comment-id="${postCommentView.postComment.id}" class="reply-link">回复</a></c:if><c:if test="${loginUser.uid == postCommentView.postComment.createUid || loginUser.uid == postCommentView.postComment.postCreateUid || context.admin}"><a href="javascript:void(0);" class="delete delete-link" post-comment-id="${postCommentView.postComment.id}">删除</a></c:if></strong></em>
		</li>
	</c:when>
	<c:otherwise>
		{'success':${result.success},'errorCode':'${result.errorCode}','errorInfo':'${result.errorInfo}','result':''}
	</c:otherwise>
</c:choose>