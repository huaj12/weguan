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
			<h2>你的拒宅发布成功了</h2>
			<p>下面这些小宅正在等待被解救，问问他们要不要同去吧</p>
			<ul>
				<c:forEach var="profile" items="${profiles}">
					<li><a href="javascript:void(0);"><img src="${jzr:userLogo(profile.uid,profile.logoPic,80)}"  /></a></li>
					<input type="hidden" value="${profile.uid}" name="uids"/>
				</c:forEach>
			</ul>
			<div class="btns"><a href="javascript:void(0);" >解救他们</a><a href="javascript:void(0);" class="ws">残酷的无视</a></div>
		</div><!--send_suss end-->
	</c:when>
</c:choose>
