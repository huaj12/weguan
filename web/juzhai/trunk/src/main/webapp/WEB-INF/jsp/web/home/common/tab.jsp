<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- <%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="user_menu"><!--user_menu begin-->
	<a href="#" <c:if test="${tabType=='acts'}">class="active"</c:if>>我想去的(1254)</a>
	<a href="#" <c:if test="${tabType=='interest'}">class="active"</c:if>>我感兴趣的人(1254)</a>
	<a href="#" <c:if test="${tabType=='dating'}">class="active"</c:if>>我约的人(1254)</a>
	<a href="#" <c:if test="${tabType=='interestMe'}">class="active"</c:if>>对我感兴趣的人(1254)</a>
	<a href="#" <c:if test="${tabType=='dateMe'}">class="active"</c:if>>约我的人(1254)</a>
</div><!--user_menu end-->