<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<select name="city" id="city">
							<c:forEach var="city" items="${citys}">
								<c:set var="s" value="${city.id}"></c:set>
								<option value="${city.id}">${city.name}</option>
							</c:forEach>
</select>
<c:if test="${fn:length(citys)==1}">
<input type="hidden" value="${s}" id="c_id"/>
</c:if>