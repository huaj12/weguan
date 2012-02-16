<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
									你是这样的小宅么？<br /><br />
									小宅QQ上有几百个好友，微薄上有几百个粉丝，手机通讯录里有几百个联系人，但节假日ta仍要宅在家里。<br /><br />
									在这些不出门的日子，小宅每天对着电脑、看着冷冰冰的微博头像和qq好友，但就是找不到一个人可以让ta感到温情。<br /><br />......<br /><br />拒宅网——助小宅门找伴儿出去玩
								</p>
							</div>
						</div><!--singe_login end-->
					</div><!--" end-->
					<div class="b"></div>
				</div><!--content end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
	</body>
</html>
