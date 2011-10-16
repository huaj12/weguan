<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>拒宅网</title>
		<link href="${jz:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
		<link href="${jz:static('/css/skins/blue.css')}" rel="stylesheet" type="text/css" />
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
			<div class="index_banner"><!--td_banner begin-->
				<a href="javascript:;" onclick="javascript:showEntrance();"></a>
			</div><!--td_banner end-->
			<div class="intru"><!--intru begin-->
				<div class="index_icon_item"><!--index_icon_item begin-->
					<div class="content_auto">
						<h2 class="icon1"></h2>
						<span>不想宅在家里，但又不知出门做什么好？</span>
						<p>在拒宅器中能看到好友最近都有哪些出去玩的计划如果感兴趣可以一键响应。</p>
					</div>
				</div><!--index_icon_item end-->
				<div class="line"></div>
				<div class="index_icon_item"><!--index_icon_item begin-->
					<div class="content_auto">
						<h2 class="icon2"></h2>
						<span>想去逛街，却又不知道哪个好友愿意同去？</span>
						<p>当有好友响应了你的拒宅兴趣，或好友添加的兴趣与你的兴趣相匹配时，拒宅器都会通知你。</p>
					</div>
				</div><!--index_icon_item end-->
				<div class="line"></div>
				<div class="index_icon_item"><!--index_icon_item begin-->
					<div class="content_auto">
						<h2 class="icon3"></h2>
						<span>平时工作繁忙，常忘了为周末做打算？</span>
						<p>拒宅器每周五为您提供为您定制的拒宅简报。让你看看这周朋友们都有什么拒宅计划。</p>
					</div>
				</div><!--index_icon_item end-->
			</div><!--intru end-->
			<div class="bottom"><!--bottom begin-->
				<p>拒宅网&copy;2011 沪ICP备11031778号<a href="mailto:contact@51juzhai.com?subject=联系我们">联系我们</a><a href="http://weibo.com/51juzhai" target="_blank">官方微博</a><a href="mailto:feedback@51juzhai.com?subject=意见反馈">意见反馈</a></p>
			</div><!--bottom end-->
		</div><!--main end-->
		<jsp:include page="/WEB-INF/jsp/common/app/foot.jsp" />
		<script type="text/javascript" src="${jz:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
		<script type="text/javascript" src="${jz:static('/js/artDialog/jquery.artDialog.js')}"></script>
		<script type="text/javascript">
			function showEntrance(){
				$.dialog({
					content: document.getElementById('entrance'),
					top:"50%",
					fixed: true,
					lock: true,
					title:"请选择一个拒宅入口",
					id: 'entrance_box',
					padding: 0
				});
			}
		</script>
		<div class="alert_box" id="entrance" style="display: none"> <!--alert_box begin-->
			<div class="rk">
				<a href="${tpMap[1].appUrl}" class="kaixin" title="点击进入">开心拒宅</a>
				<a href="#" class="cs_renren" title="即将推出">人人拒宅</a>
				<a href="#" class="cs_weibo" title="即将推出">微博拒宅</a>
				<a href="#" class="cs_anzuo" title="即将推出">安卓拒宅</a>
			</div>

</div> <!--alert_box end-->
	</body>
</html>
