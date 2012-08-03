<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- <div class="title3" idea-id="${idea.id}"><!--title2 begin-->
	<div id="city-select" class="l_select_menu" name="cityId"><!--l_select_menu begin-->
		<p><a href="javascript:void(0);" hidefocus city-id="${cityId}"><c:choose><c:when test="${cityId == 0}">全国</c:when><c:otherwise>${jzd:cityName(cityId)}</c:otherwise></c:choose></a></p>
		<div></div>
		<div class="l_select_menu_box"><!--city_list begin-->
			<div class="l_select_menu_box_t"></div>
			<div class="l_select_menu_box_m"><!--l_select_menu_box_m begin-->
				<div class="vip_city"><!--vip_city begin-->
					<h3>主要城市:</h3>
					<div class="list city-list"><!--list begin-->
						<c:forEach var="specialCity" items="${jzd:specialCityList()}">
							<a href="javascript:void(0);" value="${specialCity.id}" <c:if test="${cityId==specialCity.id}">class="act"</c:if>>${specialCity.name}</a>
						</c:forEach>
						<a href="javascript:void(0);" value="0" <c:if test="${cityId == 0}">class="act"</c:if>>全国</a>
					</div><!--list end-->
				</div><!--vip_city end-->
				<div class="all_city"><!--all_city begin-->
					<h3>所有城市:</h3>
					<div class="list"><!--list begin-->
						<div class="sheng">
						</div>
						<div class="shi city-list" style="display: none;"><!--city_area begin-->
						</div><!--city_area end-->
					</div><!--list end-->
				</div><!--all_city end-->
			</div><!--l_menu_box_m end-->
			<div class="l_select_menu_box_b"></div>
		</div><!--l_select_menu_box end-->
	</div><!--l_select_menu end-->
	<div id="gender-select" class="select_menu" name="genderType"><!--select_menu begin-->
		<p><a href="javascript:void(0);"></a></p>
		<div></div>
		<div class="select_box"><!--select_box begin-->
			<span>
				<a href="javascript:void(0);" value="all" <c:if test="${empty genderType || genderType == 'all'}">class="selected"</c:if>>所有小宅</a>
				<a href="javascript:void(0);" value="male" <c:if test="${genderType == 'male'}">class="selected"</c:if>>宅男</a>
				<a href="javascript:void(0);" value="female" <c:if test="${genderType == 'female'}">class="selected"</c:if>>宅女</a>
			</span>
			<em></em>
		</div><!--select_box end-->
	</div><!--select_menu end-->
	<div class="all"><c:choose><c:when test="${cityId > 0}">${jzd:cityName(cityId)}</c:when><c:otherwise>全国</c:otherwise></c:choose>有${pager.totalResults}位<c:choose><c:when test="${genderType == 'male'}">宅男</c:when><c:when test="${genderType == 'female'}">宅女</c:when><c:otherwise>小宅</c:otherwise></c:choose>想去</div>
</div><!--title3 end-->
<c:choose>
	<c:when test="${not empty ideaUserViewList}">
		<div class="response_list"><!--response_list begin-->
			<c:forEach var="ideaUserView" items="${ideaUserViewList}">
				<div class="pub_box mouseHover <c:choose><c:when test="${ideaUserView.profileCache.gender == 1}">boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--pub_box begin-->
					<div class="pub_box_t"></div>
					<div class="pub_box_m"><!--pub_box_m begin-->
						<p><a href="/home/${ideaUserView.profileCache.uid}"><img src="${jzr:userLogo(ideaUserView.profileCache.uid, ideaUserView.profileCache.logoPic, 80)}" width="80" height="80" /></a></p>
						<h2><a href="/home/${ideaUserView.profileCache.uid}"><c:out value="${ideaUserView.profileCache.nickname}" /></a></h2>
						<b><c:set var="date" value="${ideaUserView.createTime}" scope="request" /><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" />发布</b>
						<c:set var="age" value="${jzu:age(ideaUserView.profileCache.birthYear, ideaUserView.profileCache.birthSecret)}" />
						<c:set var="constellationName" value="${jzd:constellationName(ideaUserView.profileCache.constellationId)}" />
						<em><c:if test="${age > 0}">${age}岁&nbsp;</c:if><c:if test="${ideaUserView.profileCache.city != null && ideaUserView.profileCache.city > 0}">${jzd:cityName(ideaUserView.profileCache.city)}<c:if test="${ideaUserView.profileCache.town != null && ideaUserView.profileCache.town > 0}">${jzd:townName(ideaUserView.profileCache.town)}</c:if>&nbsp;</c:if><c:if test="${not empty constellationName}">${constellationName}&nbsp;</c:if><c:if test="${not empty ideaUserView.profileCache.profession}">${ideaUserView.profileCache.profession}</c:if></em>
						<c:if test="${context.uid != ideaUserView.profileCache.uid}">
							<div class="date"><a href="javascript:void(0);" target-uid="${ideaUserView.profileCache.uid}" target-nickname="${ideaUserView.profileCache.nickname}" idea-id="${idea.id}">约ta同去</a></div>
							<span><a class="send-message" href="javascript:void(0);" target-uid="${ideaUserView.profileCache.uid}" target-nickname="${ideaUserView.profileCache.nickname}">私信</a></span>
							<div class="keep user-remove-interest remove-interest-${ideaUserView.profileCache.uid}" <c:if test="${!ideaUserView.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" class="done" uid="${ideaUserView.profileCache.uid}" title="点击取消关注">已关注</a></div>
							<div class="keep user-add-interest interest-${ideaUserView.profileCache.uid}" <c:if test="${ideaUserView.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" uid="${ideaUserView.profileCache.uid}" title="点击关注">关注ta</a></div>
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
			<c:param name="url" value="/idea/${idea.id}/user/${cityId}_${genderType}" />
		</c:import>
	</c:when>
	<c:otherwise>
		<div class="response_list"><!--response_list begin-->
			<div class="none">暂时还没有人想去</div>
		</div><!--response_list end-->		
	</c:otherwise>
</c:choose> --%>



<div class="wgo_title"  idea-id="${idea.id}"><!--wgo_title begin-->
		<div class="tab">
			<span <c:if test="${tabType=='ideaUser'}">class="act"</c:if>><p></p><a href="/idea/${idea.id}">想去的人(${idea.useCount})</a><p></p></span>
		</div>
		<c:if test="${tabType=='ideaUser' }">
	<div id="gender-select" class="select_menu" name="genderType"><!--select_menu begin-->
		<p><a href="javascript:void(0);"></a></p>
		<div></div>
		<div class="select_box"><!--select_box begin-->
			<span>
				<a href="javascript:void(0);" value="all" <c:if test="${empty genderType || genderType == 'all'}">class="selected"</c:if>>所有小宅</a>
				<a href="javascript:void(0);" value="male" <c:if test="${genderType == 'male'}">class="selected"</c:if>>宅男</a>
				<a href="javascript:void(0);" value="female" <c:if test="${genderType == 'female'}">class="selected"</c:if>>宅女</a>
			</span>
			<em></em>
		</div><!--select_box end-->
	</div><!--select_menu end-->	
	<div id="city-select" class="l_select_menu" name="cityId"><!--l_select_menu begin-->
		<p><a href="javascript:void(0);" hidefocus city-id="${cityId}"><c:choose><c:when test="${cityId == 0}">全国</c:when><c:otherwise>${jzd:cityName(cityId)}</c:otherwise></c:choose></a></p>
		<div></div>
		<div class="l_select_menu_box"><!--city_list begin-->
			<div class="l_select_menu_box_t"></div>
			<div class="l_select_menu_box_m"><!--l_select_menu_box_m begin-->
				<div class="vip_city"><!--vip_city begin-->
					<h3>主要城市:</h3>
					<div class="list city-list"><!--list begin-->
						<c:forEach var="specialCity" items="${jzd:specialCityList()}">
							<a href="javascript:void(0);" value="${specialCity.id}" <c:if test="${cityId==specialCity.id}">class="act"</c:if>>${specialCity.name}</a>
						</c:forEach>
						<a href="javascript:void(0);" value="0" <c:if test="${cityId == 0}">class="act"</c:if>>全国</a>
					</div><!--list end-->
				</div><!--vip_city end-->
				<div class="all_city"><!--all_city begin-->
					<h3>所有城市:</h3>
					<div class="list"><!--list begin-->
						<div class="sheng">
						</div>
						<div class="shi city-list" style="display: none;"><!--city_area begin-->
						</div><!--city_area end-->
					</div><!--list end-->
				</div><!--all_city end-->
			</div><!--l_menu_box_m end-->
			<div class="l_select_menu_box_b"></div>
		</div><!--l_select_menu_box end-->
	</div><!--l_select_menu end-->
	</c:if>
</div><!--wgo_title end-->
<c:choose>
	<c:when test="${not empty ideaUserViewList}">
		<div class="wgo_list"><!--wgo_list begin-->
			<ul>
				<c:forEach var="ideaUserView" items="${ideaUserViewList}">
					<li class="<c:choose><c:when test="${ideaUserView.profileCache.gender == 1}">boy</c:when><c:otherwise>girl</c:otherwise></c:choose> mouseHover">
						<p><a href="/home/${ideaUserView.profileCache.uid}"><img src="${jzr:userLogo(ideaUserView.profileCache.uid, ideaUserView.profileCache.logoPic, 80)}" width="80" height="80" /></a></p>
						<h3><a href="/home/${ideaUserView.profileCache.uid}"><c:out value="${ideaUserView.profileCache.nickname}" /></a></h3>
						<c:set var="age" value="${jzu:age(ideaUserView.profileCache.birthYear, ideaUserView.profileCache.birthSecret)}" />
						<c:set var="constellationName" value="${jzd:constellationName(ideaUserView.profileCache.constellationId)}" />
						<em><c:if test="${age > 0}">${age}岁&nbsp;</c:if><c:if test="${ideaUserView.profileCache.city != null && ideaUserView.profileCache.city > 0}">${jzd:cityName(ideaUserView.profileCache.city)}<c:if test="${ideaUserView.profileCache.town != null && ideaUserView.profileCache.town > 0}">${jzd:townName(ideaUserView.profileCache.town)}</c:if>&nbsp;</c:if><c:if test="${not empty constellationName}">${constellationName}&nbsp;</c:if><c:if test="${not empty ideaUserView.profileCache.profession}">${ideaUserView.profileCache.profession}</c:if></em>
						<c:if test="${context.uid != ideaUserView.profileCache.uid}">
							<div class="date"><a href="javascript:void(0);" target-uid="${ideaUserView.profileCache.uid}" target-nickname="${ideaUserView.profileCache.nickname}" idea-id="${idea.id}">约ta同去</a></div>
						</c:if>
					</li>
				</c:forEach>
			</ul>
			<div class="clear"></div>
			<c:choose>
				<c:when test="${tabType=='ideaUser'}">
					<c:set var="url" value="/idea/${idea.id}/user/${cityId}_${genderType}"></c:set>
				</c:when>
				<c:otherwise>
					<c:set var="url" value="/idea/${idea.id}/interest" />
				</c:otherwise>
			</c:choose>
			<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
				<c:param name="pager" value="${pager}"/>
				<c:param name="url" value="${url}" />
			</c:import>
		</div><!--wgo_list end-->
	</c:when>
	<c:otherwise>
		<div class="wgo_list">
			<div class="none">暂时还没有人想去</div>
		</div>		
	</c:otherwise>
</c:choose>
