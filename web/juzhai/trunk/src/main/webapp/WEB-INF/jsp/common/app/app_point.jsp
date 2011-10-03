<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<div class="integral"><p>积分:${point}</p><a href="javascript:;" onclick="javascript:$('div.al_box_s1').fadeIn(500);"></a></div>
<div class="alert_box al_box_s1" style="display:none;"><!--alert_box begin-->
	<div class="box_top"></div>
	<div class="box_mid"><!--box_mid begin-->
		<div class="title"><h2>拒宅积分的用处</h2><a href="javascript:;" class="close" onclick="javascript:$('div.al_box_s1').fadeOut(500);"></a></div>
		<p>
			查看拒宅推荐信息 -10分<br />
			查看拒宅邀请信息 -20分<br />
			<span>你可通过如下方式获得积分</span><br />
			每天登陆 +5分<br />
			添加拒宅兴趣 +5分<br />
			点击“想去” +5分<br />
			订阅邮箱 +20分<br />
			发布拒宅召集令 +20分<br />
			成功邀请一个好友 +10分<br />
			在我的消息中立即响应 +10分<br />
			在我的消息主动联系对方 +10分
		</p>
	</div><!--box_mid end-->
	<div class="box_bot"></div>
</div><!--alert_box end-->
