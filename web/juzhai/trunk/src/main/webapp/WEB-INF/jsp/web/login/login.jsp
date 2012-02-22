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
									<a href="/web/login/6?turnTo=${turnTo}"><img src="${jzr:static('/images/web/48.png')}" /></a>
								</div>
							</div><!--l_left end-->
							<div class="l_right">
								<p>
									<font>你是这样的小宅么？</font><br /><br />
									你qq上有上百个好友，微博上有几百个粉丝；但在节假日里，却找不到人陪你出去玩。
									<br /><br />
									<br />
									<font>拒宅网助你脱宅</font><br /><br />
									拒宅网搭建了一个找伴儿出去玩的平台。在这里小宅们说出各种新奇、有趣的拒宅好主意；大家交流，认识，并相约一起出去玩。<br /><br />
									拒宅网每位用户都来源于微博。通过ta的微博你可以更真实的了解一个人的所思所想，ta的品味，圈子，甚至性格。当然,这个人是真是假自然也骗不过聪明的你咯。<br />
								</p>
							</div>
						</div><!--singe_login end-->
					</div><!--" end-->
					<div class="b"></div>
				</div><!--content end-->
			</div><!--main end-->
			<c:set var="footType" value="fixed" scope="request"/>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
	</body>
</html>
