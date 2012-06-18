<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="message_list">
	<c:choose>
		<c:when test="${empty dialogViewList}">
			<div class="none">目前你还没有收到私信哦</div>
		</c:when>
		<c:otherwise>
			<c:forEach var="dialogView" items="${dialogViewList}">
				<c:set var="flag" value="${context.uid==dialogView.dialogContent.receiverUid}"></c:set>
				<div class="list_item mouseHover <c:choose><c:when test='${dialogView.targetProfile.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose> <c:choose><c:when test='${context.uid==dialogView.dialogContent.receiverUid}'>m_s</c:when><c:otherwise>m_r</c:otherwise></c:choose>">
					<div class="item_t"></div>
					<div class="item_m">
						<div class="icon"><span><em></em></span></div>
						<div class="message_item">
							<div class="btn">
								<c:if test="${flag}">
									<c:choose>
										<c:when test="${not empty dialogView.shield && dialogView.shield }">
											<a href="javascript:void(0);"  class="done">已屏蔽</a>
										</c:when>
										<c:otherwise>
											<a href="javascript:void(0);" target-uid="${dialogView.targetProfile.uid}" target-name="<c:out value='${dialogView.targetProfile.nickname}'></c:out>" class="pingbi" title="被你屏蔽的人将不能再给你发送任何消息">屏蔽ta</a>								
										</c:otherwise> 
									</c:choose>
									<a href="javascript:void(0);" target-content="${dialogView.dialogContent.content}" target-uid="${dialogView.targetProfile.uid}" class="jubao">举报</a>
								</c:if>
								<a href="javascript:void(0);" class="del-dialog-btn"  dialog-id="${dialogView.dialog.id}" target-name="<c:out value='${dialogView.targetProfile.nickname}'></c:out>">删除</a>
							</div>
							<div></div>
							<div class="photo"><a href="/home/${dialogView.targetProfile.uid}"><img src="${jzr:userLogo(dialogView.targetProfile.uid,dialogView.targetProfile.logoPic,80)}"  width="80" height="80"/></a></div>
							<c:set var="age" value="${jzu:age(dialogView.targetProfile.birthYear, dialogView.targetProfile.birthSecret)}" />
							<c:set var="constellationName" value="${jzd:constellationName(dialogView.targetProfile.constellationId)}" />
							<div class="information"><a href="/home/${dialogView.targetProfile.uid}"><c:out value="${dialogView.targetProfile.nickname}" /></a><span><c:if test="${age > 0}">${age}岁&nbsp;</c:if><c:if test="${dialogView.targetProfile.city != null && dialogView.targetProfile.city > 0}">${jzd:cityName(dialogView.targetProfile.city)}<c:if test="${dialogView.targetProfile.town != null && dialogView.targetProfile.town > 0}">${jzd:townName(dialogView.targetProfile.town)}</c:if>&nbsp;</c:if><c:if test="${not empty constellationName}">${constellationName}&nbsp;</c:if><c:if test="${not empty dialogView.targetProfile.profession}">${dialogView.targetProfile.profession}</c:if></span></div>
							<div class="msg_content">
								<span>
									<c:choose>
										<c:when test="${flag}">
										<h3>ta发给我</h3>
									</c:when>
									<c:otherwise>
										<h4>我发给ta</h4>
									</c:otherwise>
									</c:choose>
									<strong><c:set var="date" value="${dialogView.dialogContent.createTime}" scope="request" /><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" /></strong>
								</span>
								<p>“${jzu:convertFace(dialogView.dialogContent.content)}”</p>
								<div class="repy_btn"><a href="javascript:void(0);" class="repy" target-name="${dialogView.targetProfile.nickname}" target-uid="${dialogView.targetProfile.uid}">回复</a></div>
								<em><a href="/home/dialogContent/${dialogView.targetProfile.uid}/1">共<font>${dialogView.dialogContentCnt}</font>条对话</a></em>
							</div>
						</div>
					</div>
					<div class="item_t"></div>
				</div>
			</c:forEach>
			<c:if test="${pager.totalPage > 1}">
				<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
					<c:param name="pager" value="${pager}"/>
					<c:param name="url" value="/home/dialog" />
				</c:import>
			</c:if>
		</c:otherwise>
	</c:choose>
</div><!--message_list end-->