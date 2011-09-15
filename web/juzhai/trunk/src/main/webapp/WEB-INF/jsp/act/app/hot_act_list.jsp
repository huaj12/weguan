<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<c:forEach var="act" items="${hotActList}">
	<p class="act_hot"><span class="fl"></span><a href="#" title="立即添加"><c:out value="${act.name}" /></a><span class="fr"></span></p>
	<!-- <p><span class="fl"></span><a href="#" title="立即添加">看变形金刚3</a><span class="fr"></span></p> -->
</c:forEach>