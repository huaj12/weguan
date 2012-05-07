<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
	<c:when test="${empty interestUserViewList}">
		<div class="my_fav"><!--my_fav begin-->
			<div class="none">
				<c:choose>
					<c:when test="${tabType=='interests'}">没有人被你收藏过</c:when>
					<c:otherwise>还没人收藏过你</c:otherwise>
				</c:choose>
			</div>
		</div><!--my_fav end-->
	</c:when>
	<c:otherwise>
		
		<div class="clear"></div>
		<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
			<c:param name="pager" value="${pager}"/>
			<c:param name="url" value="/home/${tabType}" />
		</c:import>
	</c:otherwise>
</c:choose>