<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content w280"><!--content begin-->
	<div class="t"></div>
	<div class="m">
		<div class="right_title"><h2>拒宅好主意</h2><a href="/showIdeas">更多</a></div>
		<div class="idea"><!--idea begin-->
			<ul>
				<c:forEach var="ideaView" items="${ideaViewList}">
					<li class="mouseHover">
						<p><c:out value="${ideaView.idea.content}" /></p>
						<c:if test="${not empty ideaView.idea.pic}">
							<div class="img"><img src="${jzr:ideaPic(ideaView.idea.id, ideaView.idea.pic)}" width="220"/></div>
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
<div class="content w280"><!--content begin-->
	<div class="t"></div>
	<div class="m">
		<div class="right_title"><h2>新加入的小宅</h2><a href="#">更多</a></div>
		<div class="new_member"><!--new_member begin-->
			<ul>
				<c:forEach var="profile" items="${profileList}">
					<li><a href="/home/${profile.uid}" title="${profile.nickname}"><img src="${jzr:userLogo(profile.uid, profile.logoPic, 80)}" width="50"  height="50"/></a></li>
				</c:forEach>
			</ul>
			<div class="yq"><a href="#">邀请好友</a></div>
		</div><!--new_member end-->
	</div>
	<div class="t"></div>
</div><!--content end-->
<!-- <div class="content w280">content begin
	<div class="t"></div>
	<div class="m">
		<div class="right_title"><h2>拒宅小贴士</h2></div>
		<div class="xts">xts begin
			<p>"想要更加了解一个人，不如去看看ta的微博哦 ”</p>
			<a href="#">给我们留言</a>
		</div>xts end
	</div>
	<div class="t"></div>
</div>content end -->