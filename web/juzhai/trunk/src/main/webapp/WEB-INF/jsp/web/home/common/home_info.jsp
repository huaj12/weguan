<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${profile.uid==context.uid}">
	<c:set var="freeDateFormHide" value="true" scope="request" />
	<jsp:include page="/WEB-INF/jsp/web/common/set_free_date.jsp" />
</c:if>
<div class="head_area"><!--head_area begin-->
	<div class="face"><a href="/home/${profile.uid}"><img src="${jzr:userLogo(profile.uid,profile.logoPic,180)}" width="180" /></a></div>
	<div class="user_infor"><!--user_infor begin-->
		<h2><a href="/home/${profile.uid}"><c:out value="${profile.nickname}" /></a></h2>
		<c:if test="${online != null}">
			<c:choose>
				<c:when test="${online}"><div class="online">当前在线</div></c:when>
				<c:otherwise><div class="offline">当前不在线</div></c:otherwise>
			</c:choose>
		</c:if>
		<div class="clear"></div>
		<div class="kongxian"><font><c:choose><c:when test="${not empty freeDateList}">${jzu:showFreeDates(freeDateList,7)}&nbsp;有空</c:when><c:otherwise>还未标注空闲时间</c:otherwise></c:choose></font><c:if test="${profile.uid==context.uid}"><a href="javascript:void(0);">设置</a></c:if></div>
		<div class="clear"></div>
		<c:set var="cityName" value="${jzd:cityName(profile.city)}" />
		<c:set var="townName" value="${jzd:townName(profile.town)}" />
		<c:set var="age" value="${jzu:age(profile.birthYear, profile.birthSecret)}" />
		<c:set var="constellationName" value="${jzd:constellationName(profile.constellationId)}" />
		<c:if test="${not empty cityName || not empty townName || age>=0 || not empty constellationName || not empty profile.profession}">
			<div class="ziye"><span><c:if test="${not empty cityName}">${cityName}</c:if><c:if test="${not empty townName}">${townName}</c:if></span><c:if test="${age>=0}"><span>${age}岁</span></c:if><c:if test="${not empty constellationName}"><span>${constellationName}</span></c:if><c:if test="${not empty profile.profession}"><span>${profile.profession}</span></c:if></div>
		</c:if>
		<c:if test="${not empty profile.feature}">
			<div class="tedian"><c:out value="${profile.feature}" /></div>
		</c:if>
		<c:if test="${profile.uid!=context.uid}">
			<div class="dated" datingid="${datingView.dating.id}" uid="${profile.uid}" <c:if test="${datingView==null}">style="display:none;"</c:if>><!--dated begin-->
				<div class="datefor"><span></span><p>已约ta&nbsp;<font class="_act"><c:out value="${datingView.act.name}" /></font>&nbsp;<font class="_contact"><c:if test="${datingView.dating.response==1}">ta的<c:import url="/WEB-INF/jsp/web/common/fragment/dating_contact_type.jsp">
					<c:param name="contactType" value="${datingView.dating.receiverContactType}"/>
				</c:import>:<c:out value="${datingView.dating.receiverContactValue}" /></c:if></font></p><span></span></div>
				<a <c:if test="${datingView.dating.response!=0}">style="display:none;"</c:if> href="javascript:void(0);" class="modifyDating">修改</a><a href="javascript:void(0);" class="removeDating">取消</a>
			</div><!--dated end-->
		</c:if>
	</div><!--user_infor end-->
	<c:choose>
		<c:when test="${profile.uid==context.uid}">
			<div class="user_btn"><!--user_btn begin--><a href="/profile/index">个人设置</a></div><!--user_btn end-->
		</c:when>
		<c:otherwise>
			<div class="ta_user_btn"><!--user_btn begin-->
				<div class="cancel_like" <c:if test="${!isInterest}">style="display:none;"</c:if>><p>已敲门</p><a href="javascript:void(0);" class="delete" uid="${profile.uid}"></a></div>
				<a href="javascript:void(0);" class="like" uid="${profile.uid}" <c:if test="${isInterest}">style="display:none;"</c:if>>敲门</a>	
				<a href="javascript:void(0);" class="date" <c:if test="${datingView!=null}">style="display:none;"</c:if> uid="${profile.uid}">约ta</a>
				<a href="javascript:void(0);" class="message_ta open-dialog" target-uid="${profile.uid}" target-nickname="<c:out value='${profile.nickname}'/>">私信</a>
			</div><!--user_btn end-->
		</c:otherwise>
	</c:choose>
</div><!--head_area end-->