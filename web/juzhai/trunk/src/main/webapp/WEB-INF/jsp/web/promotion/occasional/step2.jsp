<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>偶遇你的ta</title>
		<link href="${jzr:static('/css/jz_promotion.css')}" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
		</head>
		<body>
		
			<div class="app"><!--APP begin-->
			<div class="sen_area"><!--sen_area begin-->
			<div class="sen3"><!--sen1 begin-->
			<div class="loading_icon"></div>
			<div class="loading_text" id="loading_text1"><img src="${jzr:static('images/promotion/loading_txt1.jpg')}" /></div>
			<div class="loading_text" id="loading_text2" style="display:none"><img src="${jzr:static('images/promotion/loading_txt2.jpg')}" /></div>
			<div class="loading_text" id="loading_text3" style="display:none"><img src="${jzr:static('images/promotion/loading_txt3.jpg')}" /></div>
			<div class="loading_text" id="loading_text4" style="display:none"><img src="${jzr:static('images/promotion/loading_txt4.jpg')}" /></div>
			<div class="loading_text" id="loading_text5" style="display:none"><img src="${jzr:static('images/promotion/loading_txt5.jpg')}" /></div>
			</div><!--sen1 end-->
			</div><!--sen_area end-->
			</div><!--APP end-->
			<script>
			var content="";
			jQuery.ajax({
				url : "/promotion/${path}/step3/",
				type : "get",
				dataType : "html",
				success : function(result) {
					content=result;
				},
				statusCode : {
					401 : function() {
						alert("系统异常");
					}
				}
			});
			var count=0;
			dynamic();
			function dynamic(){ 
			count++;
			loadText(count);
			if(count<6){
			 setTimeout(function(){dynamic();},1000);
			} 
			}
				function loadText(index){
					for(var i=1;i<6;i++){
						$("#loading_text"+i).hide();
					}
					$("#loading_text"+index).show();
					if(index==6){
						while(true){
							if(content!=""){
								break;
							}
						}
						$(".app").html(content);
					}
				}
			</script>
		</body>
</html>
