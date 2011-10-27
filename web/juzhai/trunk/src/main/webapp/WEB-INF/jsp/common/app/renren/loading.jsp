<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>loading...</title>
		<link href="${jz:static('/css/jz.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<jsp:include page="/WEB-INF/jsp/common/app/script/script.jsp" />
		<%String sessionKey=request.getParameter("xn_sig_session_key");
		if(sessionKey==null){
			%>
			<script type="text/javascript" src="${jz:static('/js/core/renren-gc.js')}"></script>
			 <script type="text/javascript">
				  var uiOpts = {
					  url : "http://graph.renren.com/oauth/authorize",
					  display : "iframe",
					  params : {"response_type":"token","client_id":"163941"},
					  onSuccess: function(r){
					    top.location = "http://apps.renren.com/juzhaiqi/";
					  },
					  onFailure: function(r){} 
				  };
				  Renren.ui(uiOpts);
			  </script>
			<%
		}else{
			%>
			<div class="main"><!--main begin-->
			<div class="skin_top_new"><!--skin_top_new begin-->
			</div><!--skin_top_new end-->
			<div class="skin_body"><!--skin_body begin-->
				<div class="skin_top_bg_new"><!--content_bg begin-->
					<div class="loading"><!--loading begin-->
						<span><img src="${jz:static('/images/loading.gif')}" /></span>
						<p></p>
					</div><!--loading end-->
				</div><!--content_bg end-->
			</div><!--skin_body end-->
			<div class="skin_bottom"></div>
		</div><!--main end-->
			<script type="text/javascript">
			$(document).ready(function(){
				var data=${data};
				jQuery.ajax({
					url: "/access",
					type: "get",
					data: data,
					dataType: "json",
					success: function(result){
						if(result && result.success){
							location.href = result.result;
						}else {
							alert("异常操作");
						}
					}
				});
			});
		</script>
			<%
		}
		%>
		
		<jsp:include page="/WEB-INF/jsp/common/app/foot.jsp" />
	</body>
</html>
