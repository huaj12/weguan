<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<c:forEach var="categoryActView" items="${categoryActViewList}">
	<!-- 要判断是否变灰 -->
	<p <c:if test="${categoryActView.hasUsed}">class="none"</c:if> <c:if test="${!categoryActView.hasUsed}">onmouseover="javascript:hotActHover(this, true);" onmouseout="javascript:hotActHover(this, false);"</c:if>>
		<span class="l"></span>
		<a href="javascript:;" <c:choose><c:when test="${!categoryActView.hasUsed}">title="立即添加" onclick="javascript:addRecommendAct(this);"</c:when><c:otherwise>title="已添加"</c:otherwise></c:choose> actid="${categoryActView.act.id}"><c:out value="${categoryActView.act.name}" /></a>
		<span class="r"></span>
	</p>
</c:forEach>