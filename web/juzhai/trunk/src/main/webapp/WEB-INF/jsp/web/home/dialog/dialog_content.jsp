<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>私信-拒宅网</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="content"><!--content begin-->
					<div class="t"></div>
					<div class="m"><!--m begin-->
						<div class="message"><!--message begin-->
							<div class="title" id="respond"><span class="sx"></span><h2>我与&nbsp;<c:out value="${targetProfile.nickname}" />&nbsp;的私信</h2><a href="/showActs">返回首页</a><a href="/home/dialog/1">返回所有私信</a></div>
							<div class="message_repy <c:choose><c:when test='${targetProfile.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--message_repy begin-->
								<div class="repy_area"><!--repy_area begin-->
									<p><img src="${jzr:userLogo(targetProfile.uid,targetProfile.logoPic,80)}"  width="80" height="80"/></p>
									<span><h4>我回复</h4><a href="/home/${targetProfile.uid}"><c:out value="${targetProfile.nickname}" /></a></span>
									<div class="text_area"><em><textarea name="content" cols="" rows=""></textarea></em><a href="javascript:void(0);" target-uid="${targetProfile.uid}">回复</a></div>
								</div><!--repy_area end-->
								<jsp:include page="dialog_content_list.jsp" />
							</div><!--message_repy end-->
						</div><!--message end-->
					</div><!--m end-->
					<div class="b"></div>
				</div><!--content end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<script type="text/javascript" src="${jzr:static('/js/web/dialog_content.js')}"></script>
	</body>
</html>
