<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<div class="message_box"><!--share_box2 begin-->
	<h2>给&nbsp;<c:out value="${targetNickname}" />&nbsp;发私信</h2>
	<div class="message_box_con"><!--message_box_con begin-->
		<div class="text_area"><textarea id="msg-dialog-textarea" name="content" cols="" rows=""></textarea></div>
		<div class="btn">
			<c:set var="inputDivId" value="msg-dialog-textarea" scope="request"/>
			<jsp:include page="/WEB-INF/jsp/web/common/face/face.jsp" />
			<a class="send" href="javascript:void(0);" target-uid="${targetUid}">发给ta</a>
			<a href="javascript:void(0);" class="sending" style="display: none;">发送中...</a>
			<b style="display:none;"></b>
		</div>
	</div><!--message_box_con end-->
</div><!--share_box2 end-->
