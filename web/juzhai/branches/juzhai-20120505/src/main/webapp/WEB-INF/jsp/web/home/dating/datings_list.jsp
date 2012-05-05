<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="item_box"><!--item_box begin-->
	<c:choose>
		<c:when test="${tabType=='datings'}"><c:set var="divClass" value="idate" /></c:when>
		<c:when test="${tabType=='datingMes'}"><c:set var="divClass" value="dateme" /></c:when>
	</c:choose>
	<div class="${divClass}"><!--idate begin-->
		<c:forEach var="datingView" items="${datingViewList}">
			<div class="item mouseHover <c:choose><c:when test='${datingView.profileCache.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--item begin-->
				<c:if test="${tabType=='datings'}">
					<div class="close"><a href="javascript:void(0);" datingid="${datingView.dating.id}"></a></div>
				</c:if>
				<c:choose>
					<c:when test="${tabType=='datings'}">
						<c:if test="${datingView.dating.response==0}">
							<div class="date_waiting"><a href="javascript:void(0);" uid="${datingView.profileCache.uid}" datingid="${datingView.dating.id}">修改</a><p>等待回应中...</p></div>
						</c:if>
						<c:if test="${datingView.dating.response==1}">
							<div class="date_done"><span></span><p>ta已接受你的邀请<br />ta的<c:import url="/WEB-INF/jsp/web/common/fragment/dating_contact_type.jsp">
								<c:param name="contactType" value="${datingView.dating.receiverContactType}"/>
							</c:import>:<c:out value="${datingView.dating.receiverContactValue}" /></p><span></span></div>
						</c:if>
					</c:when>
					<c:otherwise>
						<div id="respDating${datingView.dating.id}" class="date_btn" <c:if test="${datingView.dating.response==1}">style="display:none;"</c:if>><a href="javascript:void(0);" datingid="${datingView.dating.id}">接受邀请</a><p>并交换联系方式</p></div>
						<div id="hasRespDating${datingView.dating.id}" class="date_done" <c:if test="${datingView.dating.response==0}">style="display:none;"</c:if>><span></span><p>已接受ta的邀请<br />ta的<c:import url="/WEB-INF/jsp/web/common/fragment/dating_contact_type.jsp">
							<c:param name="contactType" value="${datingView.dating.starterContactType}"/>
						</c:import>:<c:out value="${datingView.dating.starterContactValue}" /></p><span></span></div>
					</c:otherwise>
				</c:choose>
				<div></div>
				<div class="photo"><!--photo begin-->
					<c:set var="age" value="${jzu:age(datingView.profileCache.birthYear, datingView.profileCache.birthSecret)}" />
					<c:set var="constellationName" value="${jzd:constellationName(datingView.profileCache.constellationId)}" />
					<c:if test="${age>=0 || not empty constellationName || not empty datingView.profileCache.profession || not empty datingView.profileCache.feature}">
						<div class="infor_show"><!--infor_show begin-->
							<c:if test="${not empty constellationName}"><p>${constellationName}</p></c:if><c:if test="${age>=0}"><p>${age}岁</p></c:if><c:if test="${not empty datingView.profileCache.profession}"><p>${datingView.profileCache.profession}</p></c:if>
							<c:if test="${not empty datingView.profileCache.feature}"><br /><span><c:out value="${datingView.profileCache.feature}" /></span></c:if>
						</div><!--infor_show end-->
					</c:if>
					<div class="face_photo"><!--face_photo begin-->
						<span>
							<a href="/home/${datingView.profileCache.uid}"><img data-original="${jzr:userLogo(datingView.profileCache.uid,datingView.profileCache.logoPic,180)}" src="${jzr:static('/images/web/1px.gif')}" width="180" height="180"/></a>
						</span>
					</div><!--face_photo end-->
				</div><!--photo end-->
				<div class="date_infor"><!--like_list begin-->
					<div class="name"><a href="/home/${datingView.profileCache.uid}"><c:out value="${datingView.profileCache.nickname}" /></a><span>上海浦东</span></div>
					<div class="date_detail"><!--date_detail begin-->
						<p>我约ta去:<a href="/act/${datingView.act.id}" id="actInfo${datingView.profileCache.uid}"><c:out value="${datingView.act.name}" /></a></p>
						<p>费用: <span id="consumeType${datingView.profileCache.uid}"><c:import url="/WEB-INF/jsp/web/common/fragment/dating_consume_type.jsp">
							<c:param name="consumeType" value="${datingView.dating.consumeType}"/>
						</c:import></span></p>
						<p>发布于&nbsp;<c:set var="date" value="${datingView.dating.createTime}" scope="request" /><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" /></p>
					</div><!--date_detail end-->
				</div><!--like_list end-->
			</div><!--item end-->
		</c:forEach>
	</div><!--idate end-->
</div><!--item_box end-->
<div class="clear"></div>
<c:if test="${response=='accept'}">
	<c:set var="tabType" value="${tabType}/accept" />
</c:if>
<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
	<c:param name="pager" value="${pager}"/>
	<c:param name="url" value="/home/${tabType}" />
</c:import>