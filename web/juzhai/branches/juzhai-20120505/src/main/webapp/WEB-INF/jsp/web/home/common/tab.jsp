<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="user_menu"><!--user_menu begin-->
	<a href="/home/acts" <c:if test="${tabType=='acts'}">class="active"</c:if>>我想去的(${actCount})</a>
	<a href="/home/interests/1" <c:if test="${tabType=='interests'}">class="active"</c:if>>我敲过门的人(${interestCount})</a>
	<a href="/home/datings/1" <c:if test="${tabType=='datings'}">class="active"</c:if>>我约的人(${datingCount})</a>
	<a href="/home/interestMes/1" <c:if test="${tabType=='interestMes'}">class="active"</c:if>>敲过我门的人(${interestMeCount})</a>
	<a href="/home/datingMes/1" <c:if test="${tabType=='datingMes'}">class="active"</c:if>>约我的人(${datingMeCount})</a>
</div><!--user_menu end-->