<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${not empty ideaUserViewList}">
	<div class="title2"><h2>共有${pager.totalResults}人想去</h2></div>
	<div class="response_list"><!--response_list begin-->
		<c:forEach var="ideaUserView" items="${ideaUserViewList}">
			<div class="pub_box mouseHover <c:choose><c:when test="${ideaUserView.profileCache.gender == 1}">boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--pub_box begin-->
				<div class="pub_box_t"></div>
				<div class="pub_box_m"><!--pub_box_m begin-->
					<p><a href="/home/${ideaUserView.profileCache.uid}"><img src="${jzr:userLogo(ideaUserView.profileCache.uid, ideaUserView.profileCache.logoPic, 80)}" width="80" height="80" /></a></p>
					<h2><a href="/home/${ideaUserView.profileCache.uid}"><c:out value="${ideaUserView.profileCache.nickname}" /></a></h2>
					<c:set var="age" value="${jzu:age(ideaUserView.profileCache.birthYear, ideaUserView.profileCache.birthSecret)}" />
					<c:set var="constellationName" value="${jzd:constellationName(ideaUserView.profileCache.constellationId)}" />
					<em><c:if test="${age > 0}">${age}岁&nbsp;</c:if><c:if test="${ideaUserView.profileCache.city != null && ideaUserView.profileCache.city > 0}">${jzd:cityName(ideaUserView.profileCache.city)}<c:if test="${ideaUserView.profileCache.town != null && ideaUserView.profileCache.town > 0}">${jzd:townName(ideaUserView.profileCache.town)}</c:if>&nbsp;</c:if><c:if test="${not empty constellationName}">${constellationName}&nbsp;</c:if><c:if test="${not empty ideaUserView.profileCache.profession}">${ideaUserView.profileCache.profession}</c:if></em>
					<b><c:set var="date" value="${ideaUserView.createTime}" scope="request" /><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" />响应</b>
					<c:if test="${context.uid != ideaUserView.profileCache.uid}">
						<span><a class="send-message" href="javascript:void(0);" target-uid="${ideaUserView.profileCache.uid}" target-nickname="${ideaUserView.profileCache.nickname}">私信</a></span>
						<div class="keep user-remove-interest remove-interest-${ideaUserView.profileCache.uid}" <c:if test="${!ideaUserView.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" class="done" uid="${ideaUserView.profileCache.uid}" title="点击取消收藏">已收藏</a></div>
						<div class="keep user-add-interest interest-${ideaUserView.profileCache.uid}" <c:if test="${ideaUserView.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" uid="${ideaUserView.profileCache.uid}" title="点击收藏">收藏ta</a></div>
					</c:if>
				</div><!--pub_box_m end-->
				<div class="clear"></div>
				<div class="pub_box_b"></div>
			</div><!--pub_box end-->
		</c:forEach>
	</div><!--response_list end-->
	<div class="clear"></div>
	<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
		<c:param name="pager" value="${pager}"/>
		<c:param name="url" value="/idea/${idea.id}/user" />
	</c:import>
</c:if>