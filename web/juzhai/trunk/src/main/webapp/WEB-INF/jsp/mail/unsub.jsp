<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>邮件退订</title>
		<link href="${jz:url('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="main"><!--main begin-->
			<div class="top"><!--top begin-->
				<h1></h1>
				<div class="rk_menu"><!--rk_menu begin-->
					<span><img src="images/entrance.gif" />
					</span> <a href="#" title="开心拒宅"><img src="images/td_r1_c1.png" />
					</a> <a href="#" title="人人拒宅"><img src="images/td_r1_c3.png" />
					</a> <a href="#" title="微博拒宅"><img src="images/td_r1_c5.png" />
					</a> <a href="#" title="安卓拒宅"><img src="images/td_r1_c7.png" />
					</a> <a href="#" title="IPHONE拒宅"><img src="images/td_r1_c9.png" />
					</a>
				</div><!--rk_menu end-->
			</div><!--top end-->
			<div class="td_banner"><!--td_banner begin-->
				<p>您的订阅邮箱：${profile.email}</p>
				<span><a href="#" <c:if test="${!profile.subEmail}">class="unclick"</c:if>></a><em style="display:none;">已退订，再见了</em></span>
			</div><!--td_banner end-->
			<div class="bottom"><!--bottom begin-->
				<a href="#">拒宅工作组: max@51juzhai.com</a>
				<p>2011 开心网 京ICP证080482号 京公网安备110000000003号</p>
			</div><!--bottom end-->
		</div><!--main end-->
		<jsp:include page="/WEB-INF/jsp/common/web/script/script.jsp" />
		<script type="text/javascript" src="${jz:url('/js/module/home.js')}"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$("div.td_banner > a").bind("click", function(){
					if($(this).hasClass("unclick")){
						return false;
					}
					jQuery.ajax({
						url: "/unsubEmail",
						type: "post",
						data: {"token":"${token}", uid:"${profile.uid}" },
						dataType: "json",
						success: function(result){
							if(result&&result.success){
								//成功
								$("div.td_banner > em").text("退订成功！").show();
								$("div.td_banner > a").addClass("unclick");
							}else if(result){
								//报错
								$("div.td_banner > em").text(result.errorInfo).show();
							}else {
								alert("系统异常！");
							}
						}
					});
				});
			});
		</script>
	</body>
</html>