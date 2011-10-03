<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<c:forEach var="hotActView" items="${hotActViewList}">
	<!-- 要判断是否变灰 -->
	<p <c:if test="${hotActView.hasUsed}">class="none"</c:if> <c:if test="${!hotActView.hasUsed}">onmouseover="javascript:hotActHover(this, true);" onmouseout="javascript:hotActHover(this, false);"</c:if>>
		<span class="fl"></span>
		<a href="#" <c:choose><c:when test="${!hotActView.hasUsed}">title="立即添加" onclick="javascript:addRecommendAct(this);"</c:when><c:otherwise>title="已添加"</c:otherwise></c:choose> actid="${hotActView.act.id}"><c:out value="${hotActView.act.name}" /></a>
		<span class="fr"></span>
	</p>
</c:forEach>