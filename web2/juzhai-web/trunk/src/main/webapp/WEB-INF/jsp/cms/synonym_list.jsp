<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table>
	<tr >
		<td colspan="2"><c:out value="${act.name}" /></td>
	</tr>
	<tr>
		<td>可能的近义词：</td>
		<td>
			<c:forEach var="maybeSynonym" varStatus="status" items="${maybeSynonymList}">
				<a href="#add" onclick="javascript:useMaybeSynonym('${maybeSynonym}');">${maybeSynonym}</a><c:if test="${!status.last}">,</c:if>
			</c:forEach>
		</td>
	</tr>
</table>
<table>
	<tr>
		<td>名称</td>
		<td>操作</td>
	</tr>
	<c:forEach var="synonymAct" items="${synonymActList}">
		<tr>
			<td>${synonymAct.name}</td>
			<td><a href="javascript:;" onclick="javascript:removeSynonym('${synonymAct.id}', '${act.id}');">删除</a></td>
		</tr>
	</c:forEach>
	<tr>
		<a name="actName"></a>
		<td><input type="text" id="addInput" /></td>
		<td><a href="javascript:;" onclick="javascript:addSynonym('${act.id}');">添加</a></td>
	</tr>
</table>