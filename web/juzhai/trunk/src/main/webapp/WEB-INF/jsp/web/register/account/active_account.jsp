<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil"%>
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
										<div class="jihuo_mail">
											<h2>请激活你的邮箱</h2>
											<p>一封确认邮件已经发送到你的邮箱${account}，点击邮件里的确认链接即可登录，快去查收邮件吧！</p>
										</div>
										<div class="btn_jh"><a href="${jzu:mailDomain(account)}" target="_blank">查看邮箱</a></div>
										<div class="ts">
											<h2>还没有收到确认邮件？</h2>
											<p>可以尝试去垃圾邮件里找找看<br /> 
												可以<a id="resend" href="javascript:void(0);">重新发送邮件</a>?<br />
												邮箱地址写错了？很抱歉，你需要<a href="/passport/register">重新注册</a><br />
											</p>
										</div>
									</div><!--zhmm end-->
								</div><!--my_infor end-->
							</div><!--set_right end-->
						</div><!--set end-->
					</div><!--m end-->
					<div class="b"></div>
				</div><!--content end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/active_account.js')}"></script>
			<c:set var="footType" value="fixed" scope="request"/>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
