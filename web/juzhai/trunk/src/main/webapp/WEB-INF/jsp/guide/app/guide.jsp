<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>新手引导</title>
		<link href="${jz:url('/css/jz.css')}" rel="stylesheet" type="text/css" />
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
							<div class="basic_yd sec_sport"><!--basic_yd begin-->
								<h3 class="fl"></h3>
								<div class="sec fr"><!--sec begin-->
									<h2>你感兴趣的${actCategory.name}</h2>
									<div id="acts">
										<c:forEach var="act" items="${actList }">
											<p><span class="fl"></span><a href="#" class="key_words" actId="${act.id}"><c:out value="${act.name}" /></a><span class="fr"></span><em></em></p>
										</c:forEach>
									</div>
									<div class="add_insterest">
										<strong><input id="actName" type="text" /></strong>
										<a href="#" class="add">添加</a>
										<div id="actNameError" class="error hidden">拒宅兴趣字数控制在1－10个中文内！</div>
									</div>
								</div><!--sec end-->
							</div><!--basic_yd end-->
						</div><!--mid end-->
						<div class="bot"></div>
						<div class="round_num"><!--round_num begin-->
							<span <c:if test="${step==1}">class="hover"</c:if>></span>
							<span <c:if test="${step==2}">class="hover"</c:if>></span>
							<span <c:if test="${step==3}">class="hover"</c:if>></span>
							<span <c:if test="${step==4}">class="hover"</c:if>></span>
						</div><!--round_num end-->
						<form id="nextForm" action="/app/guide/next" method="post">
						</form>
						<div class="next_btn"><a href="#">下一步</a></div>
					</div><!--content end-->
				</div><!--content_bg end-->
			</div><!--skin_body end-->
			<div class="skin_bottom"></div>
		</div><!--main end-->
		<jsp:include page="/WEB-INF/jsp/common/app/script/script.jsp" />
		<script type="text/javascript" src="${jz:url('/js/module/guide.js')}"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				
			});
		</script>
	</body>
</html>
