<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>邀请进入拒宅网</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<div class="fix_top"><!--fix_top begin-->
					<div class="top"><!--top begin-->
						<h1></h1>
					</div><!--top end-->
				</div><!--fix_top end-->
				<div class="clear"></div>
				<div class="user_yq"><!--user_yq begin-->
					<div class="user_face"><!--user_face begin-->
						<p><img src="${jzr:userLogo(profile.uid, profile.logoPic, 120)}" /></p>
					</div><!--user_face end-->
					<div class="yq_infor"><!--yq_infor begin-->
						<h2><span class="<c:choose><c:when test="${profile.gender == 1}">boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><c:out value="${profile.nickname}" /></span>邀请你加入拒宅网</h2>
						<p>
							你是这样的小宅么？<br />
							你qq上有上百个好友，微博上有几百个粉丝；但在节假日里，却找不到人陪你出去玩。<br />
							拒宅网助你脱宅<br />
							我们搭建了一个找伴儿出去玩的平台！大家交流，认识，并相约一起出去玩。
						</p>
						<div class="login_btns"><!--login_btns begin-->
							<em>用合作网站账号登录：</em>
										<a href="/web/login/6?incode=${token}"><img src="${jzr:static('/images/web2/yq_wb.jpg')}" /></a>
										<a href="/web/login/7?incode=${token}"><img src="${jzr:static('/images/web2/yq_db.jpg')}" /></a>
							<c:choose>
									<c:when test="${not empty isQplus&&isQplus}">
										<a href="/qplus/loginDialog/9"><img src="${jzr:static('/images/web2/yq_qq.jpg')}" /></a>
									</c:when>
									<c:otherwise>
										<a href="/web/login/8?incode=${token}"><img src="${jzr:static('/images/web2/yq_qq.jpg')}" /></a>	
									</c:otherwise>
							</c:choose>
						</div><!--login_btns end-->
					</div><!--yq_infor end-->
				</div><!--user_yq end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<c:set var="footType" value="invite" scope="request"/>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
