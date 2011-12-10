<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>拒宅网</title>
<link href="${jz:static('/css/jz_web.css')}" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<div class="warp">
		<!--warp begin-->
		<div class="main">
			<!--main begin-->
			<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
			<!-- content begin -->
			<!-- content end -->
		</div>
		<!--main end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<script type="text/javascript">
			function showLogin(){
				$.dialog({
					content:$("#_loginDiv")[0],
					top:"50%",
					fixed: true,
					lock: true,
					title:"加入拒宅",
					id: 'login_div_box',
					padding: 0
				});
			}
		</script>
		<div class="bottom">
			<!--bottom begin-->
			<div class="link">
				<a href="#">关于我们</a>|<a href="#">联系我们</a>|<a href="#">加入我们</a>|<a
					href="#">帮助</a>
			</div>
			<p>
				拒宅网©2011 沪ICP备11031778号 沪ICP备10041365号 增值电信业务经营许可证 沪B2-20110031<br />

				客服热线：021-50179078-2095 客服QQ:494623520
			</p>
		</div>
		<!--bottom end-->
	</div>
	<!--warp end-->
</body>
</html>
