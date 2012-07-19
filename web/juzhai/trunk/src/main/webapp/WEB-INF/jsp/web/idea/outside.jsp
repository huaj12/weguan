<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${jzd:cityName(idea.city )}拒宅好主意_<c:out value="${jzu:truncate(idea.content, 60, '...')}" /> 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
		<meta name="keywords" content="${jzd:cityName(idea.city )}拒宅 ,${jzd:cityName(idea.city)}拒宅好主意,${jzd:cityName(idea.city )}出去玩" />
		<meta name="description" content="<c:if test="${!empty jzd:cityName(idea.city )}">在jzd:cityName(idea.city )}</c:if>周末不想宅在家拒宅网帮你出好主意,<c:out value="${jzu:truncate(idea.content, 120, '...')}" />_" />
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
		<style type="text/css">html, body{overflow: hidden;}</style>
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main_iframe"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="main_iframe_part" idea-link="${idea.link}"><!--main_part begin-->
				</div><!--main_part end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/idea_outside.js')}"></script>
		</div><!--warp end-->
		<jsp:include page="/WEB-INF/jsp/web/common/count.jsp"/>
	</body>
</html>
