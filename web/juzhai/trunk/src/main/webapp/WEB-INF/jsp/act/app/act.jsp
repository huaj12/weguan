<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>无标题文档</title>
		<link href="${jz:static('/css/jz.css')}" rel="stylesheet" type="text/css" />
		<link href="${jz:static('/css/jquery.autocomplete.css')}" rel="stylesheet" type="text/css" />
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
							<div class="project_sub"><!--project_sub begin-->
								<div class="photo fl"><img src="${jz:actLogo(act.id,act.logo,120)}" /></div>
								<div class="infor fl"><!--infor begin-->
									<h2><c:out value="${act.name}" /></h2>
									<h5><c:out value="${act.intro}" /><!-- <a href="#">详细</a> --></h5>
									<div class="ytj" <c:if test="${!hasAct}">style="display:none;"</c:if>>
										<p></p><span><em>已添加</em><strong>|</strong><a href="javascript:;" actid="${act.id}" actname="${act.name}">取消</a></span><p></p>
									</div>
									<a href="javascript:;" class="want btn" actid="${act.id}" <c:if test="${hasAct}">style="display:none;"</c:if>>我想去+1</a><c:if test="${!isShield}"><a href="javascript:;" class="zjl btn" actname="${act.name}">发布召集令</a></c:if><a href="javascript:;" class="yqhy btn" actname="${act.name}">邀请好友</a>
								</div><!--infor end-->
								<div class="wantgolist"><!--wantgolist begin-->
									<div class="title" actid="${act.id}"><!--title begin-->
										<h3>想去的</h3><em></em>
										<a href="javascript:;" class="active" id="friendUser"><p class="l"></p><span>好友(${fUserActCount})</span><p class="r"></p></a>
										<a href="javascript:;" class="link" id="allUser"><p class="l"></p><span>所有人(${userActCount})</span><p class="r"></p></a>
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
		<jsp:include page="/WEB-INF/jsp/common/app/artDialog/artDialog.jsp" />
		<script type="text/javascript" src="${jz:static('/js/module/act.js')}"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				
			});
		</script>
		<script type="text/javascript" src="${jz:static('/js/base/kaixin_plugin.js')}"></script>
		<jsp:include page="/WEB-INF/jsp/common/app/foot.jsp" />
	</body>
</html>