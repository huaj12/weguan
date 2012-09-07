<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>我的系统通知 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="content"><!--content begin-->
					<div class="t"></div>
					<div class="m"><!--m begin-->
						<div class="ty_frame"><!--ty_frame begin-->
							<div class="title"><span class="tz"></span><h2>系统通知</h2></div>
							<div class="system_tz"><!--system_tz begin-->
								<c:choose>
									<c:when test="${empty sysNoticeList}">
										<div class="none_tz">目前没有你的通知</div>										
									</c:when>
									<c:otherwise>
										<ul>
											<c:forEach var="sysNotice" items="${sysNoticeList}">
												<li class="mouseHover"><em>[<fmt:formatDate value="${sysNotice.createTime}" pattern="yyyy-MM-dd" />]</em><p><b></b>${sysNotice.content}</p><span><a href="javascript:void(0);" sysnoticeid="${sysNotice.id}">删除</a></span></li>
											</c:forEach>
										</ul>
										<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
											<c:param name="pager" value="${pager}"/>
											<c:param name="url" value="/notice/sysNotices" />
										</c:import>
									</c:otherwise>
								</c:choose>
							</div><!--system_tz end-->
						</div><!--ty_frame end-->
					</div><!--m end-->
					<div class="b"></div>
				</div><!--content end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/sys_notice.js')}"></script>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
