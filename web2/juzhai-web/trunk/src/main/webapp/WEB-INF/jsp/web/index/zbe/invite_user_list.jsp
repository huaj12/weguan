<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="tpName" value="${jzd:tpName(context.tpId)}" />
<c:forEach var="inviteUser" items="${inviteUserList}" varStatus="status">
	<li <c:if test="${tpName == 'weibo'}">name="${inviteUser.name}"</c:if> <c:if test="${tpName == 'douban'}">uid="${inviteUser.userId}"</c:if> onclick="javascript:clickInviteUser(this);" onmouseover="javascript:mouseHover(this, true);" onmouseout="javascript:mouseHover(this, false);" class="<c:if test="${status.count%4==0}">mr0</c:if> <c:choose><c:when test='${inviteUser.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose>" title="点击添加">
		<p><img src="${inviteUser.logoUrl}" width="50" height="50"/></p>
		<span><a target="blank" href="${jzr:tpHomeUrl(inviteUser.userId, context.tpId)}"><c:out value="${inviteUser.name}" /></a></span>
		<em>${inviteUser.city}</em>
	</li>
</c:forEach>