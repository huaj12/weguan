<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- <%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="user_menu"><!--user_menu begin-->
	<a href="/home/acts" <c:if test="${tabType=='acts'}">class="active"</c:if>>我想去的(1254)</a>
	<a href="/home/interests/1" <c:if test="${tabType=='interests'}">class="active"</c:if>>我感兴趣的人(1254)</a>
	<a href="/home/datings/1" <c:if test="${tabType=='datings'}">class="active"</c:if>>我约的人(1254)</a>
	<a href="/home/interestMes/1" <c:if test="${tabType=='interestMes'}">class="active"</c:if>>对我感兴趣的人(1254)</a>
	<a href="/home/datingMes/1" <c:if test="${tabType=='datingMes'}">class="active"</c:if>>约我的人(1254)</a>
</div><!--user_menu end-->