<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
	<c:when test="${ideaView != null}">
		<ul>
			<li>
				<p><a href="/idea/${ideaView.idea.id}" <c:if test="${empty isQplus || !isQplus}">target="_blank"</c:if>><c:out value="${ideaView.idea.content}" /></a></p>
				<c:if test="${not empty ideaView.idea.pic}">
					<div class="img"><a href="/idea/${ideaView.idea.id}"><img src="${jzr:ideaPic(ideaView.idea.id, ideaView.idea.pic, 200)}"/></a></div>
				</c:if>
				<c:if test="${ideaView.idea.useCount > 0}">
					<span><a href="/idea/${ideaView.idea.id}">${ideaView.idea.useCount}人想去</a></span>
				</c:if>
				<div class="btn"><a href="javascript:void(0);" class="wtgo" idea-id="${ideaView.idea.id}">我想去</a><a href="javascript:void(0);" class="hyge">换一个</a></div>
				<div class="clean"></div><div class="clean"></div>
			</li>
			
		</ul>
	</c:when>
	<c:otherwise>
		<div class="none_jz">没有了，改天再来看看</div>	
	</c:otherwise>
</c:choose>