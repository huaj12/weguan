<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:forEach var="postView" items="${postViewList}">		
	<div class="pub_box mouseHover"><!--pub_box begin-->
		<div class="pub_box_t"></div>
		<div class="pub_box_m"><!--pub_box_m begin-->
			<p><font><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${postView.post.purposeType}"/></c:import>：</font><a href="/post/${postView.post.id}"><c:out value="${postView.post.content}" /></a></p>
			<div class="infor"><!--infor begin-->
				<c:if test="${not empty postView.post.pic}">
					<div class="img"><a href="/post/${postView.post.id}"><img data-original="${jzr:postPic(postView.post.id, postView.post.ideaId, postView.post.pic, 200)}" src="${jzr:static('/images/web2/1px.gif')}"/></a></div>
				</c:if>
				<!-- <div class="clear"></div> -->
				<span><c:set var="date" value="${postView.post.createTime}" scope="request" /><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" />更新</span>
				<span class="tag">${jzd:categoryName(postView.post.categoryId)}</span>
				<c:if test="${postView.post.dateTime != null}">
					<span class="time"><fmt:formatDate value="${postView.post.dateTime}" pattern="yyyy.MM.dd"/></span>
				</c:if>
				<c:if test="${not empty postView.post.place}">
					<span class="adress"><c:out value="${jzu:truncate(postView.post.place,40,'...')}"></c:out></span>
				</c:if>
				<c:if test="${not empty postView.post.link}">
					<span class="link"><a href="${postView.post.link}" <c:if test="${empty isQplus || !isQplus}">target="_blank"</c:if>>查看相关链接</a></span>
				</c:if>
			</div><!--infor end-->
			<c:choose>
				<c:when test="${context.uid == profile.uid}">
					<div class="con_btn">
						<c:choose>
							<c:when test="${postView.post.commentCnt > 0}">
								<a href="/post/${postView.post.id}/comment">有${postView.post.commentCnt}条留言</a>
							</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${postView.post.responseCnt > 0}">
								<a href="/post/${postView.post.id}/respuser">${postView.post.responseCnt}人有兴趣</a>
							</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
						<a href="javascript:void(0);" class="edit" post-id="${postView.post.id}">编辑</a>
						<a href="javascript:void(0);" class="delete" post-id="${postView.post.id}">删除</a>
					</div>
				</c:when>
				<c:otherwise>
					<div class="btn"><!--btn begin-->
						<div class="message_s2"><a href="/post/${postView.post.id}/comment" post-id="${postView.post.id}">留言<c:if test="${postView.post.commentCnt > 0}">(${postView.post.commentCnt})</c:if></a></div>
						<c:choose>
							<c:when test="${postView.hasResponse}">
								<div class="like done"><a href="javascript:void(0);" class="xy">有兴趣</a><div class="xy_num"><p class="l"></p><a href="javascript:void(0);">${postView.post.responseCnt}</a><p class="r"></p></div></div>
							</c:when>
							<c:otherwise>
								<div class="like post-response" id="response${postView.post.id}" post-id="${postView.post.id}" resp-count="${postView.post.responseCnt}" nickname="<c:out value='${profile.nickname}' />" post-content="<c:out value="${jzu:truncate(postView.post.content,50,'...')}" />"><a href="javascript:void(0);" class="xy">有兴趣</a><div class="xy_num"><p class="l"></p><a href="javascript:void(0);">${postView.post.responseCnt}</a><p class="r"></p></div></div>
							</c:otherwise>
						</c:choose>
						<div class="zfa"><a href="javascript:void(0);" post-id="${postView.post.id}">我也想去</a></div>
					</div><!--btn end-->
				</c:otherwise>
			</c:choose>
		</div><!--pub_box_m end-->
		<div class="clear"></div>
		<div class="pub_box_b"></div>
	</div><!--pub_box end-->
</c:forEach>