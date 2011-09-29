<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>新手引导</title>
		<link href="${jz:url('/css/jz.css')}" rel="stylesheet" type="text/css" />
		
	</head>
	<body>
		<div class="main"><!--main begin-->
			<div class="skin_top_new"></div>
			<div class="skin_body">
				<!--skin_body begin-->
				<div class="skin_top_bg_new">
					<jsp:include page="/WEB-INF/jsp/common/app/app_guide_header.jsp" />
					<div class="content white mt50"><!--content begin-->
						<div class="top"></div>
						<div class="mid"><!--mid begin-->
							<div class="basic_yd sec_rss"><!--basic_yd begin-->
								<h3 class="fl"></h3>
								<div class="sec fr"><!--sec begin-->
									<h2>好了，你已经添加了${userActCount}个拒宅兴趣!</h2>
									<h4>当你的同城好友有同样的兴趣时，我们会在下班前告知您</h4>
									<div class="add_dy">
										<strong>
											<input name="" type="text" />
										</strong>
										<a href="#" class="add complete">订阅</a>
										<div class="error" style="display:none"></div>
									</div>
								</div><!--sec end-->
							</div><!--basic_yd end-->
						</div><!--mid end-->
						<div class="bot"></div>
						<!-- <div class="round_num">
							<c:forEach begin="1" end="${totalStep}" var="index">
								<span <c:if test="${step==index}">class="hover"</c:if>></span>
							</c:forEach>
						</div> -->
						<form id="nextForm" action="/app/guide/next" method="post">
						</form>
						<div class="next_btn">
							<a href="#" class="complete">完成</a>
						</div>
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
