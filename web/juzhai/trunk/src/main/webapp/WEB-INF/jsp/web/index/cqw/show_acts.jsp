<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>无标题文档</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="back_top" style="display: none;"><!--back_top begin-->
					<p></p>
					<span><a href="#"></a></span>
					<p></p>
				</div><!--back_top end-->
				<div class="cqw"><!--cqw begin-->
					<div class="title"><!--title begin-->
						<h2 class="<c:choose><c:when test='${loginUser.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose>">
							<span>其实不想走</span>,这周末想怎么过？
						</h2>
						<div class="select"><!--select begin-->
							<span><select name="language" id="language" categoryid="${categoryId}">
								<option value="time" <c:if test="${orderType=='time'}">selected="selected"</c:if>>最新</option>
								<option value="pop" <c:if test="${orderType=='pop'}">selected="selected"</c:if>>流行度</option>
							</select></span>
						</div><!--select end-->
						<div class="title_right">
							<c:forEach var="category" items="${categoryList}">
								<a href="/showActs/${orderType}/${category.id}/1">${category.name}</a>
							</c:forEach>
						</div>
					</div><!--title end-->
					<jsp:include page="show_acts_list.jsp" />
				</div><!--cqw end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<script type="text/javascript" src="${jzr:static('/js/web/show_acts.js')}"></script>
	</body>
</html>
