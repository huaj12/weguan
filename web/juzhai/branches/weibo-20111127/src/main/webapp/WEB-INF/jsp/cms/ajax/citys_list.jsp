<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<select name="city">
							<c:forEach var="city" items="${citys}">
								<option value="${city.id}">${city.name}</option>
							</c:forEach>
</select>