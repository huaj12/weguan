<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>邮件退订</title>
		<link href="${jz:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="main"><!--main begin-->
			<div class="top"><!--top begin-->
				<h1></h1>
				<div class="rk_menu"><!--rk_menu begin-->
					<a href="${tpMap[1].appUrl}" title="点击进入"><img src="${jz:static('/images/web/td_r1_c1.png')}" /></a>
					<a href="javascript:;" title="即将推出"><img src="${jz:static('/images/web/td_r1_c3.png')}" /></a>
					<a href="javascript:;" title="即将推出"><img src="${jz:static('/images/web/td_r1_c5.png')}" /></a>
					<a href="javascript:;" title="即将推出"><img src="${jz:static('/images/web/td_r1_c7.png')}" /></a>
					<!-- <a href="javascript:;" title="即将推出"><img src="${jz:static('/images/web/td_r1_c9.png')}" /></a> -->
				</div><!--rk_menu end-->
			</div><!--top end-->
			<div class="td_banner"><!--td_banner begin-->
				<p>您的订阅邮箱：${profile.email}</p>
				<span><a href="javascript:;" <c:if test="${!profile.subEmail}">class="unclick"</c:if>></a><em style="display:none;">已退订，再见了</em></span>
			</div><!--td_banner end-->
			<div class="bottom"><!--bottom begin-->
				<p>拒宅网&copy;2011 沪ICP备11031778号<a href="mailto:contact@51juzhai.com">联系我们</a><a href="http://weibo.com/51juzhai" target="_blank">官方微博</a><a href="mailto:feedback@51juzhai.com">意见反馈</a></p>
			</div><!--bottom end-->
		</div><!--main end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<script type="text/javascript">
			$(document).ready(function(){
				$("div.td_banner > span > a").bind("click", function(){
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
								$("div.td_banner > span > em").text("退订成功！").show();
								$("div.td_banner > span > a").addClass("unclick");
							}else if(result){
								//报错
								$("div.td_banner > span > em").text(result.errorInfo).show();
							}else {
								alert("系统异常！");
							}
						}
					});
					return false;
				});
			});
		</script>
	</body>
</html>