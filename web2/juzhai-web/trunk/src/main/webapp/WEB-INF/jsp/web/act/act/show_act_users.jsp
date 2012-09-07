<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="keywords" content="<c:out value="${act.name}" /> 详细介绍 图片 优惠价 找伴儿同去" />
		<meta name="description" content="<c:out value="${act.name}" />，查看最近也想去<c:out value="${act.name}" />的人，找伴儿一起去<c:out value="${act.name}" />，查看<c:out value="${act.name}" />的详细介绍" />
		<title><c:out value="${act.name}" />-拒宅网</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="content"><!--content begin-->
					<div class="t"></div>
					<div class="m"><!--" begin-->
						<div class="project"><!--project begin-->
							<div class="p_left"><!--p_left begin-->
								<c:set var="menuType" value="users" scope="request" />
								<jsp:include page="act_header.jsp" />
								<jsp:include page="act_users_list.jsp" />
							</div><!--p_left end-->
							<jsp:include page="act_right.jsp" />
						</div><!--project end-->
					</div><!--" end-->
					<div class="b"></div>
				</div><!--content end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/act_header.js')}"></script>
			<script type="text/javascript" src="${jzr:static('/js/web/act_users.js')}"></script>
			<script type="text/javascript" src="http://v2.jiathis.com/code_mini/jia.js" charset="utf-8"></script>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
