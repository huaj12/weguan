<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>拒宅网手机版  拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="warp"><!--warp begin-->
		<div class="main"><!--main begin-->
			<div class="fix_top"><!--fix_top begin-->
				<div class="top"><!--top begin-->
					<h3></h3>
					<div class="back_to_home"><a href="http://www.51juzhai.com/">去拒宅网&gt;&gt;</a></div>
				</div><!--top end-->
			</div><!--fix_top end-->
			<div class="clear"></div>
			<div class="iphone"><!--iphone begin-->
				<div class="page_seo"><!--page_seo begin-->
					<h1>拒宅手机版</h1>
					<h2>随时随地拒宅</h2>
					<h2>流畅的对话聊天</h2>         
					<h2>更及时的通知</h2>
				</div><!--page_seo end-->
				<div class="iphone_page"><!--iphone_page begin-->
					<div class="btns">
						<a href="/download/ios" target="_blank"><img src="${jzr:static('/images/web2/iphone_btn2.jpg')}" /></a>
						<!-- 
						<a href="#"><img src="${jzr:static('/images/web2/iphone_btn1.jpg')}" /></a>
						<a href="#"><img src="${jzr:static('/images/web2/iphone_btn3.jpg')}" /></a>
						<a href="#"><img src="${jzr:static('/images/web2/iphone_btn4.jpg')}" /></a>
						 -->
					</div>
				<div class="show"></div>
				</div><!--iphone_page end-->
			</div><!--iphone end-->
			<c:set var="footType" value="welcome" scope="request"/>
			<c:set var="parame" value="iphone_mt" scope="request"/>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--main end-->
	</div><!--warp end-->
</body>
</html>
