<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="zbr_item_list"><!--zbr_item_list begin-->
	<c:choose>
		<c:when test="${empty showUserViewList}"><div class="zbr_item_list_none">还没有你认识的人加入拒宅网！</div></c:when>
		<c:otherwise>
			<c:forEach var="showUserView" items="${showUserViewList}">
				<div class="item_zbr mouseHover <c:choose><c:when test='${showUserView.profile.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--item_zbr begin-->
					<div class="more"><a href="/home/${showUserView.profile.uid}" title="点击进入"></a></div>
					<div class="btn">
						<a href="javascript:void(0);" class="mail" target-uid="${showUserView.profile.uid}" target-nickname="<c:out value='${showUserView.profile.nickname}'/>">私信</a>
						<a id="removeDating${showUserView.profile.uid}" href="javascript:void(0);" class="yueta_done" <c:if test="${!showUserView.hasDating}">style="display:none;"</c:if>>已约ta</a>
						<a id="dating${showUserView.profile.uid}" href="javascript:void(0);" class="yueta" uid="${showUserView.profile.uid}" <c:if test="${showUserView.hasDating}">style="display:none;"</c:if>>约ta</a>
						<div id="removeInterest${showUserView.profile.uid}" class="ygxq" <c:if test="${!showUserView.hasInterest}">style="display:none;"</c:if>><p>已敲门</p><a href="javascript:void(0);" class="delete" uid="${showUserView.profile.uid}"></a></div>
						<a id="interest${showUserView.profile.uid}" href="javascript:void(0);" class="like" uid="${showUserView.profile.uid}" <c:if test="${showUserView.hasInterest}">style="display:none;"</c:if>>敲门</a>
					</div>
					<div></div>
					<div class="photo"><!--photo begin-->
						<c:set var="age" value="${jzu:age(showUserView.profile.birthYear, showUserView.profile.birthSecret)}" />
						<c:set var="constellationName" value="${jzd:constellationName(showUserView.profile.constellationId)}" />
						<c:if test="${age>=0 || not empty constellationName || not empty showUserView.profile.profession || not empty showUserView.profile.feature}">
							<div class="infor_show"><!--infor_show begin-->
								<c:if test="${not empty constellationName}"><p>${constellationName}</p></c:if><c:if test="${age>=0}"><p>${age}岁</p></c:if><c:if test="${not empty showUserView.profile.profession}"><p>${showUserView.profile.profession}</p></c:if>
								<c:if test="${not empty showUserView.profile.feature}"><br /><span><c:out value="${showUserView.profile.feature}" /></span></c:if>
							</div><!--infor_show end-->
						</c:if>
						<div class="face_photo"><!--face_photo begin-->
							<span>
								<a href="/home/${showUserView.profile.uid}"><img data-original="${jzr:userLogo(showUserView.profile.uid,showUserView.profile.logoPic,180)}" src="${jzr:static('/images/web2/1px.gif')}" height="180" width="180"/></a>
							</span>
						</div><!--face_photo end-->
					</div><!--photo end-->
					<div class="ta_like_list"><!--like_list begin-->
						<div class="name"><a href="/home/${showUserView.profile.uid}"><c:out value="${showUserView.profile.nickname}" /></a><c:if test="${showUserView.online}"><p class="online">当前在线</p></c:if></div>
						<c:set var="cityName" value="${jzd:cityName(showUserView.profile.city)}" />
						<c:set var="townName" value="${jzd:townName(showUserView.profile.town)}" />
						<div class="time">
							<c:if test="${not empty cityName || not empty townName}">
								<span><c:if test="${not empty cityName}">${cityName}</c:if><c:if test="${not empty townName}">${townName}</c:if><em>|</em></span>
							</c:if>
							<p><c:choose><c:when test='${not empty showUserView.freeDateList}'>${jzu:showFreeDates(showUserView.freeDateList,7)}&nbsp;有空</c:when><c:otherwise>还未标注空闲时间</c:otherwise></c:choose></p>
						</div>
						<ul class="list"><!--list begin-->
							<c:choose>
								<c:when test="${empty showUserView.userActViewList}">
									<p>ta还未添加想去的项目哦！</p>
								</c:when>
								<c:otherwise>
									<c:forEach var="userActView" items="${showUserView.userActViewList}">
										<li><em>·</em><a href="/act/${userActView.act.id}"><c:out value="${userActView.act.name}" /></a><c:if test="${userActView.userAct.top}"><span>很想去</span></c:if></li>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</ul><!--list end-->
					</div><!--like_list end-->
				</div><!--item_zbr end-->
			</c:forEach>
		</c:otherwise>
	</c:choose>
</div><!--zbr_item_list end-->
<c:if test="${not empty showUserViewList}">
	<c:choose>
		<c:when test="${pager==null}">
			<c:if test="${showUserCount!=null&&showUserCount>10}">
				<div class="zbr_more"><a href="/${userType}/<c:choose><c:when test='${loginUser.gender==1}'>female</c:when><c:otherwise>male</c:otherwise></c:choose>/1">查看更多</a></div>
			</c:if>
		</c:when>
		<c:otherwise>
			<div class="clear"></div>
			<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
				<c:param name="pager" value="${pager}"/>
				<c:param name="url" value="/${userType}/${genderType}" />
			</c:import>
		</c:otherwise>
	</c:choose>
</c:if>