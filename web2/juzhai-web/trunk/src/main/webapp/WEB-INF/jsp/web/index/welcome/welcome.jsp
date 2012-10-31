<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
		<meta  name="keywords"   content="拒宅,找伴,出去玩,上海拒宅好主意,北京拒宅好主意,深圳拒宅好主意,创意拒宅好主意" />
		<meta  name="description"   content="不想宅在家拒宅网帮你找伴儿,出去玩,发现上海拒宅好主意,北京拒宅好主意,深圳拒宅好主意,创意拒宅好主意和同兴趣的朋友,促成约会" />
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
						<h1><a href="http://www.51juzhai.com/"></a></h1>
						<div class="welcome_page_login"><a href="/login" title="登录">登录</a><a href="/passport/register" title="注册">注册</a><a href="/dl/index" title="手机版">手机版</a></div>
					</div><!--top end-->
				</div><!--fix_top end-->
					<div class="clear"></div>
				<div class="welcome_page"><!--begin-->
					<div class="left_show_img">
						<p><img src="${jzr:ideaPic(idea.id,idea.pic, 450)}" width="490" height="310" /></p>
						<span><em>${jzu:truncate(idea.content,50,'...')}</em><h2>拒宅网 助你找伴出去玩</h2></span>
						<div class="black_area_bg"></div>
					</div>
					<div class="right_idea_area">
						<div class="right_login">
									<a href="javascript:void(0);" class="wb login-btn" title="使用微博账号登录" go-uri="/web/login/6"></a>
									<a href="javascript:void(0);" class="db login-btn" title="使用豆瓣账号登录" go-uri="/web/login/7"></a>
									<c:choose>
										<c:when test="${not empty isQplus&&isQplus}">
										<a href="javascript:void(0);" class="qq login-btn" title="使用QQ账号登录" go-uri="/qplus/loginDialog/9"></a>
										</c:when>
										<c:otherwise>
										<a href="javascript:void(0);" class="qq login-btn" title="使用QQ账号登录" go-uri="/web/login/8"></a>	
										</c:otherwise>
									</c:choose>
						</div>
						<div class="right_idea">
							<h2>${userCount}位小宅正在努力脱宅...</h2>
							<ul>
								<c:forEach items="${postView }" var="view">
								<li><p><img src="${jzr:userLogo(view.profileCache.uid,view.profileCache.logoPic,80)}"  width="40" height="40"/></p><span><font><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${view.post.purposeType}"/></c:import>:</font><c:out value="${jzu:truncate(view.post.content,40,'...')}"></c:out></span></li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div><!--end-->
				</div><!--main end-->
				<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
				<script type="text/javascript" src="${jzr:static('/js/web/welcome.js')}"></script>
				<c:set var="footType" value="welcome" scope="request" />
				<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
			</div><!--warp end-->
</body>
</html>
