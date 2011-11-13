<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>拒宅器</title>
	<link href="${jz:static('/css/jz_v2.css')}" rel="stylesheet" type="text/css" />
		<link href="${jz:static('/css/jquery.autocomplete.css')}" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="main">
		<!--main begin-->
		<jsp:include page="/WEB-INF/jsp/common/app/app_header.jsp" />
		<div class="skin_body">
			<!--skin_body begin-->
			<div class="skin_top_bg">
				<!--content_bg begin-->
				<div class="content white">
					<!--content begin-->
					<div class="top"></div>
					<div class="mid">
						<!--mid begin-->
						<div class="jz_box">
							<!--jz_box begin-->
							<div class="search">
								<!--search begin-->
								<em><a href="#">返回首页</a> </em>
								<h2>
									抱歉， 没有找到<span>${name }</span>这个拒宅项目！
								</h2>
								<p>
									${name} 已经提交给我们，通过审核后将立即加入。我们建议您去<a href="#">拒宅项目库</a>通过分类查找。
								</p>
							</div>
							<!--search end-->
						</div>
						<!--jz_box end-->
					</div>
					<!--mid end-->
					<div class="bot"></div>
				</div>
				<!--content end-->
			</div>
			<!--content_bg end-->
		</div>
		<!--skin_body end-->
		<div class="skin_bottom"></div>
	</div>
	<!--main end-->
	<jsp:include page="/WEB-INF/jsp/common/app/script/script.jsp" />
	<script type="text/javascript" src="${jz:static('/js/base/kaixin_plugin.js')}"></script>
	<jsp:include page="/WEB-INF/jsp/common/app/foot.jsp" />
</body>
</html>