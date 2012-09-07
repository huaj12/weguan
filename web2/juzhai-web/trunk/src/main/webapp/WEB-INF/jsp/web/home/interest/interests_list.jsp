<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
	<c:when test="${empty interestUserViewList}">
		<div class="my_fav"><!--my_fav begin-->
			<div class="none">
				<c:choose>
					<c:when test="${tabType=='interests'}">没有人被你关注过</c:when>
					<c:otherwise>还没人关注过你</c:otherwise>
				</c:choose>
			</div>
		</div><!--my_fav end-->
	</c:when>
	<c:otherwise>
		<div class="my_fav"><!--my_fav begin-->
			<c:forEach var="interestUserView" items="${interestUserViewList}">
				<div class="pub_box mouseHover <c:choose><c:when test='${interestUserView.profileCache.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--pub_box begin-->
					<div class="pub_box_t"></div>
					<div class="pub_box_m"><!--pub_box_m begin-->
						<p><a href="/home/${interestUserView.profileCache.uid}"><img src="${jzr:userLogo(interestUserView.profileCache.uid,interestUserView.profileCache.logoPic,80)}" width="80" height="80" /></a></p>
						<h2><a href="/home/${interestUserView.profileCache.uid}"><c:out value="${interestUserView.profileCache.nickname}" /></a></h2><c:if test="${interestUserView.online}"><strong class="online">当前在线</strong></c:if>
						<c:set var="cityName" value="${jzd:cityName(interestUserView.profileCache.city)}" />
						<c:set var="townName" value="${jzd:townName(interestUserView.profileCache.town)}" />
						<c:set var="age" value="${jzu:age(interestUserView.profileCache.birthYear,interestUserView.profileCache.birthSecret)}" />
						<c:set var="constellationName" value="${jzd:constellationName(interestUserView.profileCache.constellationId)}" />
						<em><c:if test="${age > 0}">${age}岁&nbsp;</c:if><c:if test="${not empty cityName}">${cityName}<c:if test="${not empty townName}">${townName}</c:if>&nbsp;</c:if><c:if test="${not empty constellationName}">${constellationName}&nbsp;</c:if><c:if test="${not empty interestUserView.profileCache.profession}">${interestUserView.profileCache.profession}</c:if></em>
						<c:if test="${interestUserView.latestPost != null}">
						<b><font><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${interestUserView.latestPost.purposeType}"/></c:import>:</font><a href="/post/${interestUserView.latestPost.id}"><c:out value="${jzu:truncate(interestUserView.latestPost.content,42,'...')}" /></a></b>
						</c:if>
						<span><a class="send-message" href="javascript:void(0);" target-uid="${interestUserView.profileCache.uid}" target-nickname="<c:out value='${interestUserView.profileCache.nickname}'/>">私信</a></span>
						<div class="keep user-remove-interest remove-interest-${interestUserView.profileCache.uid}" <c:if test="${!interestUserView.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" class="done" uid="${interestUserView.profileCache.uid}" title="点击取消关注">已关注</a></div>
						<div class="keep user-add-interest interest-${interestUserView.profileCache.uid}" <c:if test="${interestUserView.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" uid="${interestUserView.profileCache.uid}" title="点击关注">关注ta</a></div>
					</div><!--pub_box_m end-->
					<div class="clear"></div>
					<div class="pub_box_b"></div>
				</div><!--pub_box end-->
			</c:forEach>
		</div><!--my_fav end-->
		<div class="clear"></div>
		<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
			<c:param name="pager" value="${pager}"/>
			<c:param name="url" value="/home/${tabType}" />
		</c:import>
	</c:otherwise>
</c:choose>