<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<div class="project_want_go"><!--project_want_go begin-->
	<c:choose>
		<c:when test="${empty actUserViewList}">
			<div class="none">
				目前还没有人想玩密室脱逃！
			</div>
		</c:when>
		<c:otherwise>
			<div class="title"><!--title begin-->
				<h2>想约个人一起去玩<c:out value="${act.name}" />么？</h2>
				<span>
					<select name="language" id="genderSelect" actid="${act.id}">
						<option value="all" <c:if test="${genderType=='all'}">selected="selected"</c:if>>性别不限</option>
						<option value="male" <c:if test="${genderType=='male'}">selected="selected"</c:if>>男性</option>
						<option value="female" <c:if test="${genderType=='female'}">selected="selected"</c:if>>女性</option>
					</select>
				</span>
			</div><!--title end-->
			<ul>
				<c:forEach var="actUserView" items="${actUserViewList}">
					<li class="mouseHover"><!--li begin-->
						<div class="photo boy"><!--photo begin-->
							<c:if test="${actUserView.hasInterest!=null&&context.uid!=actUserView.profileCache.uid}">
								<div class="link_t" <c:if test="${actUserView.hasInterest}">style="display:none;"</c:if>><!--link_t begin-->
									<p></p><a href="javascript:void(0);" uid="${actUserView.profileCache.uid}">感兴趣</a><p></p>
								</div><!--link_t end-->
								<div id="hasInterest${actUserView.profileCache.uid}" class="link_t_done" <c:if test="${!actUserView.hasInterest}">style="display:none;"</c:if>><!--link_t_done begin-->
									<p></p><span>已感兴趣</span><p></p>
								</div><!--link_t_done end-->
							</c:if>
							<c:if test="${actUserView.hasDating!=null&&context.uid!=actUserView.profileCache.uid}">
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
									<a href="/home/${actUserView.profileCache.uid}"><img src="${actUserView.profileCache.logoPic}" height="180"/></a>
								</span>
							</div><!--face_photo end-->
							<div class="name"><!--name begin-->
								<p><c:out value="${actUserView.profileCache.nickname}" /></p><c:if test="${actUserView.online}"><span class="online">当前在线</span></c:if>
							</div><!--name end-->
						</div><!--photo end-->
					</li><!--li end-->
				</c:forEach>
			</ul>
			<div class="clear"></div>
			<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
				<c:param name="pager" value="${pager}"/>
				<c:param name="url" value="/act/${act.id}/users/${genderType}" />
			</c:import>
		</c:otherwise>
	</c:choose>
</div><!--project_want_go end-->
