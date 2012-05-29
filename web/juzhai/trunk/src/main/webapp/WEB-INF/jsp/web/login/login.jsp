<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="keywords" content="拒宅 ,找伴儿, 出去玩, 约会 ,交友" />
		<meta name="description" content="不想宅在家,找伴儿,出去玩,发现出去玩的好主意和同兴趣的朋友,促成约会"  />
		<title>登录 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="member_area"><!--member_area begin-->
					<div class="area_t"></div>
					<div class="area_m"><!--area_m begin-->
						<div class="member_register"><!--member_register begin-->
							<form id="login-form" action="login" method="post">
								<input type="hidden" name="verifyKey" value="${loginForm.verifyKey}" />
								<input type="hidden" name="turnTo" value="${loginForm.turnTo}" />
								<div class="reg_left"><!--reg_left begin-->
									<h2>登录拒宅网</h2>
									<div class="input_x"><!--input_x begin-->
										<h3>邮箱：</h3>
										<div id="form-account" class="big_input">
											<p class="l"></p><span><input name="account" type="text" value="${loginForm.account}" /></span><p class="r"></p>
										</div>
									</div><!--input_x end-->
									<div class="input_x"><!--input_x begin-->
										<h3>密码：</h3>
										<div id="form-pwd" class="big_input">
											<p class="l"></p><span><input name="password" type="password"/></span><p class="r"></p>
										</div>
									</div><!--input_x end-->
									<div class="remember_me">
										<span><input name="remember" type="checkbox" value="true" <c:if test="${loginForm.remember}">checked="checked"</c:if> /></span>
										<p>记住我（2周内自动登录）</p>
									</div>
									<c:if test="${not empty loginForm.verifyKey}">
										<div class="input_x"><!--input_x begin-->
											<h3>验证码：</h3>
											<div id="from-verify-code" class="big_input <c:if test='${errorCode == "10007"}'>wrong</c:if>">
												<p class="l"></p><span class="w110"><input name="verifyCode" type="text" /></span><p class="r"></p>
												<div class="yzm"><img src="/code/getverifycode?key=${loginForm.verifyKey}&t=${t}" url="/code/getverifycode?key=${loginForm.verifyKey}&t=" /></div><div class="hyz"><a href="javascript:void(0);">换一张</a></div>
											</div>
										</div><!--input_x end-->
									</c:if>
									<div class="clear"></div>
									<div class="login_error" <c:if test="${empty errorInfo}">style="display:none;"</c:if>>${errorInfo}</div>
									<input type="submit" style="display: none;" />
									<div class="btn"><a href="javascript:void(0);">立即登录</a></div>
									<div class="forget_pwd"><a href="/passport/getbackpwd">忘记密码？</a></div>
								</div><!--reg_left end-->
							</form>
							<c:set var="isLogin" value="true" scope="request"/>
							<jsp:include page="tp_login.jsp" />
						</div><!--member_register end-->
					</div><!--area_m end-->
					<div class="clear"></div>
					<div class="area_b"></div>
				</div><!--member_area end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/login.js')}"></script>
			<c:set var="footType" value="fixed" scope="request"/>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>