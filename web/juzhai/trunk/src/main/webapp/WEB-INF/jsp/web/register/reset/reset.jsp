<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>重设密码 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="find_pwd"><!--find_pwd begin-->
					<div class="find_t"></div>
					<div class="find_m"><!--find_m begin-->
						<div class="find_area"><!--find_area begin-->
							<h2>拒宅网 - 重设密码</h2>
							<div class="input_x"><!--input_x begin-->
								<h3>账号邮箱：</h3>
								<div class="big_input unclick">
									<p class="l"></p><span><input type="text" disabled="disabled" value="${account}" /></span><p class="r"></p>
									<em></em>
								</div>
							</div><!--input_x end-->
							<form id="reset-form" action="/passport/resetpwd" method="post">
								<input type="hidden" name="uid" value="${resetPwdForm.uid}" />
								<input type="hidden" name="token" value="${resetPwdForm.token}" />
								<div class="input_x"><!--input_x begin-->
									<h3>新密码：</h3>
									<div id="form-pwd" class="big_input <c:if test='${errorCode == "10002"}'>wrong</c:if>">
										<p class="l"></p><span><input name="pwd" type="password" /></span><p class="r"></p>
										<em>${errorInfo}</em>
									</div>
								</div><!--input_x end-->
								<div class="input_x"><!--input_x begin-->
									<h3>确认密码：</h3>
									<div id="form-confirm-pwd" class="big_input <c:if test='${errorCode == "10003"}'>wrong</c:if>">
										<p class="l"></p><span><input name="confirmPwd" type="password" /></span><p class="r"></p>
										<em>${errorInfo}</em>
									</div>
								</div><!--input_x end-->
								<div class="btn" style="margin-left:92px; padding-bottom:10px;"><a href="javascript:void(0);">保&nbsp;&nbsp;&nbsp;&nbsp;存</a></div>
							</form>
						</div><!--find_area end-->
					</div><!--find_m end-->
					<div class="clear"></div>
					<div class="find_b"></div>
				</div><!--find_pwd end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/reset.js')}"></script>
			<c:set var="footType" value="fixed" scope="request"/>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
