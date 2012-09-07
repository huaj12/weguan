<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- <%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="ta_menu"><!--user_menu begin-->
	<a href="javascript:void(0);" <c:if test="${tabType=='acts'}">class="active"</c:if>>ta想去的(${actCount})</a>
</div><!--user_menu end-->