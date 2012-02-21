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
					<div class="img"><a href="/post/${postView.post.id}"><img data-original="${jzr:postPic(postView.post.id, postView.post.ideaId, postView.post.pic, 200)}" src="${jzr:static('/images/web/1px.gif')}"/></a></div>
				</c:if>
				<!-- <div class="clear"></div> -->
				<span><c:set var="date" value="${postView.post.createTime}" scope="request" /><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" />更新</span>
				<span class="tag">${jzd:categoryName(postView.post.categoryId)}</span>
				<c:if test="${postView.post.dateTime != null}">
					<span class="time"><fmt:formatDate value="${postView.post.dateTime}" pattern="yyyy.MM.dd"/></span>
				</c:if>
				<c:if test="${not empty postView.post.place}">
					<span class="adress"><c:out value="${postView.post.place}" /></span>
				</c:if>
				<c:if test="${not empty postView.post.link}">
					<span class="link"><a href="${postView.post.link}" target="_blank">查看相关链接</a></span>
				</c:if>
			</div><!--infor end-->
			<c:choose>
				<c:when test="${context.uid == profile.uid}">
					<div class="con_btn">
						<c:choose><c:when test="${postView.post.responseCnt > 0}"><a href="/post/${postView.post.id}">有${postView.post.responseCnt}人响应</a></c:when><c:otherwise><em>无人响应</em></c:otherwise></c:choose>
						<a href="javascript:void(0);" class="edit" post-id="${postView.post.id}">编辑</a>
						<a href="javascript:void(0);" class="delete" post-id="${postView.post.id}">删除</a>
					</div>
				</c:when>
				<c:otherwise>
					<div class="btn"><!--btn begin-->
						<c:choose>
							<c:when test="${postView.hasResponse}">
								<div class="like done"><a href="javascript:void(0);" class="xy">已响应</a><a href="/post/${postView.post.id}" class="xy_num">(${postView.post.responseCnt})</a></div>
							</c:when>
							<c:otherwise>
								<div class="like post-response" id="response${postView.post.id}"><a href="javascript:void(0);" class="xy" post-id="${postView.post.id}">响应</a><a href="/post/${postView.post.id}" class="xy_num">(<font>${postView.post.responseCnt}</font>)</a></div>
							</c:otherwise>
						</c:choose>
						<!-- <div class="message_s1"><a href="javascript:void(0);">私信</a></div> -->
						<div class="zfa"><a href="javascript:void(0);" post-id="${postView.post.id}">转发</a></div>
					</div><!--btn end-->
				</c:otherwise>
			</c:choose>
		</div><!--pub_box_m end-->
		<div class="clear"></div>
		<div class="pub_box_b"></div>
	</div><!--pub_box end-->
</c:forEach>