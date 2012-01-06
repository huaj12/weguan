<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${!empty context.tpName}">
	<c:set value="/js/core/connect/${context.tpName}/${context.tpName}.js"
		var="s"></c:set>
	<script type="text/javascript" src="${jz:static(s)}"></script>
</c:if>
<!--微博分享2-->
				<div style="display: none" id="weibo_ivite" class="aui_state_focus"><div class="aui_outer">
				<table class="aui_border">
				<tbody>
				<tr><td class="aui_nw"></td><td class="aui_n"></td><td class="aui_ne"></td></tr>
				
				<tr><td class="aui_w"></td><td class="aui_c">
				<div class="aui_inner">
				<table class="aui_dialog">
				<tbody>
				<tr><td class="aui_header" colspan="2"><div class="aui_titleBar"><div class="aui_title" style="cursor: auto;">消息</div><a href="javascript:/*artDialog*/;" class="aui_close"></a></div></td></tr>
				<tr>
				  <td class="aui_main" style="width: auto; height: auto;">
				<div class="aui_content" style="padding:20px 25px;">
				<div class="share_box2"><!--share_box2 begin-->
				<h2>邀请好友加入拒宅</h2>
				<div class="share_con"><!--share_con begin-->
				<div class="text_area"><textarea name="" id="invite_content" cols="" rows=""></textarea>
					<input type="hidden" id="invite_act_id" value=""/>
				</div>
				<div class="btn"><em>通过微博发布</em><a href="javascript:void(0);" onclick="send_invite()">发布</a></div>
				</div><!--share_con end-->
				</div>
				<!--share_box2 end-->
</div>
</td></tr></tbody></table></div></td><td class="aui_e"></td></tr><tr><td class="aui_sw"></td><td class="aui_s"></td><td class="aui_se" style="cursor: auto;"></td></tr></tbody></table></div></div>
				
