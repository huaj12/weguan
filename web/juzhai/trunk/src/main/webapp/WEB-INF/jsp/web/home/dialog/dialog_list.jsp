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
				<div class="list_item mouseHover <c:choose><c:when test='${dialogView.targetProfile.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose> <c:choose><c:when test='${context.uid==dialogView.dialogContent.receiverUid}'>m_s</c:when><c:otherwise>m_r</c:otherwise></c:choose>">
					<div class="item_t"></div>
					<div class="item_m">
						<div class="icon"><span><em></em></span></div>
						<div class="message_item">
							<div class="btn"><a href="javascript:void(0);" class="delete" dialog-id="${dialogView.dialog.id}" target-name="${dialogView.targetProfile.nickname}">删除</a><a href="javascript:void(0);" class="repy" target-name="${dialogView.targetProfile.nickname}" target-uid="${dialogView.targetProfile.uid}">回复</a></div>
							<div></div>
							<div class="photo"><a href="/home/${dialogView.targetProfile.uid}"><img src="${jzr:userLogo(dialogView.targetProfile.uid,dialogView.targetProfile.logoPic,80)}"  width="80" height="80"/></a></div>
							<div class="msg_content">
								<span>
									<c:choose>
										<c:when test="${context.uid==dialogView.dialogContent.receiverUid}">
										<a href="/home/${dialogView.targetProfile.uid}"><c:out value="${dialogView.targetProfile.nickname}" /></a>
										<h3>发给我</h3>
									</c:when>
									<c:otherwise>
										<h4>我发给</h4>
										<a href="/home/${dialogView.targetProfile.uid}"><c:out value="${dialogView.targetProfile.nickname}" /></a>
									</c:otherwise>
									</c:choose>
									<strong><c:set var="date" value="${dialogView.dialogContent.createTime}" scope="request" /><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" /></strong>
								</span>
								<p><c:out value="${dialogView.dialogContent.content}" /></p>
								<em><a href="/home/dialogContent/${dialogView.targetProfile.uid}/1">共<font>${dialogView.dialogContentCnt}</font>条对话</a></em>
							</div>
						</div>
					</div>
					<div class="item_t"></div>
				</div>
			</c:forEach>
			<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
				<c:param name="pager" value="${pager}"/>
				<c:param name="url" value="/home/dialog" />
			</c:import>
		</c:otherwise>
	</c:choose>
</div><!--message_list end-->