<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach var="tip" items="${tips}" varStatus="status">
	<c:set var="hasTip" value="true" />
	<div class="pro_box tips_${status.count}" <c:if test="${status.first != true}">style="display:none;"</c:if>>
		<p><c:out value="${tip}" /></p>
	</div>
</c:forEach>
<c:if test="${!hasTip}">
	<div class="pro_box tips_1" style="display:none;">
		<p></p>
	</div>
</c:if>