<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="jzr"
	uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="拒宅 ,找伴儿, 出去玩, 约会 ,交友" />
<meta name="description" content="不想宅在家,找伴儿,出去玩,发现出去玩的好主意和同兴趣的朋友,促成约会" />
<title>帐号被锁定-拒宅网</title>
<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<div class="warp">
		<!--warp begin-->
		<div class="main">
			<!--main begin-->
			<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
			<div class="content">
				<!--content begin-->
				<div class="t"></div>
				<div class="m">
					<!--m begin-->
					<div class="bfh">
						<!--bfh begin-->
						<p></p>
						<span class="girl">您的账号已被管理员封禁，解封时间<fmt:formatDate value="${shieldTime}" pattern="yyyy-MM-dd HH:mm" /></span> <em>原因：您的言行可能违反了 <a href="/about/rule">拒宅网社区管理细则</a></em>
						<b>如有疑问请email我们：<a href="mailto:feedback@51juzhai.com">feedback@51juzhai.com</a>
						</b>
					</div>
					<!--bfh end-->
				</div>
				<!--m end-->

				<div class="b"></div>

			</div>
			<!--content end-->

		</div>
		<!--main end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<c:set var="footType" value="fixed" scope="request" />
		<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
	</div>
	<!--warp end-->
</body>
</html>
