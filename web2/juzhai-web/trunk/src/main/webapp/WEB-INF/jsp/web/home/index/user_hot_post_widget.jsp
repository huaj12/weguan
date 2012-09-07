<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	<c:if test="${not empty userHotPostView&&fn:length(userHotPostView)>3}">
		<div class="content_box w285"><!--content begin-->
			<div class="t"></div>
				<div class="m">
					<div class="right_title"><h2>小宅热议中...</h2></div>
					<div class="reying"><!--reying begin-->
						<ul>
							<c:forEach var="postView" items="${userHotPostView}">
								<li><p><a href="/post/${postView.post.id}"><c:out value="${jzu:truncate(postView.post.content,60,'...')}" /></a></p><em>来自:<a href="/home/${postView.profileCache.uid}" class="boy"><c:out value="${postView.profileCache.nickname}" /></a></em><span><a href="/post/${postView.post.id}">${postView.post.commentCnt}条留言</a></span></li>
							</c:forEach>
						</ul>
					</div><!--reying end-->
				</div>
			<div class="t"></div>
		</div><!--content end-->
	</c:if>
