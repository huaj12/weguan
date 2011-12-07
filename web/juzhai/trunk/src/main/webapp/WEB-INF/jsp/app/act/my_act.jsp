<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>我的拒宅</title>
		<c:set var="cssFile" value="/css/jz_${context.tpName}.css" />
		<link href="${jz:static(cssFile)}" rel="stylesheet" type="text/css" />
		<link href="${jz:static('/css/jquery.autocomplete.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<jsp:include page="/WEB-INF/jsp/common/app/${context.tpName}/top_logo.jsp" />
		<div class="main"><!--main begin-->
			<c:set var="page" value="myAct" scope="request" />
			<jsp:include page="/WEB-INF/jsp/app/common/app_header.jsp" />
			<div class="skin_body"><!--skin_body begin-->
				<div class="skin_top_bg"><!--content_bg begin-->
					<div class="content white"><!--content begin-->
						<div class="top"></div>
						<div class="mid"><!--mid begin-->
							<div class="pub_style"><!--pub_style begin-->
								<div class="title">
									<h2>我想去的拒宅项目(<em id="myActCnt">${pager.totalResults}</em>)</h2>
									<div class="or">
										<p>或者</p>
										<span><input name="" id="categoryAddAct"  type="text"   onfocus="if(this.value=='手动输入')this.value=''" onblur="if(this.value=='')this.value='手动输入'" value="手动输入"/></span><a id="_addMyActs" href="javascript:void(0);">推荐</a>
										<div class="ts" id="categoryAddActError" style="display:none"><em>感谢推荐!<br />我们审核后会加入拒宅器</em></div>
									</div>
									<a href="/app/showCategoryActs" class="more">去拒宅库添加</a>
								</div>
								<div class="box"><!--box begin-->
									<jsp:include page="my_act_list.jsp" />
								</div><!--box ends-->
							</div><!--pub_style end-->
						</div><!--mid end-->
						<div class="bot"></div>
					</div><!--content end-->
				</div><!--content_bg end-->
			</div><!--skin_body end-->
			<div class="skin_bottom"></div>
		</div><!--main end-->
		<jsp:include page="/WEB-INF/jsp/app/common/script/script.jsp" />
		<jsp:include page="/WEB-INF/jsp/app/common/artDialog/artDialog.jsp" />
		<script type="text/javascript" src="${jz:static('/js/module/my_act.js')}"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				
			});
		</script>
		<jsp:include page="/WEB-INF/jsp/app/common/script/app.jsp" />
		<jsp:include page="/WEB-INF/jsp/app/common/foot.jsp" />
	</body>
</html>