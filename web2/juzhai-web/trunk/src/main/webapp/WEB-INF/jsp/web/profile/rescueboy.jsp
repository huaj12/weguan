<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宅男自救器 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
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
						<c:set var="page" value="rescueboy" scope="request" />
						<jsp:include page="/WEB-INF/jsp/web/profile/common/left.jsp" />
						<div class="set_right"><!--set_right begin-->
							<div class="title"><h2>设置宅男自救器</h2></div>
							<div class="save_self"><!--save_self begin-->
							<div class="img"></div>
								<div class="save_right">
								<p>启用后，如果您每天登陆，我们会帮您精选出优质的拒宅邀请发给适合你的宅女。
									让您时常收到宅女们的惊喜回复。本功能仅对部分优质宅男开放，快来试试吧！
								</p>
										<c:choose>
											<c:when test="${not empty loginUser.logoPic }">
												<div class="close_save" <c:if test="${!open}">style="display: none"</c:if> >
													<em></em>
													<a href="javascript:void(0);"></a>
												</div>
												<div class="open_save" <c:if test="${open }">style="display: none"</c:if>>
													<a href="javascript:void(0);"></a>
												</div>
												<div class="save_ms" <c:if test="${open }">style="display: none"</c:if>>自救器开启后,您随时可以关闭</div>
											</c:when>
											<c:otherwise>
												<div class="open_save_unable">
													<a href="javascript:void(0);"></a>
												</div>
												<div class="save_ms">自救器开启后,您随时可以关闭</div>
												<div class="load_face">上传真实头像后才能使用本功能哦，<a href="/profile/index/face">去上传头像</a></div>
											</c:otherwise>
										</c:choose>
										<div class="open_save_ing" style="display:none;">
												<a href="javascript:void(0);"></a>
										</div>
								</div>
							</div><!--save_self end-->
						</div><!--set_right end-->
					</div><!--set end-->
				</div><!--m end-->
				<div class="b"></div>
			</div><!--content end-->
		</div><!--main end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<script type="text/javascript" src="${jzr:static('/js/web/rescueboy.js')}"></script>
		<c:set var="footType" value="fixed" scope="request"/>
		<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
	</div><!--warp end-->
</body>
</html>
