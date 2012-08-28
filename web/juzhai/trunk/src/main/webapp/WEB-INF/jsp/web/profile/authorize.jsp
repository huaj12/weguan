<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>授权管理 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
					<c:choose>
						<c:when test="${context.tpName == 'weibo'}"><c:set var="name" value="新浪微博"/></c:when>
						<c:when test="${context.tpName == 'douban'}"><c:set var="name" value="豆瓣"/></c:when>
						<c:when test="${context.tpName == 'qq'}"><c:set var="name" value="QQ"/></c:when>
					</c:choose>
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
						<c:set var="page" value="authorize" scope="request" />
						<jsp:include page="/WEB-INF/jsp/web/profile/common/left.jsp" />
						<div class="set_right"><!--set_right begin-->
							<div class="title"><h2>我的${name}授权</h2></div>
							<div class="sq_set">
							<c:choose>
								<c:when test="${isExpired}">
									<p>${name}授权已过期，请重新授权激活${name}同步功能</p>
									<a href="/authorize/token/${context.tpId }">&lt;&lt;去重新授权</a>
								</c:when>
								<c:otherwise>
									<p>您的${name}授权成功，可以正常使用</p>
								</c:otherwise>
							</c:choose>
							<br/>
							<font color="red">${errorInfo}</font>
							</div>
						</div><!--set_right end-->
					</div><!--set end-->
				</div><!--m end-->
				<div class="b"></div>
			</div><!--content end-->
		</div><!--main end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<c:set var="footType" value="fixed" scope="request"/>
		<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
	</div><!--warp end-->
</body>
</html>
