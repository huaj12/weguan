<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData"%>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
	<c:when test="${postView != null}">
		<div class="card <c:choose><c:when test="${postView.profileCache.gender == 1}">boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--card begin-->
			<div class="card_top"></div>
			<div class="card_mid"><!--card_mid begin-->
				<div class="photo"><a href="/home/${postView.profileCache.uid}" <c:if test="${empty isQplus || !isQplus}">target="_blank"</c:if>><img src="${jzr:userLogo(postView.profileCache.uid,postView.profileCache.logoPic,180)}" /></a></div>
				<div class="card_infor"><!--card_infor begin-->
					<p><font><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${postView.post.purposeType}"/></c:import>:</font><a href="/post/${postView.post.id}" <c:if test="${empty isQplus || !isQplus}">target="_blank"</c:if>><c:out value="${postView.post.content}" /></a></p>
					<div class="infor"><!--infor begin-->
						<%-- <c:if test="${not empty postView.post.pic}">
							<div class="img"><a href="/post/${postView.post.id}"><img data-original="${jzr:postPic(postView.post.id, postView.post.ideaId, postView.post.pic, 200)}" src="${jzr:static('/images/web2/1px.gif')}"/></a></div>
						</c:if> --%>
						<span class="tag">${jzd:categoryName(postView.post.categoryId)}</span>
						<c:if test="${postView.post.dateTime != null}">
							<span class="time"><fmt:formatDate value="${postView.post.dateTime}" pattern="yyyy.MM.dd"/></span>
						</c:if>
						<c:if test="${not empty postView.post.place}">
							<span class="adress"><c:out value="${postView.post.place}" /></span>
						</c:if>
						<c:if test="${not empty postView.post.link}">
							<span class="link"><a href="${postView.post.link}" <c:if test="${empty isQplus || !isQplus}">target="_blank"</c:if>>查看相关链接</a></span>
						</c:if>
					</div><!--infor end-->
					<div class="ta_infor">
						<a href="/home/${postView.profileCache.uid}" <c:if test="${empty isQplus || !isQplus}">target="_blank"</c:if>><c:out value="${postView.profileCache.nickname}" /></a>
						<div class="keep user-remove-interest remove-interest-${postView.profileCache.uid}" <c:if test="${!postView.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" class="done" uid="${postView.profileCache.uid}" title="点击取消收藏">已收藏</a></div>
						<div class="keep user-add-interest interest-${postView.profileCache.uid}" <c:if test="${postView.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" uid="${postView.profileCache.uid}" title="点击收藏">收藏ta</a></div>
						<c:set var="age" value="${jzu:age(postView.profileCache.birthYear, postView.profileCache.birthSecret)}" />
						<c:set var="constellationName" value="${jzd:constellationName(postView.profileCache.constellationId)}" />
						<span><c:if test="${age > 0}">${age}岁&nbsp;</c:if><c:if test="${postView.profileCache.city != null && postView.profileCache.city > 0}">${jzd:cityName(postView.profileCache.city)}<c:if test="${postView.profileCache.town != null && postView.profileCache.town > 0}">${jzd:townName(postView.profileCache.town)}</c:if>&nbsp;</c:if><c:if test="${not empty constellationName}">${constellationName}&nbsp;</c:if><c:if test="${not empty postView.profileCache.profession}">${postView.profileCache.profession}</c:if></span>
					</div>
				</div><!--card_infor end-->
			</div><!--card_mid end-->
			<div class="card_bot"></div>
		</div><!--card end-->
		<div class="btn"><a href="javascript:void(0);" class="xy" post-id="${postView.post.id}" resp-count="${postView.post.responseCnt}" rescue-uid="${postView.profileCache.uid}" nickname="<c:out value='${postView.profileCache.nickname}' />" post-content="<c:out value="${jzu:truncate(postView.post.content,50,'...')}" />">响应ta</a> <a href="javascript:void(0);" class="next" rescue-uid="${postView.profileCache.uid}">换一个</a></div>
	</c:when>
	<c:otherwise>
		<div class="card girl"><!--card begin-->
			<div class="card_top"></div>
			<div class="card_mid"><!--card_mid begin-->
				<div class="photo"><img src="${jzr:static('/images/web2/jjxz_img_none.jpg')}" /></div>
				<div class="card_infor"><!--card_infor begin-->
					<div class="none">目前暂时没有需要你解救的小宅了</div>
					<div class="gtzl"><a href="/home">改天再来</a></div>
				</div><!--card_infor end-->
			</div><!--card_mid end-->
			<div class="card_bot"></div>
		</div><!--card end-->
	</c:otherwise>
</c:choose>