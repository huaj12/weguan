<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content_box w285"><!--content begin-->
	<div class="t"></div>
	<div class="m">
		<div class="right_title"><h2>拒宅好主意</h2><a href="/showIdeas">更多</a></div>
		<div class="idea"><!--idea begin-->
			<ul>
				<c:forEach var="ideaView" items="${ideaViewList}">
					<li class="mouseHover">
						<p><c:out value="${ideaView.idea.content}" /></p>
						<c:if test="${not empty ideaView.idea.pic}">
							<div class="img"><img data-original="${jzr:ideaPic(ideaView.idea.id, ideaView.idea.pic)}" src="${jzr:static('/images/web/1px.gif')}" width="220"/></div>
						</c:if>
						<span><a href="#" id="useCount-${ideaView.idea.id}">${ideaView.idea.useCount}</a>人想去</span>
						<c:choose>
							<c:when test="${ideaView.hasUsed}">
								<div class="sended"><a href="javascript:void(0);">已发布</a></div>
							</c:when>
							<c:otherwise>
								<div class="sending" style="display: none;"><a href="javascript:void(0);">发布中..</a></div>
								<div class="send"><a href="javascript:void(0);" idea-id="${ideaView.idea.id}">发布拒宅</a></div>
							</c:otherwise>
						</c:choose>
					</li>
				</c:forEach>
			</ul>
		</div><!--idea end-->
	</div>
	<div class="t"></div>
</div><!--content end-->