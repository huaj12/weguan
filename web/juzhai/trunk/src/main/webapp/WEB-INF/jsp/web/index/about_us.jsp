<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>关于我们-拒宅网</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="content"><!--content begin-->
					<div class="t"></div>
					<div class="m"><!--m begin-->
						<div class="about"><!--about_us begin-->
							<div class="title"><span class="about_us"></span><h2>关于我们</h2><span class="mail_us"></span><h2>联系我们</h2></div>
							<div class="about_box"><!--about_box begin-->
								<p>
									<b>你是这样的小宅么？</b><br />
									你微薄上有几百个粉丝，手机通讯录里有几百个联系人，但节假日仍要宅在家里。而你却不知道:其实很多人跟你一样，并不想宅在家，也希望周末能找个伴儿一起出去玩。<br /><br />
									<b>拒宅网如何帮助小宅们找伴儿出去玩？</b><br />
									告诉拒宅网你的空闲时间、你想出去玩什么；就可能会有同样爱好的人约你出去。你也可以在拒宅网上找到适合自己的玩伴儿（比如周六也想去打台球的人）;主动约ta。拒宅其实就这么简单；为何不就此开始呢？<br /><br />
									<a href="/showActs">立即体验&gt;&gt;</a>
								</p>
							</div><!--about_box end-->
							<div class="mail_box"><!--mail_box begin-->
								<p>
									<b>E-mail：</b>max@51juzhai.com<br />
									<b>小宅q群：</b>204851202<br />
									<b>官方微博：</b><a href="http://www.weibo.com/51juzhai">weibo.com/51juzhai</a><br />
								</p>
							</div><!--mail_box begin-->
						</div><!--about_us end-->
					</div><!--m end-->
					<div class="b"></div>
				</div><!--content end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
	</body>
</html>
