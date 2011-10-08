<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>拒宅器</title>
		<link href="${jz:url('/css/jz.css')}" rel="stylesheet" type="text/css" />
		<link href="${jz:url('/css/jquery.autocomplete.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="main"><!--main begin-->
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
										<a href="/msg/showUnRead.html" class="active new">
											<p class="l"></p>
											<span><em class="mail"></em><strong>未读(${pager.totalResults})</strong></span>
											<p class="r"></p>
										</a>
										<a href="/msg/showRead.html" class="link">
											<p class="l"></p>
											<span><em class="mail_open"></em><strong>已读(${readCount})</strong></span>
											<p class="r"></p>
										</a>
									</div><!--tab end-->
									<!--pbr begin--><!-- <div class="pbr"><a href="javascript:;">被我屏蔽的人</a></div> --><!--pbr end-->
								</div><!--title end-->
								<div class="box_body" id="unReadContent" ><!--box_body begin-->
								<jsp:include page="/WEB-INF/jsp/msg/app/ajax/unReadContent.jsp" />
								</div><!--box_body end-->
							</div><!--message_box end-->
						</div><!--mid end-->
						<div class="bot"></div>
					</div><!--content end-->
				</div><!--content_bg end-->
			</div><!--skin_body end-->
			<div class="skin_bottom"></div>
		</div><!--main end-->
		<div class="alert_box " id="pointDiv" style="display: none" >
			<div class="box_top"></div>
			<div class="box_mid" ><!--box_mid begin-->
				<div class="title"></div>
				<p>
					查看该消息需要 <em id="_ponit">20</em> 拒宅积分，而你当前只有 <em>${point}</em> 积分。<br />
					<span>你可通过如下方式获得积分：</span><br />
					添加一个兴趣+5分<br />
					每天登陆+5分<br />
					点击“想去”+5分<br />
					评价好友+5分<br />
					响应好友的邀请+10分<br />
					发布拒宅召集令+20分<br />
					订阅邮箱+20分
				</p>
				<a href="javascript:;" onclick="closeAllDiv();" class="iknow">知道了</a>
			</div><!--box_mid end-->
			<div class="box_bot"></div>
		</div>
		<script type="text/javascript" src="${jz:url('/js/module/msg.js')}"></script>
		<jsp:include page="/WEB-INF/jsp/common/app/script/script.jsp" />
		<jsp:include page="/WEB-INF/jsp/common/app/artDialog/artDialog.jsp" />
		<jsp:include page="/WEB-INF/jsp/common/app/foot.jsp" />
	
	</body>
</html>