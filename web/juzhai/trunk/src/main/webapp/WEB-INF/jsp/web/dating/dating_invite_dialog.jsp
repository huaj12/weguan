<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="jzr"
	uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<div class="share_box2">
	<!--share_box2 begin-->
	<h2>邀请好友加入拒宅</h2>
	<div class="share_con">
		<!--share_con begin-->
		<div class="text_area">
			<textarea name="" id="invite_content" cols="" rows="">${message}</textarea>
			<input type="hidden" id="invite_act_id" value="" />
		</div>
		<div class="btn">
			<em>通过微博发布</em><a href="javascript:void(0);" onclick="send_invite()">发布</a>
		</div>
	</div>
	<!--share_con end-->
</div>
<!--share_box2 end-->
