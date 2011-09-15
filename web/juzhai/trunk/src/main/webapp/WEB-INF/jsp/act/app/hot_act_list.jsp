<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<c:forEach var="hotActView" items="${hotActViewList}">
	<!-- 要判断是否变灰 -->
	<p onmouseover="javascript:hotActMouseOver(true);" onmouseout="javascript:hotActMouseOver(false);">
		<span class="fl"></span>
		<a href="#" title="立即添加" onclick="javascript:addAct();" actId="${hotActView.act.id}"><c:out value="${hotActView.act.name}" /></a>
		<span class="fr"></span>
	</p>
</c:forEach>