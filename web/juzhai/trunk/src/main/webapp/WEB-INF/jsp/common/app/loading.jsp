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
		<div class="main"><!--main begin-->
			<div class="skin_top_new"><!--skin_top_new begin--></div><!--skin_top_new end-->
			<div class="skin_body"><!--skin_body begin-->
				<div class="skin_top_bg_new"><!--content_bg begin-->
					<div class="loading"><!--loading begin-->
						<span><img src="${jz:static('/images/loading.gif')}" /></span>
						<p id="p1">正在开启拒宅器...</p>
						<p id="p2" style="display:none;">开始导入你的好友...</p>
						<p id="p3" style="display:none;">正在挖掘好友兴趣...</p>
						<p id="p4" style="display:none;">为你准备兴趣匹配...</p>
						<p id="p5" style="display:none;">开启你的拒宅通知...</p>
					</div><!--loading end-->
				</div><!--content_bg end-->
			</div><!--skin_body end-->
			<div class="skin_bottom"></div>
		</div><!--main end-->
		<script type="text/javascript" src="${jz:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
		<script type="text/javascript">
			var pCount = $("div.loading > p").size();
			var pNum = 1;
			setInterval(function() {
				$("div.loading > #p"+ pNum).fadeOut(200, function() {
					pNum = pNum == pCount ? 1 : (pNum + 1);
					$("div.loading > #p"+ pNum).fadeIn(200);
				});
			}, 3000);
		
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
		<jsp:include page="/WEB-INF/jsp/common/app/foot.jsp" />
	</body>
</html>
