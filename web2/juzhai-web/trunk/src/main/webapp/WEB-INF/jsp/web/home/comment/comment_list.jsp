<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="jz_list"><!--jz_list begin-->
	<div class="title"><!--title begin-->
		<h2>我的留言</h2>
		<div class="ly_menu"><!--ly_menu begin-->
			<span <c:if test="${commentType == 'inbox'}">class="act"</c:if>><p></p><a href="/home/comment/inbox/1">我收到的</a><p></p></span>
			<span <c:if test="${commentType == 'outbox'}">class="act"</c:if>><p></p><a href="/home/comment/outbox/1">我发出的</a><p></p></span>
		</div><!--ly_menu end-->
		<div class="back_home"><a href="/home">返回首页</a></div>
	</div><!--title end-->
	<div class="liuyan_box"><!--liuyan_box begin-->
		<c:choose>
			<c:when test="${empty postCommentViewList}">
				<div class="none"><c:choose><c:when test="${commentType == 'outbox'}">你还没有留过言</c:when><c:when test="${commentType == 'inbox'}">还没有人给你留言</c:when></c:choose></div>
			</c:when>
			<c:otherwise>
				<c:forEach var="postCommentView" items="${postCommentViewList}">
					<c:set var="isMe" value="${loginUser.uid == postCommentView.postComment.createUid}" />
					<c:choose>
						<c:when test="${!isMe}">
							<c:set var="targetUser" value="${postCommentView.createUser}" />
						</c:when>
						<c:when test="${null != postCommentView.parentUser}">
							<c:set var="targetUser" value="${postCommentView.parentUser}" />
						</c:when>
						<c:otherwise>
							<c:set var="targetUser" value="${postCommentView.postCreateUser}" />
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${null != postCommentView.parentUser}">
							<c:set var="parentContent" value="${postCommentView.postComment.parentContent}" />
							<c:set var="action" value="回复" />
							<c:set var="target" value="留言" />
						</c:when>
						<c:otherwise>
							<c:set var="parentContent" value="${postCommentView.postComment.postContent}" />
							<c:set var="action" value="留言" />
							<c:set var="target" value="拒宅" />
						</c:otherwise>
					</c:choose>
					<div class="item mouseHover <c:choose><c:when test="${targetUser.gender == 1}">boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--item begin-->
						<div class="face"><!--face begin-->
							<a href="/home/${targetUser.uid}"><img src="${jzr:userLogo(targetUser.uid,targetUser.logoPic,80)}" width="80" /></a>
						</div><!--face end-->
						<div class="ly_conetent"><!--ly_conetent begin-->
							<div class="ly_t"></div>
							<div class="ly_m"><!--ly_m begin-->
								<div></div>
								<b><c:if test="${!isMe}"><a href="/home/${targetUser.uid}"><c:out value="${targetUser.nickname}" /></a>：</c:if>${jzu:convertFace(postCommentView.postComment.content)}</b>
								<strong>${action}到<c:choose><c:when test="${!isMe}">你</c:when><c:otherwise><a href="/home/${targetUser.uid}" class="u"><c:out value="${targetUser.nickname}" /></a></c:otherwise></c:choose>的${target}<a href="/post/${postCommentView.postComment.postId}">“${jzu:truncate(parentContent,40,'...')}”</a></strong>
								<em>
									<div class="time">发布于&nbsp;<c:set var="date" value="${postCommentView.postComment.createTime}" scope="request" /><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" /></div>
									<c:if test="${!isMe}">
										<a href="javascript:void(0);" id="report-conment" uid="<c:out value='${postCommentView.createUser.uid}' />" content="<c:out value='${postCommentView.postComment.content}' />" post-id="${postCommentView.postComment.postId}" class="jb">举报</a>
										<a href="javascript:void(0);" nickname="<c:out value='${postCommentView.createUser.nickname}' />" content="<c:out value="${jzu:truncate(postCommentView.postComment.content,40,'...')}"/>" post-comment-id="${postCommentView.postComment.id}" class="reply-link">回复</a>
									</c:if>
									<c:if test="${isMe || loginUser.uid == postCommentView.postComment.postCreateUid}">
										<a href="javascript:void(0);" class="delete-link" post-comment-id="${postCommentView.postComment.id}">删除</a>
									</c:if>
								</em>
								<div class="clear"></div>
								<c:if test="${!isMe}">
									<c:set var="postId" value="${postCommentView.postComment.postId}" scope="request"/>
									<c:set var="parentId" value="${postCommentView.postComment.id}" scope="request" />
 									<jsp:include page="/WEB-INF/jsp/web/post/comment_send_box.jsp" />
								</c:if>
							</div><!--ly_m end-->
							<div class="ly_b"></div>
						</div><!--ly_conetent end-->
					</div><!--item end-->
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div><!--liuyan_box end-->
</div><!--jz_list end-->
<div class="clear"></div>
<c:if test="${not empty postCommentViewList}">
	<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
		<c:param name="pager" value="${pager}"/>
		<c:param name="url" value="/home/comment/${commentType}" />
	</c:import>
</c:if>