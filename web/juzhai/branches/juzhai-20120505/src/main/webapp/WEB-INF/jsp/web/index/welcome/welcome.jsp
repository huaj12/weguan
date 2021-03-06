<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
		<meta  name="keywords"   content="拒宅,找伴,出去玩,上海约会地点,北京约会地点,深圳约会地点,创意约会地点,约会地点,约会" />
		<meta  name="description"   content="不想宅在家拒宅网帮你找伴儿,出去玩,发现上海约会地点,北京约会地点,深圳约会地点,创意约会地点和同兴趣的朋友,促成约会" />
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<div class="fix_top"><!--fix_top begin-->
					<c:if test="${empty isQplus||!isQplus}">
							<div class="beta"></div>
					</c:if>
					<div class="top"><!--top begin-->
						<h2></h2>
						<div class="login_btn"><a href="/login" class="btn_log" title="登录">登录</a><a href="/passport/register" class="btn_res" title="注册">注册</a></div>
						<%-- <div class="welcome_login"><p>登录:</p>
							<a href="javascript:void(0);" go-uri="/web/login/6" class="wb" title="使用微博账号登录"></a>
							<a href="javascript:void(0);" go-uri="/web/login/7" class="db"  title="使用豆瓣账号登录"></a>
							<a href="javascript:void(0);" go-uri="/web/login/8" class="qq"  title="使用QQ账号登录"></a>
						</div>
						<div class="login_btns"><!--login_btns begin-->
							<p>加入拒宅：</p>
							<span>
								<a href="javascript:void(0);" class="wb login-btn" title="使用微博账号登录" go-uri="/web/login/6">登录</a>
								<a href="javascript:void(0);" class="db login-btn"  title="使用豆瓣账号登录" go-uri="/web/login/7">登录</a>
								<a href="javascript:void(0);" class="qq login-btn"  title="使用QQ账号登录" go-uri="/web/login/8">登录</a>
							</span>
						</div><!--login_btns end--> --%>
					</div><!--top end-->
				</div><!--fix_top end-->
				<div class="welcome"><!--welcome begin-->
					<div class="welcome_t"></div>
					<div class="welcome_m">
						<h2>
							<b>一个小清新的脱宅社区</b>
							<div class="wel_login">
								<a href="javascript:void(0);" class="wb login-btn" title="使用微博账号登录" go-uri="/web/login/6">登录</a>
								<a href="javascript:void(0);" class="db login-btn" title="使用豆瓣账号登录" go-uri="/web/login/7">登录</a>
								<c:choose>
									<c:when test="${not empty isQplus&&isQplus}">
									<a href="javascript:void(0);" class="qq login-btn" title="使用QQ账号登录" go-uri="/qplus/loginDialog/9">登录</a>
									</c:when>
									<c:otherwise>
									<a href="javascript:void(0);" class="qq login-btn" title="使用QQ账号登录" go-uri="/web/login/8">登录</a>	
									</c:otherwise>
								</c:choose>
							</div>
						</h2>
						<div class="clear"></div>
						<div style="height: 290px;position: relative;overflow: hidden;margin: 0px 100px;">
							<div id="window-box" style="position: absolute;height: 250px;width: 7500px;left: 5px;">
								<ul window-count="${fn:length(postWindowViews)}">
									<c:forEach items="${postWindowViews}" varStatus="index" var="view">
										<li>
											<p><a href="/login"><img src="${jzr:userLogo(view.profileCache.uid, view.profileCache.logoPic, 120)}" width="120" height="120"/></a></p>
											<span><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${view.postWindow.purposeType}" /></c:import>: </span>
											<em><a href="/login"><c:out value="${jzu:truncate(view.postWindow.content, 74, '...')}" /></a></em>
										</li>
									</c:forEach>
								</ul>
							</div>
						</div>
						<div class="arrow_left"><a href="javascript:void(0);"></a></div>
						<div class="arrow_right"><a href="javascript:void(0);"></a></div>
					</div>
					<div class="welcome_b"></div>
				</div><!--welcome end-->
				<div class="wel_num"><!--wel_num begin-->
					<jsp:include page="welcome_num.jsp" />
				</div><!--wel_num end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/welcome.js')}"></script>
			<c:set var="footType" value="welcome" scope="request"/>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
