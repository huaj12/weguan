<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>无标题文档</title>
		<c:set var="cssFile" value="/css/jz_${context.tpName}.css" />
		<link href="${jz:static(cssFile)}" rel="stylesheet" type="text/css" />
		<link href="${jz:static('/css/jquery.autocomplete.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<jsp:include page="/WEB-INF/jsp/common/app/${context.tpName}/top_logo.jsp" />
		<div class="main"><!--main begin-->
			<jsp:include page="/WEB-INF/jsp/common/app/app_header.jsp" />
			<div class="skin_body"><!--skin_body begin-->
				<div class="skin_top_bg"><!--content_bg begin-->
					<div class="content white"><!--content begin-->
						<div class="top"></div>
						<div class="mid"><!--mid begin-->
							<div class="project_sub"><!--project_sub begin-->
								<div class="back_to_home"><a href="javascript:void(0);" onclick="javascript:history.go(-1);">返回上一页</a></div>
								<div class="photo fl"><img src="${jz:actLogo(act.id,act.logo,120)}" /></div>
								<div class="infor fl"><!--infor begin-->
									<h2><c:out value="${act.name}" /><a href="/app/index" class="fxgd">发现更多&gt;&gt;</a></h2>
									<h5><c:out value="${act.intro}" /><!-- <a href="#">详细</a> --></h5>
									<div class="ytj" <c:if test="${!hasAct}">style="display:none;"</c:if>>
										<p></p><span><em>已添加</em><strong>|</strong><a href="javascript:void(0);" actid="${act.id}" actname="${act.name}">取消</a></span><p></p>
									</div>
									<a href="javascript:void(0);" class="want btn" actid="${act.id}" <c:if test="${hasAct}">style="display:none;"</c:if> title="接收相关邀请">❤ 我想去</a><c:if test="${!isShield}"><a href="javascript:void(0);" class="zjl btn" actid="${act.id}">分享</a></c:if><%--<a href="javascript:void(0);" class="yqhy btn" actid="${act.id}">邀请好友</a> --%>
								</div><!--infor end-->
								<div class="wantgolist"><!--wantgolist begin-->
									<div class="title" actid="${act.id}" friendUser="${showFriendUser}"><!--title begin-->
										<h3>想去的</h3><em></em>
										<a href="javascript:void(0);" class="active" id="allUser"><p class="l"></p><span>所有人(${userActCount})</span><p class="r"></p></a>
										<!-- <a href="#" class="link"><p class="l"></p><span>同城人(12)</span><p class="r"></p></a> -->
										<a href="javascript:void(0);" class="link" id="friendUser"><p class="l"></p><span>好友(${fUserActCount})</span><p class="r"></p></a>
									</div><!--title end-->
									<div id="actUserList" style="display: none;"></div>
									<div class="item_loading"><!--item_loading begin-->
										<img src="${jz:static('/images/loading.gif')}" />
									</div><!--item_loading end-->
								</div><!--wantgolist end-->
							</div><!--project_sub end-->
						</div><!--mid end-->
						<div class="bot"></div>
					</div><!--content end-->
				</div><!--content_bg end-->
			</div><!--skin_body end-->
			<div class="skin_bottom"></div>
		</div><!--main end-->
		<jsp:include page="/WEB-INF/jsp/common/app/script/script.jsp" />
		<jsp:include page="/WEB-INF/jsp/common/app/sendMessage.jsp" />
		<jsp:include page="/WEB-INF/jsp/common/app/sendFeed.jsp" />
		<script type="text/javascript" src="${jz:static('/js/module/act.js')}"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				
			});
		</script>
		<jsp:include page="/WEB-INF/jsp/common/app/script/app.jsp" />
		<jsp:include page="/WEB-INF/jsp/common/app/foot.jsp" />
	</body>
</html>