<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
	<c:when test="${empty visitorViewList}">
		<div class="my_fav"><!--my_fav begin-->
			<div class="none">还没有人来看过你</div>
		</div><!--my_fav end-->
	</c:when>
	<c:otherwise>
		<div class="my_fav"><!--my_fav begin-->
			<c:forEach var="visitorView" items="${visitorViewList}">
				<div class="pub_box mouseHover <c:choose><c:when test='${visitorView.profileCache.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--pub_box begin-->
					<div class="pub_box_t"></div>
					<div class="pub_box_m"><!--pub_box_m begin-->
						<p><a href="/home/${visitorView.profileCache.uid}"><img src="${jzr:userLogo(visitorView.profileCache.uid,visitorView.profileCache.logoPic,80)}" width="80" height="80" /></a></p>
						<h2><a href="/home/${visitorView.profileCache.uid}"><c:out value="${visitorView.profileCache.nickname}" /></a></h2><c:if test="${visitorView.online}"><strong class="online">当前在线</strong></c:if><div class="vtime"><c:set var="date" value="${visitorView.visitTime}" scope="request" /><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" />来访</div>
						<c:set var="cityName" value="${jzd:cityName(visitorView.profileCache.city)}" />
						<c:set var="townName" value="${jzd:townName(visitorView.profileCache.town)}" />
						<c:set var="age" value="${jzu:age(visitorView.profileCache.birthYear,visitorView.profileCache.birthSecret)}" />
						<c:set var="constellationName" value="${jzd:constellationName(visitorView.profileCache.constellationId)}" />
						<em><c:if test="${age > 0}">${age}岁&nbsp;</c:if><c:if test="${not empty cityName}">${cityName}<c:if test="${not empty townName}">${townName}</c:if>&nbsp;</c:if><c:if test="${not empty constellationName}">${constellationName}&nbsp;</c:if><c:if test="${not empty visitorView.profileCache.profession}">${visitorView.profileCache.profession}</c:if></em>
						<c:if test="${visitorView.latestPost != null}">
						<b><font><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${visitorView.latestPost.purposeType}"/></c:import>:</font><a href="/post/${visitorView.latestPost.id}"><c:out value="${jzu:truncate(visitorView.latestPost.content,42,'...')}" /></a></b>
						</c:if>
						<span><a class="send-message" href="javascript:void(0);" target-uid="${visitorView.profileCache.uid}" target-nickname="<c:out value='${visitorView.profileCache.nickname}'/>">私信</a></span>
						<div class="keep user-remove-interest remove-interest-${visitorView.profileCache.uid}" <c:if test="${!visitorView.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" class="done" uid="${visitorView.profileCache.uid}" title="点击取消收藏">已收藏</a></div>
						<div class="keep user-add-interest interest-${visitorView.profileCache.uid}" <c:if test="${visitorView.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" uid="${visitorView.profileCache.uid}" title="点击收藏">收藏ta</a></div>
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