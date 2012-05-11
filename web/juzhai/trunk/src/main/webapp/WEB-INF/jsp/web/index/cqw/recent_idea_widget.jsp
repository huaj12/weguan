<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${not empty recentIdeaViewList}">
	<div class="content_box w285"><!--content begin-->
		<div class="t"></div>
		<div class="m">
			<div class="right_title"><h2>最近大家想去的</h2></div>
			<div class="zjxq"><!--zjxq begin-->
				<ul>
					<c:forEach var="ideaView" items="${recentIdeaViewList}">
						<li>
							<p><a href="/idea/${ideaView.idea.id}"><c:out value="${ideaView.idea.content}" /></a><c:if test="${ideaView.profileCache != null}"><a href="/home/${ideaView.profileCache.uid}" class="from">&nbsp;<c:out value="${ideaView.profileCache.nickname}" /></a></c:if></p>
							<c:if test="${not empty ideaView.idea.pic}">
								<div class="img"><a href="/idea/${ideaView.idea.id}" ><img src="${jzr:ideaPic(ideaView.idea.id, ideaView.idea.pic, 200)}"/></a></div>
							</c:if>
							<c:if test="${ideaView.idea.useCount > 0}">
								<span><a href="/idea/${ideaView.idea.id}">${ideaView.idea.useCount}人想去</a></span>
							</c:if>
							<div class="btn idea-btn idea-btn-${ideaView.idea.id}"><a href="javascript:void(0);" class="wtgo" idea-id="${ideaView.idea.id}">我想去</a></div>
						</li>
					</c:forEach>
				</ul>
			</div><!--zjxq end-->
		</div>
		<div class="t"></div>
	</div><!--content end-->
	
</c:if>