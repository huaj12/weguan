<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="keywords" content="拒宅 ,找伴儿, 出去玩, 约会 ,交友" />
		<meta name="description" content="不想宅在家,找伴儿,出去玩,发现出去玩的好主意和同兴趣的朋友,促成约会"  />
		<title>注册 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<div class="fix_top"><!--fix_top begin-->
					<div class="beta"></div>
					<div class="top"><!--top begin-->
						<h1><a href="http://www.51juzhai.com"></a></h1>
						<div class="login_btn"><a href="/login" class="btn_log" title="登录">登录</a><a href="/passport/register" class="btn_res" title="注册">注册</a></div>
					</div><!--top end-->
				</div><!--fix_top end-->
				<div class="member_area"><!--member_area begin-->
					<div class="area_t"></div>
					<div class="area_m"><!--area_m begin-->
						<div class="member_register"><!--member_register begin-->
							<div class="reg_left"><!--reg_left begin-->
								<h2>注册拒宅网</h2>
								<form id="register-form" action="/passport/register" method="post">
									<input type="hidden" name="inviterUid" value="${registerForm.inviterUid}" />
									<input type="hidden" name="verifyKey" value="${registerForm.verifyKey}" />
									<div class="input_x"><!--input_x begin-->
										<h3>邮箱：</h3>
										<div id="form-account" class="big_input <c:if test='${errorCode == "10001" || errorCode == "10009"}'>wrong</c:if>">
											<p class="l"></p><span><input name="account" value="${registerForm.account}" type="text" /></span><p class="r"></p>
											<em>${errorInfo}</em>
										</div>
									</div><!--input_x end-->
									<div class="input_x"><!--input_x begin-->
										<h3>昵称：</h3>
										<div id="form-nickname" class="big_input <c:if test='${errorCode == "20005" || errorCode == "20006" || errorCode == "20018" || errorCode == "20021"}'>wrong</c:if>">
											<p class="l"></p><span><input name="nickname" value="${registerForm.nickname}" type="text" /></span><p class="r"></p>
											<em>${errorInfo}</em>
										</div>
									</div><!--input_x end-->
									<div class="input_x"><!--input_x begin-->
										<h3>密码：</h3>
										<div id="form-pwd" class="big_input <c:if test='${errorCode == "10002"}'>wrong</c:if>">
											<p class="l"></p><span><input name="pwd" type="password" /></span><p class="r"></p>
											<em>${errorInfo}</em>
										</div>
									</div><!--input_x end-->
									<div class="input_x"><!--input_x begin-->
										<h3>确认密码：</h3>
										<div id="form-confirm-pwd" class="big_input <c:if test='${errorCode == "10003"}'>wrong</c:if>">
											<p class="l"></p><span><input name="confirmPwd" type="password"/></span><p class="r"></p>
											<em>${errorInfo}</em>
										</div>
										<!-- <div class="clear"></div>
										<div class="clear"></div> -->
									</div><!--input_x end-->
									<div class="input_x"><!--input_x begin-->
										<h3>验证码：</h3>
										<div id="from-verify-code" class="big_input <c:if test='${errorCode == "10007"}'>wrong</c:if>">
											<p class="l"></p><span class="w110"><input name="verifyCode" type="text" /></span><p class="r"></p>
											<div class="yzm"><img src="/code/getverifycode?key=${registerForm.verifyKey}&t=${t}" url="/code/getverifycode?key=${registerForm.verifyKey}&t=" /></div><div class="hyz"><a href="javascript:void(0);">换一张</a></div>
											<em>${errorInfo}</em>
										</div>
									</div><!--input_x end-->
									<div class="btn"><a href="javascript:void(0);">立即注册</a></div>
								</form>
								<div class="xieyi">
								<span><input name="agreement" type="checkbox" checked="checked" /></span><p>我已阅读并同意<a href="/about/agreement" target="_blank">《使用协议》</a></p>
								<div class="clear"></div>
								<div class="clear"></div>
								<div class="clear"></div>
								<div class="clear"></div>
								</div>
							</div><!--reg_left end-->
							<jsp:include page="/WEB-INF/jsp/web/login/tp_login.jsp" />
						</div><!--member_register end-->
					</div><!--area_m end-->
					<div class="clear"></div>
					<div class="area_b"></div>
				</div><!--member_area end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/register.js')}"></script>
			<div style="display: none;">
				<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
			</div>
		</div><!--warp end-->
	</body>
</html>
