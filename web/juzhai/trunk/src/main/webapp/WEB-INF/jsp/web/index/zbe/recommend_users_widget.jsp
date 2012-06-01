<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${not empty recommendUserViewList}">
	<div class="content_box w285"><!--content begin-->
		<div class="t"></div>
		<div class="m">
			<div class="right_title"><h2>你可能感兴趣的小宅</h2><a href="/profile/preference">去调整口味</a></div>
			<div class="maybe_like"><!--maybe_like begin-->
				<ul>
					<c:forEach var="recommendUserView" items="${recommendUserViewList}">
						<li class="mouseHover <c:choose><c:when test="${recommendUserView.profile.gender == 1}">boy</c:when><c:otherwise>girl</c:otherwise></c:choose>">
							<p><a href="/home/${recommendUserView.profile.uid}"><img src="${jzr:userLogo(recommendUserView.profile.uid, recommendUserView.profile.logoPic, 80)}" width="50" height="50" /></a></p>
							<span>
								<a href="/home/${recommendUserView.profile.uid}" class="name"><c:out value="${recommendUserView.profile.nickname}" /></a>
								<a href="javascript:void(0);" class="mail send-message" target-uid="${recommendUserView.profile.uid}"></a>
								<a href="javascript:void(0);" class="like user-add-interest interest-${recommendUserView.profile.uid}" <c:if test="${recommendUserView.hasInterest}">style="display:none"</c:if> uid="${recommendUserView.profile.uid}" title="点击关注"></a>
								<a href="javascript:void(0);" class="like done user-remove-interest remove-interest-${recommendUserView.profile.uid}" <c:if test="${!recommendUserView.hasInterest}">style="display:none"</c:if> uid="${recommendUserView.profile.uid}" title="点击取消关注"></a>
							</span>
							<c:set var="age" value="${jzu:age(recommendUserView.profile.birthYear, recommendUserView.profile.birthSecret)}" />
							<c:set var="constellationName" value="${jzd:constellationName(recommendUserView.profile.constellationId)}" />
							<em><c:if test="${age > 0}">${age}岁&nbsp;</c:if><c:if test="${recommendUserView.profile.city != null && recommendUserView.profile.city > 0}">${jzd:cityName(recommendUserView.profile.city)}<c:if test="${recommendUserView.profile.town != null && recommendUserView.profile.town > 0}">${jzd:townName(recommendUserView.profile.town)}</c:if>&nbsp;</c:if><c:if test="${not empty constellationName}">${constellationName}&nbsp;</c:if><c:if test="${not empty recommendUserView.profile.profession}">${recommendUserView.profile.profession}</c:if></em>
						</li>
					</c:forEach>
				</ul>
			</div><!--maybe_like end-->
		</div>
		<div class="t"></div>
	</div><!--content end-->
</c:if>