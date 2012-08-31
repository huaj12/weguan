<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
		<div class="map_show">
				<div class="title"><h2><c:out value="${idea.content}"></c:out></h2></div>
				<div class="clear"></div>
				<div class="map_area" id="big-map-container" town-name="${jzd:townName(idea.town)}" city-name="${jzd:cityName(idea.city)}" place-name="<c:out value="${idea.place}"/>" lat="${point.lat}" lng="${point.lng}"></div>
		</div>
<!--map_tcc end-->
