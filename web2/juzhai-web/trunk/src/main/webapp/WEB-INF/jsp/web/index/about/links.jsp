<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>关于我们 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="content"><!--content begin-->
					<div class="t"></div>
					<div class="m"><!--m begin-->
						<div class="other_page"><!--other_page begin-->
							<div class="other_page"><!--other_page begin-->
							<div class="other_left"><!--other_left begin-->
								<h2>友情链接</h2>
								<ul>
									<li><a href="http://www.douban.com/" target="_blank">豆瓣</a></li>
									<li><a href="http://www.xuejineng.cn/" target="_blank">学技能</a></li>
									<li><a href="http://app.tongbu.com/" target="_blank">同步助手</a></li>
									<li><a href="http://www.party021.com" target="_blank">上海派对网</a></li>
									<li><a href="http://www.365ddt.com/" target="_blank">店店通</a></li>
									<li><a href="http://www.25pp.com/" target="_blank">iPhone游戏</a></li>
									<li><a href="http://www.gzhong.cn/" target="_blank">工众网</a></li>
								</ul>
							</div><!--other_left end-->
							<div class="other_right"><!--other_right begin-->
								<a href="/about/us">了解拒宅网</a>
								<a href="/about/rule">社区管理细则</a>
								<a href="/about/contact">联系我们</a>
								<a href="/about/join">加入我们</a>
								<a href="javascript:void(0);" class="select">友情链接</a>
							</div><!--other_right end-->
						</div><!--other_page end-->
					</div><!--m end-->
					<div class="b"></div>
				</div><!--content end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<c:set var="footType" value="fixed" scope="request"/>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
