<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:choose>
	<c:when test="${not empty profiles&&fn:length(profiles)>0}">
		<div class="send_suss"><!--send_suss begin-->
			<h2>拒宅发布成功了，再邀请一些宅女同去吧</h2>
			<ul>
				<c:forEach var="profile" items="${profiles}">
					<li><a href="javascript:void(0);"><img src="${jzr:userLogo(profile.uid,profile.logoPic,80)}"  width="50" height="50" /></a></li>
					<input type="hidden" value="${profile.uid}" name="uids"/>
				</c:forEach>
			</ul>
			<div class="btns"><a href="javascript:void(0);" >邀请她们</a><a href="javascript:void(0);" class="ws">残酷的无视</a></div>
		</div><!--send_suss end-->
	</c:when>
</c:choose>
