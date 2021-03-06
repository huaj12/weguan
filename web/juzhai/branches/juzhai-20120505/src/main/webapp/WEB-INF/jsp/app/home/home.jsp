<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>拒宅器</title>
		<c:set var="cssFile" value="/css/jz_${context.tpName}.css" />
		<link href="${jz:static(cssFile)}" rel="stylesheet" type="text/css" />
		<link href="${jz:static('/css/jquery.autocomplete.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<jsp:include page="/WEB-INF/jsp/app/common/${context.tpName}/top_logo.jsp" />
		<div class="main"><!--main begin-->
			<c:set var="page" value="home" scope="request"/>
			<jsp:include page="/WEB-INF/jsp/app/common/app_header.jsp" />
			<div class="skin_body"><!--skin_body begin-->
				<div class="skin_top_bg"><!--content_bg begin-->
					<div class="content white" style="display:none;"><!--content begin-->
					</div><!--content end-->
					<div class="loading_home"><!--loading_home begin-->
						<div class="top"></div>
						<div class="mid"><!--mid begin-->
							<span><img src="${jz:static('/images/loading.gif')}" /></span><p>正在加载....</p>
						</div><!--mid end-->
						<div class="bot"></div>
					</div><!--loading_home end-->
					<c:choose>
						<c:when test="${context.tpName == 'kaixin001'}">
							<div class="homeAdvise check_box tz <c:choose><c:when test="${isAdvise==null||isAdvise}">tz_secleted</c:when><c:otherwise>tz_link</c:otherwise></c:choose>">
								<p></p>
								<span>将我想去的分享给好友</span>
							</div>
						</c:when>
						<%-- <c:when test="${context.tpName == 'renren'}">
							<div class="homeAdvise check_box tz tz_secleted" style="display: none;" />
						</c:when> --%>
					</c:choose>
				</div><!--content_bg end-->
			</div><!--skin_body end-->
			<div class="skin_bottom"></div>
		</div><!--main end-->
		<jsp:include page="/WEB-INF/jsp/app/common/script/script.jsp" />
		<jsp:include page="/WEB-INF/jsp/app/common/send_feed.jsp" />
		<script type="text/javascript" src="${jz:static('/js/module/home.js')}"></script>
		<jsp:include page="/WEB-INF/jsp/app/common/script/app.jsp" />
		<jsp:include page="/WEB-INF/jsp/app/common/foot.jsp" />
	</body>
</html>