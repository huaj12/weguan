<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- <c:set var="freeDateList" value="${param.freeDateList}" /> --%>
<c:choose>
	<c:when test="${empty param.freeDateList}">
		还未标注空闲时间
	</c:when>
	<c:otherwise>
		<c:forEach var="freeDate" items="${param.freeDateList}" varStatus="status">
			<%-- <fmt:formatDate value="${freeDate}" pattern="yyyy-MM-dd"/> --%>
			@${status.index}@${freeDate}
		</c:forEach>
	</c:otherwise>
</c:choose>