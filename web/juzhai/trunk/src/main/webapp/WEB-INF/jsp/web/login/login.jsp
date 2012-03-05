<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="keywords" content="拒宅 ,找伴儿, 出去玩, 约会 ,交友" />
		<meta name="description" content="不想宅在家,找伴儿,出去玩,发现出去玩的好主意和同兴趣的朋友,促成约会"  />
		<title>登录-拒宅网</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="content"><!--content begin-->
					<div class="t"></div>
					<div class="m"><!--" begin-->
						<div class="singe_login"><!--singe_login begin-->
							<div class="l_left"><!--l_left begin-->
								<h2><p>加入拒宅</p><span>助你找伴儿出去玩</span></h2>
								<div class="btn">
									<a href="/web/login/6?turnTo=${turnTo}"><img src="${jzr:static('/images/web2/weibo_btn.gif')}" /></a>
									<a href="/web/login/7?turnTo=${turnTo}"><img src="${jzr:static('/images/web2/douban_btn.gif')}" /></a>
									<a href="/web/login/8?turnTo=${turnTo}"><img src="${jzr:static('/images/web2/qq_btn.gif')}" /></a>
								</div>
							</div><!--l_left end-->
							<div class="l_right">
								<p>
									<font>你是这样的小宅么？</font><br />
									你qq上有上百个好友，微博上有几百个粉丝；但在节假日里，却找不到人陪你出去玩。<br /><br />
									<font>拒宅网助你脱宅</font><br />
									拒宅网搭建了一个找伴儿出去玩的平台。在这里小宅们说出各种新奇、有趣的拒宅好主意；大家交流，认识，并相约一起出去玩。<br /><br />
									<font>一起打造一个阳光社区</font><br />
									厌倦了单刀直入物欲横流的交友社区么？难道交友约会就不能和阳光清新搭配么？
									如果您也有同感，就与我们一起营造这样的阳光社区吧。上传真实头像，发布你的拒宅好主意，积极的与感兴趣的小宅们互动。希望每位小宅都能找到属于自己的阳光周末。
								</p>
							</div>
						</div><!--singe_login end-->
					</div><!--" end-->
					<div class="b"></div>
				</div><!--content end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<c:set var="footType" value="fixed" scope="request"/>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
