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
			<a href="/web/login/6?turnTo=[0]"><img src="${jzr:static('/images/web2/login_btn.gif')}" /></a>
			<a href="/web/login/7?turnTo=[0]"><img src="${jzr:static('/images/web2/db_login_btn.gif')}" /></a>
			<a href="/web/login/8?turnTo=[0]"><img src="${jzr:static('/images/web2/qq_login_btn.gif')}" /></a>
		</div>
		<div class="box_right">
			<b>你是这样的小宅么？</b><br />
			你qq上有上百个好友，微博上有几百个粉丝；但在节假日里，却找不到人陪你出去玩。<br /><br />
			<b>拒宅网助你脱宅：</b><br />
			我们搭建了一个找伴儿出去玩的社区。在这里小宅们说出各种新奇、有趣的拒宅好主意；大家交流，认识，并相约一起出去玩。<br />
		</div>
	</div><!--login_show_box end-->
</div>
<div id="dialog-message" style="display: none;">
	<div class="message_box" login="${context.uid > 0}"><!--share_box2 begin-->
		<h2>给&nbsp;{0}&nbsp;发私信</h2>
		<div class="message_box_con"><!--message_box_con begin-->
		<div class="text_area"><textarea name="content" cols="" rows=""></textarea></div>
		<div class="btn"><span>200字以内</span><a class="send" href="javascript:void(0);" onclick="javascript:sendMessage(this);return false;" target-uid="[0]">发给ta</a><a href="javascript:void(0);" class="sending" style="display: none;">发送中...</a><b style="display:none;"></b></div>
		</div><!--message_box_con end-->
	</div><!--share_box2 end-->
</div>
<div id="dialog-date" style="display:none;">
	<div class="send_area" login="${context.uid > 0}"><!--send_area begin-->
		<div class="date_title">想约<font>{0}</font>玩什么?</div>
		<div class="random_select"><p>没想好？</p><a href="javascript:void(0);" class="random ml0">帮我选一个</a></div>
		<div class="textarea"><textarea init-msg="写下你的约会计划..."></textarea></div>
		<div class="date_error" style="display:none;"></div>
		<div class="btn"><a href="javascript:void(0);">约ta</a></div>
		<div class="sending" style="display:none;"><a href="javascript:void(0);">发送中</a></div>
		<div class="kj">仅你们俩可见</div>
	</div><!--send_area end-->
</div>
