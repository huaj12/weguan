<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>我的拒宅</title>
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
											<c:choose>
												<c:when test="${status.count==1}"><c:set var="pClass" value="act_hot" /></c:when>
												<c:when test="${status.count==2}"><c:set var="pClass" value="joy" /></c:when>
												<c:when test="${status.count==3}"><c:set var="pClass" value="sport" /></c:when>
												<c:when test="${status.count==4}"><c:set var="pClass" value="travel" /></c:when>
												<c:when test="${status.count==5}"><c:set var="pClass" value="leisure" /></c:when>
												<c:when test="${status.count==6}"><c:set var="pClass" value="food" /></c:when>
											</c:choose>
											<p class="${pClass}">
												<span class="fl"></span>
												<a href="javascript:;" actCategoryId="${actCategory.id}"><c:out value="${actCategory.name}"/></a>
												<span class="fr"></span>
												<strong></strong>
											</p>
										</c:forEach>
									</div><!--rec_tab end-->
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
		<jsp:include page="/WEB-INF/jsp/common/app/foot.jsp" />
		<script type="text/javascript">
			$(document).ready(function(){
				
			});
		</script>
	</body>
</html>