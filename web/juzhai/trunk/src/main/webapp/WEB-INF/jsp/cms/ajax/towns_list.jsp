<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<select name="town" id="town">
							<c:forEach var="town" items="${towns}">
								<option value="${town.id}">${town.name}</option>
							</c:forEach>
</select>