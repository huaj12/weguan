<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="set_left"><!--set_left begin-->
	<a href="/profile/index" <c:if test="${page=='index'}">class="active"</c:if> >我的资料</a> 
	<a href="/profile/index/face" <c:if test="${page=='face'}">class="active"</c:if> >个人形象</a>
	<a href="/profile/email" <c:if test="${page=='mail'}">class="active"</c:if>>拒宅通知</a>
</div>