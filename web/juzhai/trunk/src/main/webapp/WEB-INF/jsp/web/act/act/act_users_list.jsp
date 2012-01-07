<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<div class="project_want_go"><!--project_want_go begin-->
	<div class="title"><!--title begin-->
		<h2>想约个人一起去玩<c:out value="${act.name}" />么？</h2>
		<span>
			<select id="genderSelect" actid="${act.id}" cityid="${cityId}">
				<option value="all" <c:if test="${genderType=='all'}">selected="selected"</c:if>>性别不限</option>
				<option value="male" <c:if test="${genderType=='male'}">selected="selected"</c:if>>男性</option>
				<option value="female" <c:if test="${genderType=='female'}">selected="selected"</c:if>>女性</option>
			</select>
		</span>
		<span>
			<select id="citySelect" actid="${act.id}" gendertype="${genderType}">
				<option value="0" <c:if test="${cityId==0}">selected="selected"</c:if>>全国</option>
				<option value="2" <c:if test="${cityId==2}">selected="selected"</c:if>>上海</option>
				<option value="1" <c:if test="${cityId==1}">selected="selected"</c:if>>北京</option>
				<option value="181" <c:if test="${cityId==181}">selected="selected"</c:if>>广州</option>
				<option value="183" <c:if test="${cityId==183}">selected="selected"</c:if>>深圳</option>
				<option value="108" <c:if test="${cityId==108}">selected="selected"</c:if>>武汉</option>
				<option value="3" <c:if test="${cityId==3}">selected="selected"</c:if>>天津</option>
				<option value="69" <c:if test="${cityId==69}">selected="selected"</c:if>>西安</option>
				<option value="157" <c:if test="${cityId==157}">selected="selected"</c:if>>南京</option>
				<option value="343" <c:if test="${cityId==343}">selected="selected"</c:if>>杭州</option>
				<option value="241" <c:if test="${cityId==241}">selected="selected"</c:if>>成都</option>
				<option value="4" <c:if test="${cityId==4}">selected="selected"</c:if>>重庆</option>
				<option value="27" <c:if test="${cityId==27}">selected="selected"</c:if>>沈阳</option>
				<option value="90" <c:if test="${cityId==90}">selected="selected"</c:if>>郑州</option>
				<option value="125" <c:if test="${cityId==125}">selected="selected"</c:if>>长沙</option>
				<option value="363" <c:if test="${cityId==363}">selected="selected"</c:if>>香港</option>
			</select>
		</span>
	</div><!--title end-->
	<c:choose>
		<c:when test="${empty actUserViewList}">
			<div class="none">
				目前还没有人想玩${act.name}！
			</div>
		</c:when>
		<c:otherwise>
			<ul>
				<c:forEach var="actUserView" items="${actUserViewList}">
					<li class="mouseHover"><!--li begin-->
						<div class="photo boy"><!--photo begin-->
							<c:if test="${context.uid!=actUserView.profileCache.uid}">
								<div class="link_t" <c:if test="${actUserView.hasInterest}">style="display:none;"</c:if>><!--link_t begin-->
									<p></p><a href="javascript:void(0);" uid="${actUserView.profileCache.uid}">敲门</a><p></p>
								</div><!--link_t end-->
								<div id="hasInterest${actUserView.profileCache.uid}" class="link_t_done" <c:if test="${!actUserView.hasInterest}">style="display:none;"</c:if>><!--link_t_done begin-->
									<p></p><span>已敲门</span><p></p>
								</div><!--link_t_done end-->
							</c:if>
							<c:if test="${context.uid!=actUserView.profileCache.uid}">
								<div id="date${actUserView.profileCache.uid}" class="date_t" <c:if test="${actUserView.hasDating}">style="display:none;"</c:if>><!--date_t begin-->
									<p></p><a href="javascript:void(0);" uid="${actUserView.profileCache.uid}">约ta</a><p></p>
								</div><!--date_t end-->
								<div id="hasDate${actUserView.profileCache.uid}" class="date_t_done" <c:if test="${!actUserView.hasDating}">style="display:none;"</c:if>><!--date_t_done begin-->
									<p></p><span>已约</span><p></p>
								</div><!--date_t_done end-->
							</c:if>
							<c:set var="age" value="${jzu:age(actUserView.profileCache.birthYear)}" />
							<c:set var="constellationName" value="${jzd:constellationName(actUserView.profileCache.constellationId)}" />
							<c:if test="${age>=0 || not empty constellationName || not empty actUserView.profileCache.profession || not empty actUserView.profileCache.feature}">
								<div class="infor_show"><!--infor_show begin-->
									<c:if test="${not empty constellationName}"><p>${constellationName}</p></c:if><c:if test="${age>=0}"><p>${age}岁</p></c:if><c:if test="${not empty actUserView.profileCache.profession}"><p>${actUserView.profileCache.profession}</p></c:if>
									<c:if test="${not empty actUserView.profileCache.feature}"><br /><span><c:out value="${actUserView.profileCache.feature}" /></span></c:if>
								</div><!--infor_show end-->
							</c:if>
							<div class="face_photo"><!--face_photo begin-->
								<span>
									<a href="/home/${actUserView.profileCache.uid}"><img src="${jzr:userLogo(actUserView.profileCache.uid,actUserView.profileCache.logoPic,180)}" height="180"/></a>
								</span>
							</div><!--face_photo end-->
							<div class="name"><!--name begin-->
								<a href="/home/${actUserView.profileCache.uid}"><c:out value="${actUserView.profileCache.nickname}" /></a><c:if test="${actUserView.online}"><span class="online">当前在线</span></c:if>
								<p title="<c:choose><c:when test='${not empty actUserView.freeDateList}'>${jzu:showFreeDates(actUserView.freeDateList,7)}&nbsp;有空</c:when><c:otherwise>还未标注空闲时间</c:otherwise></c:choose>"><c:choose><c:when test="${not empty actUserView.freeDateList}">${jzu:showFreeDates(actUserView.freeDateList,4)}&nbsp;有空</c:when><c:otherwise><%-- 还未标注空闲时间 --%></c:otherwise></c:choose></p>
							</div><!--name end-->
						</div><!--photo end-->
					</li><!--li end-->
				</c:forEach>
			</ul>
			<div class="clear"></div>
			<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
				<c:param name="pager" value="${pager}"/>
				<c:param name="url" value="/act/${act.id}/users_${genderType}_${cityId}" />
			</c:import>
		</c:otherwise>
	</c:choose>
</div><!--project_want_go end-->
