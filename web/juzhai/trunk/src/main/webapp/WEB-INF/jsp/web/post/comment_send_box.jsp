<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form id="comment-form" onsubmit="javascript:return false;">
	<c:if test="${parentId == null}">
		<c:set var="parentId" value="0" />
	</c:if>
	<div class="repy_area_s2"><!--repy_area_s2 begin-->
		<input type="hidden" name="postId" value="${postId}" />
		<input type="hidden" name="parentId" value="${parentId}" />
		<div class="repy_for" style="display:none"><span>回复<font class="reply-nickname"></font>的“<font class="reply-content"></font>”</span><a href="javascript:void(0);"></a></div>
		<div class="input"><!--input begin-->
			<p class="l"></p><span class="w450"><input id="comment-input-${postId}" name="content" type="text" /></span><p class="r"></p>
		</div><!--input end-->
		<div class="btns"><!--btns begin-->
			<c:set var="inputDivId" value="comment-input-${postId}" scope="request"/>
			<jsp:include page="/WEB-INF/jsp/web/common/face/face.jsp" />
			<div class="error" style="display: none;"></div>
			<!-- <div class="repy_btn"><a href="javascript:void(0);" class="done" style="display:none;">发布中</a></div> -->
			<div class="repy_btn"><a href="javascript:void(0);">发布留言</a></div>
		</div><!--btns end-->
		<div class="clear"></div>
	</div><!--repy_area_s2 end-->
</form>