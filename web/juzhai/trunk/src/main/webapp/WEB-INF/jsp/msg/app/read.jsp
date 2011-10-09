<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>拒宅器</title>
		<link href="${jz:url('/css/jz.css')}" rel="stylesheet" type="text/css" />
		<link href="${jz:url('/css/jquery.autocomplete.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="main">
			<!--main begin-->
			<jsp:include page="/WEB-INF/jsp/common/app/app_header.jsp" />
			<div class="skin_body"><!--skin_body begin-->
				<div class="skin_top_bg"><!--content_bg begin-->
					<jsp:include page="/WEB-INF/jsp/common/app/app_prompt.jsp" />
					<jsp:include page="/WEB-INF/jsp/common/app/app_point.jsp" />
					<div class="content white"><!--content begin-->
						<div class="top"></div>
						<div class="mid"><!--mid begin-->
							<div class="message_box"><!--message_box begin-->
								<div class="title"><!--title begin-->
									<div class="tab"><!--tab begin-->
										<a href="/msg/showUnRead.html" class="link new">
											<p class="l"></p>
											<span><em class="mail"></em><strong>未读(<strong id="unReadCnt">${unReadCount}</strong>)</strong></span>
											<p class="r"></p>
										</a>
										<a href="/msg/showRead.html" class="active">
											<p class="l"></p>
											<span><em class="mail_open"></em><strong>已读(<strong id="readCnt">${pager.totalResults}</strong>)</strong></span>
											<p class="r"></p>
										</a>
									</div><!--tab end-->
									<!--pbr begin-->
									<!-- <div class="pbr"><a href="javascript:;">被我屏蔽的人</a></div> --><!--pbr end-->
								</div><!--title end-->
								<div class="box_body" id="readContent"><!--box_body begin-->
									<jsp:include page="/WEB-INF/jsp/msg/app/ajax/readContent.jsp" />
								</div><!--box_body end-->
							</div><!--message_box end-->
						</div><!--mid end-->
						<div class="bot"></div>
					</div><!--content end-->
				</div><!--content_bg end-->
			</div><!--skin_body end-->
			<div class="skin_bottom"></div>
		</div><!--main end-->
		<jsp:include page="/WEB-INF/jsp/common/app/script/script.jsp" />
		<jsp:include page="/WEB-INF/jsp/common/app/artDialog/artDialog.jsp" />
		<jsp:include page="/WEB-INF/jsp/common/app/foot.jsp" />
		<script type="text/javascript" src="${jz:url('/js/module/msg.js')}"></script>
	</body>
</html>