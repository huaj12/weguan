<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="repy_list_item <c:choose><c:when test='${dialogContentView.dialogContent.senderUid==context.uid}'>me</c:when><c:otherwise>ta</c:otherwise></c:choose>"><!--repy_list_item begin-->
	<div class="photo"><img src="${jzr:userLogo(dialogContentView.profile.uid,dialogContentView.profile.logoPic,80)}"  width="80" height="80"/></div>
	<div class="repy_box mouseHover"><!--repy_box begin-->
		<div class="arrow"></div>
		<div></div>
		<p></p>
		<span>
			<h3><c:choose><c:when test="${dialogContentView.dialogContent.senderUid==context.uid}">我:</c:when><c:otherwise><a href="/home/${dialogContentView.profile.uid}"><c:out value="${dialogContentView.profile.nickname}" />:</a></c:otherwise></c:choose></h3>
			<em><c:out value="${dialogContentView.dialogContent.content}" /></em>
			<b><c:set var="date" value="${dialogContentView.dialogContent.createTime}" scope="request" /><jsp:include page="/WEB-INF/jsp/web/common/fragment/show_time.jsp" /></b>
			<div class="btn"><a href="javascript:void(0);" id="del-btn" dialog-content-id="${dialogContentView.dialogContent.id}" target-uid="${targetProfile.uid}">删除</a><a href="javascript:void(0);" id="repy-btn">回复</a></div>
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