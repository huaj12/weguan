<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>我的订阅设置 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin--> 
			<div class="main"><!--main begin-->
			<c:set var="page" value="face" scope="request"/>
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="content"><!--content begin-->
					<div class="t"></div>
					<div class="m"><!--m begin-->
						<div class="set"><!--set begin-->
							<c:set var="page" value="mail" scope="request" />
							<jsp:include page="/WEB-INF/jsp/web/profile/common/left.jsp" />
							<div class="set_right"><!--set_right begin-->
								<form action="/profile/updateEmail" id="updateEmail" method="post">
									<div class="title"><h2>用email订阅下面的消息</h2></div>
									<div class="rss"><!--rss begin-->
										<div class="rss_x"><div class="check"><span><input name="interestMe" type="checkbox" <c:if test="${emailForm.interestMe}">checked="checked"</c:if> value="true" /></span><p>有人敲我的门</p></div></div>
										<div class="rss_x"><div class="check"><span><input name="datingMe" type="checkbox" <c:if test="${emailForm.datingMe}">checked="checked"</c:if> value="true" /></span><p>有人约我</p></div></div>
										<div class="rss_x"><div class="check"><span><input name="acceptDating" type="checkbox" <c:if test="${emailForm.acceptDating}">checked="checked"</c:if> value="true" /></span><p>有人接受了我的邀约</p></div></div>
										<div class="line"></div>
										<div class="rss_x"><div class="check"><span><input name="sysNotice" type="checkbox" <c:if test="${emailForm.sysNotice}">checked="checked"</c:if> value="true" /></span><p>我的通知</p></div></div>
										<div class="rss_x">
											<div class="tit">订阅邮箱：</div>
											<div class="input"><p class="l"></p><span class="w190"><input id="subEmail" name="email" type="text" value="${emailForm.email}"/></span><p class="r"></p></div>
											<div id="emailError" class="error" <c:if test="${errorCode==null||errorCode==''}">style="display:none;"</c:if>>${errorInfo}</div>
										</div>
										<div class="btn"><a href="javascript:void(0);" id="updateEmailBtn">保存</a></div>
									</div><!--rss end-->
								</form>
							</div><!--set_right end-->
						</div><!--set end-->
					</div><!--m end-->
					<div class="b"></div>
				</div><!--content end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<jsp:include page="/WEB-INF/jsp/web/common/script/jcrop.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/sub_email.js')}"></script>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
