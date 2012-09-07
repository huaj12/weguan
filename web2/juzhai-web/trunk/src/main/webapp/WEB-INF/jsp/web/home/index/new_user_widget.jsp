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
			<div class="right_title"><h2>新加入的小宅</h2><a href="/searchusers">更多</a></div>
			<div class="new_member"><!--new_member begin-->
				<ul>
					<c:forEach var="profile" items="${profileList}">
						<li><a href="/home/${profile.uid}"  title="<c:out value='${profile.nickname}' />"><img src="${jzr:userLogo(profile.uid, profile.logoPic, 80)}" width="50"  height="50"/></a></li>
					</c:forEach>
				</ul>
				<c:if test="${context.uid > 0 && loginUser.gender == 0}">
					<c:set var="qq" value="${jzd:qqGroup(loginUser.city)}" />
					<c:if test="${qq != null && qq != ''}">
						<div class="qqqun">
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${jzd:cityName(loginUser.city)}拒宅QQ群：${qq}</p>
						</div>
					</c:if>
				</c:if>
			</div><!--new_member end-->
		</div>
		<div class="t"></div>
</div><!--content end-->
</c:if>