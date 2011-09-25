<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach var="tip" items="${tips}" varStatus="status">
	<div class="pro_box tips_${status.count}" <c:if test="${status.first != true}">style="display:none;"</c:if>>
		<p><c:out value="${tip}" /></p>
	</div>
</c:forEach>