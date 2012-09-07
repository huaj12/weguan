<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>授权设置 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
</head>
<c:set var="tpName" value="${jzd:tpName(context.tpId)}" />
<c:choose>
	<c:when test="${tpName == 'weibo'}"><c:set var="name" value="新浪微博"/></c:when>
	<c:when test="${tpName == 'douban'}"><c:set var="name" value="豆瓣"/></c:when>
	<c:when test="${tpName == 'qq'}"><c:set var="name" value="QQ"/></c:when>
</c:choose>
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
							<div class="title"><h2>第三方网站授权</h2></div>
								<div class="sq_set">
									<p>授权后，你可以把拒宅计划同步到新浪微博，让好友及时看到你的拒宅召唤</p>
								</div>
								<div class="dsf_sq  sina">
									<p></p>
									<span>新浪微博</span>
									<em>未授权</em>
									<a  href="/authorize/bind/6"></a>
								</div>
								<div class="clear"></div>
								<div class="dsf_sq douban">
									<p></p>
									<span>豆瓣</span>
									<em>未授权</em>
									<a  href="/authorize/bind/7"></a>
								</div>
								<div class="clear"></div>
								<div class="dsf_sq qq">
									<p></p>
									<span>QQ</span>
									<em>未授权</em>
									<a  href="/authorize/bind/8"></a>
								</div>
								<div class="clear"></div>
								<div class="sq_error">${errorInfo}</div>
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
