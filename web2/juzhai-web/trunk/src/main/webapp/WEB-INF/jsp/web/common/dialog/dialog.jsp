<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			<h2>登录拒宅网</h2>
			<div class="clear"></div>
					<a href="/web/login/6?turnTo=[0]"><img src="${jzr:static('/images/web2/login_btn.gif')}" /></a>
					<a href="/web/login/7?turnTo=[0]"><img src="${jzr:static('/images/web2/db_login_btn.gif')}" /></a>
			<c:choose>
					<c:when test="${not empty isQplus&&isQplus}">
						<a href="/qplus/loginDialog/9"><img src="${jzr:static('/images/web2/qq_login_btn.gif')}" /></a>
					</c:when>
					<c:otherwise>
						<a href="/web/login/8?turnTo=[0]"><img src="${jzr:static('/images/web2/qq_login_btn.gif')}" /></a>	
					</c:otherwise>
			</c:choose>
			
		</div>
		<form id="login-box-form">
			<input type="hidden" name="turnTo" value="[0]" />
			<div class="box_right">
				<div class="infor_x"><!--infor_x begin-->
					<h4>邮箱：</h4>
					<div id="form-account" class="input_ttc"><!--input begin-->
						<p class="l"></p><span class="w160"><input name="account" type="text" /></span><p class="r"></p>
					</div><!--input end-->
				</div><!--infor_x end-->
				<div class="infor_x"><!--infor_x begin-->
					<h4>密码：</h4>
					<div id="form-pwd" class="input_ttc"><!--input begin-->
						<p class="l"></p><span class="w160"><input name="password" type="password" /></span><p class="r"></p>
						<a href="/passport/getbackpwd"> 忘记密码?</a>
					</div><!--input end-->
				</div><!--infor_x end-->
				<div class="error"></div>
				<div class="btn"><a href="javascript:void(0);">登录</a></div>
				<div class="zc"><a href="/passport/register">注册</a></div>
			</div>
		</form>
	</div><!--login_show_box end-->
</div>
<div id="dialog-date" style="display:none;">
	<div class="send_area" login="${context.uid > 0}"><!--send_area begin-->
		<div class="date_title">想约<font>{0}</font>玩什么?</div>
		<div class="random_select"><p>没想好？</p><a href="javascript:void(0);" class="random ml0">帮我选一个</a></div>
		<div class="textarea"><textarea init-tip="写下你的约会计划..."></textarea></div>
		<div class="date_error" style="display:none;"></div>
		<div class="btn"><a href="javascript:void(0);">约ta</a></div>
		<div class="sending" style="display:none;"><a href="javascript:void(0);">发送中</a></div>
		<div class="kj">仅你们俩可见</div>
	</div><!--send_area end-->
</div>
<div id="dialog-response" style="display:none;">
	<div class="fy_show_box" login="${context.uid > 0}"><!--fy_show_box begin-->
		<h2>响应<font>{0}</font>的拒宅</h2>	
		<p>{1}</p>
		<div class="text_area"><textarea init-tip="响应附言：建议附上你的qq或手机(仅对方可见)"></textarea></div>
		<div class="btn_area">
			<div class="error" style="display:none;"></div>
			<span><a href="javascript:void(0);" class="resp-btn">我要响应</a></span>
			<span style="display:none;" class="sending"><a href="javascript:void(0);">发送中</a></span>
		</div>
	</div><!--fy_show_box end-->
</div><!--fy_show_box end-->
<div id="dialog-shield-uid" style="display:none;">
	<div class="show_box"><!--show box-->
		<h2>确定要屏蔽&nbsp;{0}&nbsp;么？</h2>
		<p>被你屏蔽的人将不能再给你发送任何消息</p>
	</div><!--show end-->
</div>

