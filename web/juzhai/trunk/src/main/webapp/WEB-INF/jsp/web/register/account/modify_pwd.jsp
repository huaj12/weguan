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
								<div class="my_infor"><!--my_infor begin-->
									<div class="zhmm"><!--zhmm begin-->
										<div class="input_x"><!--input_x begin-->
											<h3>登录邮箱：</h3>
											<div class="big_input unclick">
												<p class="l"></p><span><input type="text" disabled="disabled" value="${account}" /></span><p class="r"></p>
												<em></em>
											</div>
										</div><!--input_x end-->
										<div class="input_x"><!--input_x begin-->
											<h3>登录密码：</h3>
											<div class="login_alink"><a href="javascript:void(0);">更改&gt;&gt;</a></div>
										</div><!--input_x end-->
										<form id="modify-pwd-form" style="display: none;">
											<div class="input_x"><!--input_x begin-->
												<h3>原始密码：</h3>
												<div id="form-old-pwd" class="big_input">
													<p class="l"></p><span><input name="oldPwd" type="password" /></span><p class="r"></p>
													<em></em>
												</div>
											</div><!--input_x end-->
											<div class="input_x"><!--input_x begin-->
												<h3>新密码：</h3>
												<div id="form-new-pwd" class="big_input">
													<p class="l"></p><span><input name="newPwd"  type="password" /></span><p class="r"></p>
													<em></em>
												</div>
											</div><!--input_x end-->
											<div class="input_x"><!--input_x begin-->
												<h3>确认密码：</h3>
												<div id="form-confirm-pwd" class="big_input">
													<p class="l"></p><span><input name="confirmPwd" type="password"/></span><p class="r"></p>
													<em>两次输入的密码的不相符</em>
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
			<script type="text/javascript" src="${jzr:static('/js/web/modify_pwd.js')}"></script>
			<c:set var="footType" value="fixed" scope="request"/>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
