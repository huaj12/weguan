<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
	<c:when test="${dialogContentView!=null}">
		<div <c:if test="${pager==null}">style="display:none;"</c:if> class="repy_list_item <c:choose><c:when test='${dialogContentView.dialogContent.senderUid==context.uid}'>me</c:when><c:otherwise>ta</c:otherwise></c:choose>"><!--repy_list_item begin-->
			<div class="photo"><a href="/home/${dialogContentView.profile.uid}"><img src="${jzr:userLogo(dialogContentView.profile.uid,dialogContentView.profile.logoPic,80)}"  width="80" height="80"/></a></div>
			<div class="repy_box mouseHover"><!--repy_box begin-->
				<div class="arrow"></div>
				<div></div>
				<p></p>
				<span>
					<c:set value="${dialogContentView.dialogContent.senderUid==context.uid}" var="isMe"></c:set>
					<h3><c:choose><c:when test="${isMe}">我:</c:when><c:otherwise><a href="/home/${dialogContentView.profile.uid}"><c:out value="${dialogContentView.profile.nickname}" />:</a></c:otherwise></c:choose></h3>
					<c:if test="${!empty dialogContentView.dialogContent.pic}"><div class="from_phone">我刚才通过拒宅手机版给你发了一张图片:</div></c:if>
					<em>“${jzu:convertFace(dialogContentView.dialogContent.content)}”</em>
					<c:if test="${!empty dialogContentView.dialogContent.pic}"><div class="pic"><img src=" ${jzr:dialogContentPic(dialogContentView.dialogContent.id,dialogContentView.dialogContent.pic,200)}"></div></c:if>
					<b><c:set var="date" value="${dialogContentView.dialogContent.createTime}" scope="request" /><jsp:include page="/WEB-INF/jsp/web/common/fragment/show_time.jsp" /></b>
					<div class="btn">
						<c:if test="${!isMe }">
							<c:choose>
								<c:when test="${not empty isShield &&isShield }">
									<a href="javascript:void(0);"  class="done">已屏蔽</a>
								</c:when>
								<c:otherwise>
									<a href="javascript:void(0);" target-uid="${targetProfile.uid}" class="dialog-pinbi" target-name="<c:out value='${targetProfile.nickname}'></c:out>"  title="被你屏蔽的人将不能再给你发送任何消息">屏蔽ta</a>
								</c:otherwise>
							</c:choose>
							<a href="javascript:void(0);" class="dialog-report" dialog-content="<c:out value="${dialogContentView.dialogContent.content}" />" target-uid="${targetProfile.uid}">举报</a>
						</c:if>
						<a href="javascript:void(0);" class="del-btn" dialog-content-id="${dialogContentView.dialogContent.id}" target-uid="${targetProfile.uid}">删除</a>
						<a href="javascript:void(0);" class="repy-btn">回复</a>
					</div>
				</span>
				<p></p>
			</div><!--repy_box end-->
		</div><!--repy_list_item end-->
		<c:if test="${pager==null}">
			<script type="text/javascript">
				bindMouseHover();
				bindReply();
				bindDelDialogContent();
			</script>
		</c:if>
	</c:when>
	<c:otherwise>
		{'success':${success},'errorCode':'${errorCode}','errorInfo':'${errorInfo}','result':''}
	</c:otherwise>
</c:choose>