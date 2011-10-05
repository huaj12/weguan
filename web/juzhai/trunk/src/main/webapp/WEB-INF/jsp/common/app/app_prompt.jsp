<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pro_box">
	<c:forEach var="tip" items="${tips}" varStatus="status">
		<c:set var="hasTip" value="true" />
		<p class="tips_${status.count}" <c:if test="${status.first != true}">style="display:none;"</c:if>><c:out value="${tip}" /></p>
	</c:forEach>
	<c:if test="${!hasTip}">
			<p class="tips_1" style="display:none;"></p>
	</c:if>
</div>