<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<div id="dialog-remove-act" style="display:none;">
	<div class="show_box"><!--show box-->
		<h2>确定不再想去么？</h2>
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
	<div class="login_box"><!--login_show_box begin-->
		<div class="box_left">
			<h2>加入拒宅</h2><em>找伴出去玩</em>
			<a href="/web/login/6?turnTo=[0]"><img src="${jzr:static('/images/web/login_btn.gif')}" /></a>
		</div>
		<div class="box_right">
			<b>你是这样的小宅么？</b><br />
			你微薄上有几百个粉丝，手机通讯录里有几百个联系人，但节假日仍要宅在家里。<br /><br />
			<b>拒宅网如何帮助小宅们找伴儿出去玩？</b><br />
			告诉拒宅网你的空闲时间、你想出去玩什么；就可能会有同样爱好的人约你出去。<br /><br />
			拒宅其实就这么简单；为何不就此开始呢？
		</div>
	</div><!--login_show_box end-->
</div>
<div id="dialog-message" style="display: none;">
	<div class="message_box"><!--share_box2 begin-->
		<h2>给&nbsp;{0}&nbsp;发私信</h2>
		<div class="message_box_con"><!--message_box_con begin-->
		<div class="text_area"><textarea name="content" cols="" rows=""></textarea></div>
		<div class="btn"><span>200字以内</span><a class="send" href="javascript:void(0);" onclick="javascript:sendMessage(this);" target-uid="[0]">发给ta</a><a href="javascript:void(0);" class="sending" style="display: none;">发送中...</a><b style="display:none;"></b></div>
		</div><!--message_box_con end-->
	</div><!--share_box2 end-->
</div>
