<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${not empty visitorViewList}">
	<div class="content_box w285"><!--content begin-->
		<div class="t"></div>
		<div class="m">
			<div class="right_title"><h2>这些人最近来看过你</h2><a href="#">更多</a></div>
			<div class="visitors"><!--visitors begin-->
				<ul>
					<c:forEach var="visitorView" items="${visitorViewList}">
						<li class="mouseHover <c:choose><c:when test="${visitorView.profileCache.gender == 1}">boy</c:when><c:otherwise>girl</c:otherwise></c:choose>">
							<p><a href="/home/${visitorView.profileCache.uid}"><img src="${jzr:userLogo(visitorView.profileCache.uid,visitorView.profileCache.logoPic,80)}" width="50" height="50" /></a></p>
							<span><a href="/home/${visitorView.profileCache.uid}" class="name"><c:out value="${visitorView.profileCache.nickname}" /></a><b><c:set var="date" value="${visitorView.visitTime}" scope="request" /><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" />来看过你</b></span>
							<c:set var="age" value="${jzu:age(visitorView.profileCache.birthYear, visitorView.profileCache.birthSecret)}" />
							<c:set var="constellationName" value="${jzd:constellationName(visitorView.profileCache.constellationId)}" />
							<em><c:if test="${age > 0}">${age}岁&nbsp;</c:if><c:if test="${visitorView.profileCache.city != null && visitorView.profileCache.city > 0}">${jzd:cityName(visitorView.profileCache.city)}<c:if test="${visitorView.profileCache.town != null && visitorView.profileCache.town > 0}">${jzd:townName(visitorView.profileCache.town)}</c:if>&nbsp;</c:if><c:if test="${not empty constellationName}">${constellationName}&nbsp;</c:if><c:if test="${not empty visitorView.profileCache.profession}">${visitorView.profileCache.profession}</c:if></em>
						</li>
					</c:forEach>
				</ul>
			</div><!--visitors end-->
		</div>
		<div class="t"></div>
	</div><!--content end-->
</c:if>