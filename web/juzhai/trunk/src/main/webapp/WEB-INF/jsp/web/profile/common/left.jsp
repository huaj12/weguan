<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="set_left"><!--set_left begin-->
	<a href="/profile/index" <c:if test="${page=='index'}">class="active"</c:if> >我的资料</a> 
	<a href="/profile/index/face" <c:if test="${page=='face'}">class="active"</c:if> >我的头像</a>
	<a href="/profile/preference" <c:if test="${page=='preference'}">class="active"</c:if> >拒宅偏好</a>
	<a href="/passport/account" <c:if test="${page=='account'}">class="active"</c:if> >账号密码</a>
	<a href="/home/blacklist" <c:if test="${page=='shield'}">class="active"</c:if> >屏蔽管理</a>
	<c:if test="${empty isQplus||!isQplus}">
		<c:if test="${context.tpId>0}">
			<a href="/show/authorize" <c:if test="${page=='authorize'}">class="active"</c:if> >授权管理</a>
		</c:if>
	</c:if>
	<%-- <a href="/profile/email" <c:if test="${page=='mail'}">class="active"</c:if>>订阅设置</a> --%>
</div>