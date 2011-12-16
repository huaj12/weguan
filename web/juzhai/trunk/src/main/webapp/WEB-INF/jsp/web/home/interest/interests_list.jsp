<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="item_box"><!--item_box begin-->
	<c:choose>
		<c:when test="${tabType=='interests'}"><c:set var="divClass" value="ilike" /></c:when>
		<c:when test="${tabType=='interestMes'}"><c:set var="divClass" value="likeme" /></c:when>
	</c:choose>
	<div class="${divClass}"><!--ilike begin-->
		<c:forEach var="interestUserView" items="${interestUserViewList}">
			<div class="item mouseHover <c:choose><c:when test='${interestUserView.profileCache.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--item begin-->
				<c:if test="${tabType=='interests'}">
					<div class="close"><a href="#"></a></div>
				</c:if>
				<div class="btn">
					<a href="javascript:void(0);" class="yueta" onclick="javascript:openDating(this);" uid="${interestUserView.profileCache.uid}">约ta</a>
					<c:if test="${tabType=='interestMes'}">
						<c:choose>
							<c:when test="${interestUserView.hasInterest}">
								<a href="javascript:void(0);" class="cancel_like" uid="${interestUserView.profileCache.uid}">已感兴趣</a>
							</c:when>
							<c:otherwise>
								<a href="javascript:void(0);" class="like" uid="${interestUserView.profileCache.uid}">感兴趣</a>
							</c:otherwise>
						</c:choose>
					</c:if>
				</div>
				<!-- <div class="yueta_ts"><span></span><p>好的，我们会告知ta！</p><span></span></div> -->
				<div></div>
				<div class="photo"><!--photo begin-->
					<c:set var="age" value="${jzu:age(interestUserView.profileCache.birthYear)}" />
					<c:set var="constellationName" value="${jzd:constellationName(interestUserView.profileCache.constellationId)}" />
					<c:if test="${age>=0 || not empty constellationName || not empty interestUserView.profileCache.profession || not empty interestUserView.profileCache.feature}">
						<div class="infor_show"><!--infor_show begin-->
							<c:if test="${not empty constellationName}"><p>${constellationName}</p></c:if><c:if test="${age>=0}"><p>${age}岁</p></c:if><c:if test="${not empty interestUserView.profileCache.profession}"><p>${interestUserView.profileCache.profession}</p></c:if>
							<c:if test="${not empty interestUserView.profileCache.feature}"><br /><span><c:out value="${interestUserView.profileCache.feature}" /></span></c:if>
						</div><!--infor_show end-->
					</c:if>
					<div class="face_photo"><!--face_photo begin-->
						<span><a href="/home/${interestUserView.profileCache.uid}"><img src="${interestUserView.profileCache.logoPic}" width="180"/></a></span>
					</div><!--face_photo end-->
					<c:set var="cityName" value="${jzd:cityName(interestUserView.profileCache.city)}" />
					<c:set var="townName" value="${jzd:townName(interestUserView.profileCache.town)}" />
					<div class="city_online"><!--city_online begin-->
						<span>&nbsp;<c:if test="${not empty cityName || not empty townName}">ta在</c:if><c:if test="${not empty cityName}">${cityName}</c:if><c:if test="${not empty townName}">${townName}</c:if></span><!-- <p class="online">近期来访</p> -->
					</div><!--city_online end-->
				</div><!--photo end-->	
				<div class="ta_like_list"><!--like_list begin-->
					<div class="name"><a href="/home/${interestUserView.profileCache.uid}"><c:out value="${interestUserView.profileCache.nickname}" /></a><span>周末想去...</span></div>
					<ul class="list"><!--list begin-->
						<c:forEach var="userActView" items="${interestUserView.userActViewList}">
							<li><em>·</em><a href="/act/${userActView.act.id}"><c:out value="${userActView.act.name}" /></a><c:if test="${userActView.userAct.top}"><span>很想去</span></c:if></li>
						</c:forEach>
					</ul><!--list end-->
				</div><!--like_list end-->
			</div><!--item end-->
		</c:forEach>
	</div><!--ilike end-->
</div><!--item_box end-->
<div class="clear"></div>
<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
	<c:param name="pager" value="${pager}"/>
	<c:param name="url" value="/home/${tabType}" />
</c:import>