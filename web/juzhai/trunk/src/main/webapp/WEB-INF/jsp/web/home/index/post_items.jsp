<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:forEach var="postView" items="${postViewList}">
	<div class="jz_item mouseHover <c:choose><c:when test="${postView.profileCache.gender == 1}">boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--jz_item begin-->
		<div class="face_infor"><!--face_infor begin-->
			<p><a href="/home/${postView.profileCache.uid}"><img src="${jzr:userLogo(postView.profileCache.uid,postView.profileCache.logoPic,120)}" width="120" height="120" /></a></p>
			<a href="/home/${postView.profileCache.uid}"><c:out value="${postView.profileCache.nickname}" /></a>
			<c:set var="age" value="${jzu:age(postView.profileCache.birthYear, postView.profileCache.birthSecret)}" />
			<c:set var="constellationName" value="${jzd:constellationName(postView.profileCache.constellationId)}" />
			<span><c:if test="${age > 0}">${age}岁&nbsp;</c:if><c:if test="${postView.profileCache.city != null && postView.profileCache.city > 0}">${jzd:cityName(postView.profileCache.city)}<c:if test="${postView.profileCache.town != null && postView.profileCache.town > 0}">${jzd:townName(postView.profileCache.town)}</c:if>&nbsp;</c:if><c:if test="${not empty constellationName}">${constellationName}&nbsp;</c:if><c:if test="${not empty postView.profileCache.profession}">${postView.profileCache.profession}</c:if></span>
		</div><!--face_infor end-->
		<div class="wtg"><!--wtg begin-->
			<div class="w_t"></div>
			<div class="w_m"><!--w_m begin-->
				<div class="arrow"></div>
				<p><font><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${postView.post.purposeType}"/></c:import>:</font><a href="/post/${postView.post.id}"><c:out value="${postView.post.content}" /></a></p>
				<div class="infor"><!--infor begin-->
					<c:if test="${not empty postView.post.pic}">
						<div class="img"><a href="/post/${postView.post.id}"><img data-original="${jzr:postPic(postView.post.id, postView.post.ideaId, postView.post.pic, 200)}" src="${jzr:static('/images/web/1px.gif')}"/></a></div>
					</c:if>
					<%-- <span><c:set var="date" value="${postView.post.createTime}" scope="request" /><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" />更新</span> --%>
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
			</div><!--w_m end-->
			<div class="clear"></div>
			<div class="w_b"></div>
			<div class="btn"><!--btn begin-->
				<div class="keep user-remove-interest remove-interest-${postView.profileCache.uid}" <c:if test="${!postView.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" class="done" uid="${postView.profileCache.uid}" title="点击取消收藏">已收藏</a></div>
				<div class="keep user-add-interest interest-${postView.profileCache.uid}" <c:if test="${postView.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" uid="${postView.profileCache.uid}" title="点击收藏">收藏ta</a></div>
				<div class="mail"><a href="javascript:void(0);" title="给ta发私信" target-uid="${postView.profileCache.uid}" target-nickname="${postView.profileCache.nickname}">私信</a></div>
				<div class="message_s2"><a href="javascript:void(0);" post-id="${postView.post.id}">留言<c:if test="${postView.post.commentCnt > 0}">(${postView.post.commentCnt})</c:if></a></div>
				<c:choose>
					<c:when test="${postView.hasResponse}">
						<div class="like done"><a href="javascript:void(0);" class="xy">已响应</a><div class="xy_num"><p class="l"></p><a href="/post/${postView.post.id}/respuser">${postView.post.responseCnt}</a><p class="r"></p></div></div>
					</c:when>
					<c:otherwise>
						<div class="like post-response" id="response${postView.post.id}"><a href="javascript:void(0);" class="xy" post-id="${postView.post.id}">响应</a><div class="xy_num"><p class="l"></p><a href="/post/${postView.post.id}/respuser"><font>${postView.post.responseCnt}</font></a><p class="r"></p></div></div>
					</c:otherwise>
				</c:choose>
				<div class="zfa"><a href="javascript:void(0);" post-id="${postView.post.id}">转发</a></div>
			</div><!--btn end-->
			<div class="clear"></div>
			<div class="message_s2_box" id="comment-box-${postView.post.id}" loaded="false" style="display: none;"><!--message_box begin-->
				<div class="box_top"></div>
				<div class="box_main"><!--box_main begin-->
					<div class="arrow"></div>
					<c:set var="postId" value="${postView.post.id}" scope="request"/>
					<jsp:include page="/WEB-INF/jsp/web/post/comment_send_box.jsp" />
					<div class="comment-list">
						<div class="repy_list_s2 bd_line">
							<div class="list_loading"><em><img src="${jzr:static('/images/web2/list_loading.gif')}"  width="16" height="16"/></em><p>留言加载中...</p></div>
						</div>
					</div>
					<div class="clear"></div>
				</div><!--box_main end-->
				<div class="box_bottom"></div>
			</div><!--message_box end-->
		</div><!--wtg end-->
	</div><!--jz_item end-->
</c:forEach>