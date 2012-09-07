<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
	<c:if test="${not empty point&&point.hasValid}">
		<div class="t"></div>
		<div class="m h250">
			<div class="map" id="container" town-name="${jzd:townName(idea.town)}" city-name="${jzd:cityName(idea.city)}" place-name="<c:out value="${idea.place}"/>" lat="${point.lat}" lng="${point.lng}"></div>
			<div class="clear"></div>
			<div class="view_map"><a href="javascript:void(0);" idea-id="${idea.id}">参看完整大图</a></div>
		</div>
		<div class="clear"></div>
		<div class="clear"></div>
		<div class="t"></div>
	</c:if>	