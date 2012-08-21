<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>账号与密码 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<c:set var="messageHide" value="true" scope="request" />
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="content"><!--content begin-->
					<div class="t"></div>
					<div class="m"><!--m begin-->
						<div class="set"><!--set begin-->
							<c:set var="page" value="account" scope="request" />
							<jsp:include page="/WEB-INF/jsp/web/profile/common/left.jsp" />
							<div class="set_right"><!--set_right begin-->
								<div class="title"><h2>设置我的账号密码</h2></div>
								<div class="my_infor"><!--my_infor begin-->
									<div class="tisi1">请填写有效的电子邮箱，该邮箱将成为你在拒宅网的登录帐号，同时用于找回密码。</div>
									<div class="zhmm"><!--zhmm begin-->
										<form id="register-form" action="/passport/setaccount" method="post">
											<div class="input_x"><!--input_x begin-->
												<h3>账号邮箱：</h3>
												<div id="form-account" class="big_input <c:if test='${errorCode == "10001" || errorCode == "10009"}'>wrong</c:if>">
													<p class="l"></p><span><input name="account" type="text" value="${account}" /></span><p class="r"></p>
													<em>${errorInfo}</em>
												</div>
											</div><!--input_x end-->
											<div class="input_x"><!--input_x begin-->
												<h3>密码：</h3>
												<div id="form-pwd" class="big_input <c:if test='${errorCode == "10002"}'>wrong</c:if>">
													<p class="l"></p><span><input name="pwd"  type="password" /></span><p class="r"></p>
													<em>${errorInfo}</em>
												</div>
											</div><!--input_x end-->
											<div class="input_x"><!--input_x begin-->
												<h3>确认密码：</h3>
												<div id="form-confirm-pwd" class="big_input <c:if test='${errorCode == "10003"}'>wrong</c:if>">
													<p class="l"></p><span><input name="confirmPwd" type="password"/></span><p class="r"></p>
													<em>${errorInfo}</em>
												</div>
											</div><!--input_x end-->
											<div class="btn"><a href="javascript:void(0);">保&nbsp;&nbsp;存</a></div>
										</form>
									</div><!--zhmm end-->
								</div><!--my_infor end-->
							</div><!--set_right end-->
						</div><!--set end-->
					</div><!--m end-->
					<div class="b"></div>
				</div><!--content end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/set_account.js')}"></script>
			<c:set var="footType" value="fixed" scope="request"/>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
