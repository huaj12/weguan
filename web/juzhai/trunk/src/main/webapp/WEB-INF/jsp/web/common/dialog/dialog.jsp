<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<div id="dialog-remove-act" style="display:none;">
	<div class="show_box"><!--show box-->
		<h2>确定不再想去&nbsp;{0}&nbsp;么？</h2>
		<p>你将不再收到相关邀请。</p>
	</div><!--show end-->
</div>
<div id="dialog-confirm" style="display:none;">
	<div class="show_box"><!--show box-->
		<h2>{0}</h2>
	</div><!--show end-->
</div>
<div id="dialog-success" style="display:none">
	<div class="show_box_auto"><!--show_box_auto begin-->
		<h2>{0}</h2>
	</div>
</div>
<div id="dialog-login" style="display:none;">
	<div class="login_show_box_1"><!--login_show_box begin-->
		<h2>加入拒宅</h2>
		<div class="link">
			<a href="/web/login/6?turnTo=[0]"><img src="${jzr:static('/images/web/weibo_btn.gif')}" /></a>
		</div>
	</div>
</div>
<div id="dialog-message" style="display: none;">
	<div class="message_box"><!--share_box2 begin-->
		<h2>给&nbsp;{0}&nbsp;发私信&nbsp;&nbsp;(200字以内)</h2>
		<div class="message_box_con"><!--message_box_con begin-->
		<div class="text_area"><textarea name="content" cols="" rows=""></textarea></div>
		<div class="btn"><a class="send" href="javascript:void(0);" onclick="javascript:sendMessage(this);" target-uid="[0]">发给ta</a><a href="javascript:void(0);" class="sending" style="display: none;">发送中...</a><b style="display:none;"></b></div>
		</div><!--message_box_con end-->
	</div><!--share_box2 end-->
</div>
