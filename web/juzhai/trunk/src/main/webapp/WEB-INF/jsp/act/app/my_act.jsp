<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>我的拒宅</title>
		<link href="${jz:url('/css/jz.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="main"><!--main begin-->
			<jsp:include page="/WEB-INF/jsp/common/app/app_header.jsp" />
			<div class="skin_body"><!--skin_body begin-->
				<div class="skin_top_bg"><!--content_bg begin-->
					<jsp:include page="/WEB-INF/jsp/common/app/app_prompt.jsp" />
					<jsp:include page="/WEB-INF/jsp/common/app/app_point.jsp" />
					<div class="content yellow"><!--content begin-->
						<div class="top"></div>
						<div class="mid"><!--mid begin-->
							<div class="jzq"><!--jzq begin-->
								<div class="interesting"><!--interesting begin-->
									<jsp:include page="my_act_list.jsp" />
								</div><!--interesting end-->
								<div class="rec_insterest"><!--rec_insterest begin-->
									<div class="rec_tab"><!--rec_tab begin-->
										<c:forEach var="actCategory" items="${hotCategoryList}" varStatus="status">
											<p class="act_hot">
												<span class="fl"></span>
												<a href="#"><c:out value="${actCategory.name}"/></a>
												<span class="fr"></span>
												<strong></strong>
											</p>
										</c:forEach>
										<!-- <p class="joy"><span class="fl"></span><a href="#">娱乐</a><span class="fr"></span><strong></strong></p>
										<p class="sport"><span class="fl"></span><a href="#">运动</a><span class="fr"></span><strong></strong></p>
										<p class="travel"><span class="fl"></span><a href="#">旅游</a><span class="fr"></span><strong></strong></p>
										<p class="leisure"><span class="fl"></span><a href="#">休闲</a><span class="fr"></span><strong></strong></p>
										<p class="food"><span class="fl"></span><a href="#">餐饮</a><span class="fr"></span><strong></strong></p> -->
									</div><!--rec_tab end-->
									<!--<a class="hot act_red"><span class="fl"></span><p>最热</p><span class="fr"></span><strong></strong></a>
									<a href="#" class="joy purple"><span class="fl"></span><p>娱乐</p><span class="fr"></span><strong></strong></a>
									<a href="#" class="sport yellow"><span class="fl"></span><p>运动</p><span class="fr"></span><strong></strong></a>
									<a href="#" class="travel green"><span class="fl"></span><p>旅游</p><span class="fr"></span><strong></strong></a>
									<a href="#" class="Leisure blue"><span class="fl"></span><p>休闲</p><span class="fr"></span><strong></strong></a>
									<a href="#" class="food  orange"><span class="fl"></span><p>餐饮</p><span class="fr"></span><strong></strong></a>
									-->
									<div class="rec_words hot"><!--rec_words begin-->
										<jsp:include page="hot_act_list.jsp" />
									</div><!--rec_words end-->
								</div><!--rec_insterest end-->
							</div><!--jzq end-->
						</div><!--mid end-->
						<div class="bot"></div>
					</div><!--content end-->
				</div><!--content_bg end-->
			</div><!--skin_body end-->
			<div class="skin_bottom"></div>
		</div><!--main end-->
		<jsp:include page="/WEB-INF/jsp/common/app/script/script.jsp" />
		<script type="text/javascript" src="${jz:url('/js/module/my_act.js')}"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				
			});
		</script>
	</body>
</html>