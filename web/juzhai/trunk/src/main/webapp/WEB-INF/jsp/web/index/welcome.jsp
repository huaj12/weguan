<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>拒宅网</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<div class="fix_top"><!--fix_top begin-->
					<div class="top"><!--top begin-->
						<h2><a href="#"></a></h2>
						<div class="welcome_login"><p>加入拒宅:</p><a href="/web/login/6?turnTo=${turnTo}"></a></div>
					</div><!--top end-->
				</div><!--fix_top end-->
				<div class="welcome"><!--welcome begin-->
					<div class="welcome_t"></div>
					<div class="welcome_m">
						<h2>寻找一份久违了的阳光周末</h2>
						<div style="height: 250px;position: relative;overflow: hidden;margin: 0px 100px;">
							<div id="window-box" style="position: absolute;height: 250px;width: 2550px;left: 5px;">
								<ul window-count="${fn:length(postWindowViews)}">
									<c:forEach items="${postWindowViews}" varStatus="index" var="view">
										<li>
											<p><img src="${jzr:userLogo(view.profileCache.uid, view.profileCache.logoPic, 120)}" width="120" height="120"/></p>
											<span><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${view.postWindow.purposeType}" /></c:import>: </span>
											<em><c:out value="${jzu:truncate(view.postWindow.content, 74, '...')}" /></em>
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
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<script type="text/javascript" src="${jzr:static('/js/web/welcome.js')}"></script>
	</body>
</html>
