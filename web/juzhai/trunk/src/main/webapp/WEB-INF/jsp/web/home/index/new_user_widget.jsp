<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${not empty profileList}">
	<div class="content_box w285"><!--content begin-->
		<div class="t"></div>
		<div class="m">
			<div class="right_title"><h2>新加入的小宅</h2><%-- <a href="/showusers">更多</a> --%></div>
			<div class="new_member"><!--new_member begin-->
				<ul>
					<c:forEach var="profile" items="${profileList}">
						<li><a href="/home/${profile.uid}" title="${profile.nickname}"><img src="${jzr:userLogo(profile.uid, profile.logoPic, 80)}" width="50"  height="50"/></a></li>
					</c:forEach>
				</ul>
				<c:if test="${context.tpName != 'qq'}">
					<div class="yq"><a href="/showInviteUsers">邀请好友</a></div>
				</c:if>
			</div><!--new_member end-->
		</div>
		<div class="t"></div>
</div><!--content end-->
</c:if>