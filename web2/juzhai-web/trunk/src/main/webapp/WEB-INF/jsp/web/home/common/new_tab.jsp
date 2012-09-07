<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="title"><!--title begin-->
	<div class="category"><!--category begin-->
		<span <c:if test="${tabType=='posts'}">class="act"</c:if>><p></p><a href="/home/posts">我的拒宅</a><p></p></span>
		<span <c:if test="${tabType=='interests'}">class="act"</c:if>><p></p><a href="/home/interests">我关注的人</a><p></p></span>
		<span <c:if test="${tabType=='interestMes'}">class="act"</c:if>><p></p><a href="/home/interestMes">我的粉丝</a><p></p></span>
		<span <c:if test="${tabType=='interestIdea'}">class="act"</c:if>><p></p><a href="/home/interestIdea">我的收藏</a><p></p></span>
		<span <c:if test="${tabType=='preference'}">class="act"</c:if>><p></p><a href="/home/preference">拒宅偏好</a><p></p></span>
		<span <c:if test="${tabType=='visitors'}">class="act"</c:if>><p></p><a href="/home/visitors">来访者</a><p></p></span>
	</div><!--category end-->
</div><!--title end-->