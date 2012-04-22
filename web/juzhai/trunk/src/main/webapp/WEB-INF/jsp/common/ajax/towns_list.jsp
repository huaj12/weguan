<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${!empty towns}">
	<select name="town" id="town">
								<option value="-1">请选择</option>	
								<c:forEach var="town" items="${towns}">
									<option value="${town.id}">${town.name}</option>
								</c:forEach>
								<option value="0">其他</option>	
	</select>
</c:if>