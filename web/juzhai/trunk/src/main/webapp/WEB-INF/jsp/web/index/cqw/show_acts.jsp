<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="keywords" content="拒宅 找伴 出去玩 约会 交友" />
		<meta name="description" content="不想宅在家，找伴儿，出去玩，发现出去玩的好主意和同兴趣的朋友，促成约会" />
		<title>出去玩-拒宅网</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
		
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<jsp:include page="/WEB-INF/jsp/web/common/back_top.jsp" />
				<div class="cqw"><!--cqw begin-->
					<div class="title"><!--title begin-->
						<h2 class="<c:choose><c:when test='${loginUser.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose>">
							<span><c:choose><c:when test="${loginUser!=null}"><c:out value="${loginUser.nickname}" /></c:when><c:otherwise>小宅</c:otherwise></c:choose></span>，最近想出去玩什么？
						</h2>
						<div class="select"><!--select begin-->
							<span><select name="language" id="language" categoryid="${categoryId}">
								<option value="time" <c:if test="${orderType=='time'}">selected="selected"</c:if>>最新</option>
								<option value="pop" <c:if test="${orderType=='pop'}">selected="selected"</c:if>>最热</option>
							</select></span>
						</div><!--select end-->
						<div class="title_right">
							<span <c:if test="${categoryId==0}">class="active"</c:if>><p class="fr"></p><a href="/showActs/${orderType}_0/1">全部</a><p class="fl"></p></span>
							<c:forEach var="category" items="${categoryList}">
								<span <c:if test="${categoryId==category.id}">class="active"</c:if>><p class="fr"></p><a href="/showActs/${orderType}_${category.id}/1">${category.name}</a><p class="fl"></p></span>
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
