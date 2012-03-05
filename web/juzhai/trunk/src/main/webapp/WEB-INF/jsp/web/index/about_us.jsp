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
									你qq上有上百个好友，微博上有几百个粉丝；但在节假日里，却找不到人陪你出去玩。<br /><br />
									<b>拒宅网助你脱宅</b><br />
									拒宅网搭建了一个找伴儿出去玩的平台。在这里小宅们说出各种新奇、有趣的拒宅好主意；大家交流，认识，并相约一起出去玩。我们每天都会评选出小宅们分享的拒宅好主意，发表在微博上与广大小宅们分享，也帮助发布者召集来更多志同道合的拒宅伙伴。<br /><br />
									<b>一起打造一个阳光社区</b><br />
									厌倦了单刀直入物欲横流的交友社区么？难道交友约会就不能和阳光清新搭配么？
									如果您也有同感，就与我们一起营造这样的阳光社区吧。上传真实头像，发布你的拒宅好主意，积极的与感兴趣的小宅们互动。希望每位小宅都能找到属于自己的阳光周末。<br /><br />
									<a href="/home">立即体验&gt;&gt;</a>
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
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<c:set var="footType" value="fixed" scope="request"/>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
