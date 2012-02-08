<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="title"><h2>共有${pager.totalResults}人响应</h2></div>
<div class="response_list"><!--response_list begin-->
	<c:forEach var="responseUserView" items="${responseUserViewList}">
	<div class="pub_box mouseHover <c:choose><c:when test="${responseUserView.profileCache.gender == 1}">boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--pub_box begin-->
		<div class="pub_box_t"></div>
		<div class="pub_box_m"><!--pub_box_m begin-->
			<p><a href="/home/${responseUserView.profileCache.uid}"><img src="${jzr:userLogo(responseUserView.profileCache.uid, responseUserView.profileCache.logoPic, 80)}" width="80" height="80" /></a></p>
			<h2><a href="/home/${responseUserView.profileCache.uid}"><c:out value="${responseUserView.profileCache.nickname}" /></a></h2>
			<c:set var="age" value="${jzu:age(responseUserView.profileCache.birthYear, responseUserView.profileCache.birthSecret)}" />
			<c:set var="constellationName" value="${jzd:constellationName(responseUserView.profileCache.constellationId)}" />
			<em><c:if test="${age >= 0}">${age}岁&nbsp;</c:if><c:if test="${responseUserView.profileCache.city != null && responseUserView.profileCache.city > 0}">${jzd:cityName(responseUserView.profileCache.city)}<c:if test="${responseUserView.profileCache.town != null && responseUserView.profileCache.town > 0}">${jzd:townName(responseUserView.profileCache.town)}</c:if>&nbsp;</c:if><c:if test="${not empty constellationName}">${constellationName}&nbsp;</c:if><c:if test="${not empty responseUserView.profileCache.profession}">${responseUserView.profileCache.profession}</c:if></em>
			<b><c:set var="date" value="${responseUserView.createTime}" scope="request" /><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" />响应</b>
			<c:if test="${context.uid != responseUserView.profileCache.uid}">
				<span><a href="javascript:void(0);">私信</a></span>
				<div class="keep user-remove-interest remove-interest-${responseUserView.profileCache.uid}" <c:if test="${!responseUserView.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" class="done" uid="${responseUserView.profileCache.uid}" title="点击取消收藏">已收藏</a></div>
				<div class="keep user-add-interest interest-${responseUserView.profileCache.uid}" <c:if test="${responseUserView.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" uid="${responseUserView.profileCache.uid}" title="点击收藏">收藏ta</a></div>
			</c:if>
		</div><!--pub_box_m end-->
		<div class="clear"></div>
		<div class="pub_box_b"></div>
	</div><!--pub_box end-->
	</c:forEach>
</div><!--response_list end-->
<div class="clear"></div>
<c:if test="${not empty responseUserViewList}">
	<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
		<c:param name="pager" value="${pager}"/>
		<c:param name="url" value="/post/${post.id}/responseUser" />
	</c:import>
</c:if>