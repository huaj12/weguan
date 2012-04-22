<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<select name="city" id="city" onchange="selectCity(this)">
	<option value="0">请选择</option>
	<c:forEach var="city" items="${citys}" varStatus="step">
		<c:if test="${step.index==0}">
			<c:set var="s" value="${city.id}"></c:set>
		</c:if>
		<option value="${city.id}">${city.name}</option>
	</c:forEach>
</select>
<input type="hidden" value="${s}" id="c_id" />
