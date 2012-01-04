<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<table>
	<c:forEach var="act" items="${acts}">
	<tr><td>${act.name}  <a href="#" onclick="removeAd('${rawId}','${act.id}')">取消</a></td></tr>
	</c:forEach>
</table>