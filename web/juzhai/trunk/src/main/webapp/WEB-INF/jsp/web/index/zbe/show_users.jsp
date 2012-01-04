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
		<title>找伴儿-拒宅网</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<c:if test="${setFreeDate!=null||setFreeDate}">
			<jsp:include page="/WEB-INF/jsp/web/common/set_free_date.jsp" />
		</c:if>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="back_top" style="display: none;"><!--back_top begin-->
					<p></p>
					<span><a href="#"></a></span>
					<p></p>
				</div><!--back_top end-->
				<div class="zbr"><!--zbr begin-->
					<div class="title"><!--title begin-->
						<h2 class="<c:choose><c:when test='${loginUser.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose>">
							<span><c:choose><c:when test="${loginUser!=null}"><c:out value="${loginUser.nickname}" /></c:when><c:otherwise>小宅</c:otherwise></c:choose></span>，这周末想怎么过？
						</h2>
						<div class="title_right"><!--title_right begin-->
							<div class="select"><!--select begin-->
								<span>
									<select name="language" id="language">
										<option value=""  selected="selected">有缘的人</option>
									</select>
								</span>
							</div><!--select end-->
							<div class="select" id="genderSelect"><!--select begin-->
								<span>
									<select id="language">
										<option value="all" <c:if test="${genderType=='all'}">selected="selected"</c:if>>性别不限</option>
										<option value="male" <c:if test="${genderType=='male'}">selected="selected"</c:if>>男性</option>
										<option value="female" <c:if test="${genderType=='female'}">selected="selected"</c:if>>女性</option>
									</select>
								</span>
							</div><!--select end-->
						</div><!--title_right end-->
					</div><!--title end-->
					<jsp:include page="show_users_list.jsp" />
				</div><!--zbr end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<script type="text/javascript" src="${jzr:static('/js/web/show_users.js')}"></script>
	</body>
</html>
