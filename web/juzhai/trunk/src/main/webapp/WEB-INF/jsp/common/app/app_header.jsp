<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="skin_top"><!--skin_top begin-->
	<div class="top_right"></div>
	<div class="top_left"></div>
	<div class="top_mid"><!--top_mid begin-->
		<div class="top_high_line"></div>
		<div class="zjxq fl"><!--最近想去 begin-->
			<a href="/app/myAct" class="wantgo" title="管理我的拒宅兴趣">我想找伴去</a>
		</div><!--最近想去 end-->
		<div class="zjxq_input fl"><!--zjxq_input begin-->
			<div id="headAddActError" class="error" style="display:none"><p>请输入完整信息信息</p></div>
			<p class="l"></p><span><input id="addAct" type="text" /></span>
			<a href="javascript:;" class="btn add" id="addActBtn" title="添加为我的兴趣">+</a>
			<a href="javascript:;" class="btn zhao" title="向好友发布动态">召</a>
			<a href="javascript:;" class="btn yao" title="邀请指定好友">邀</a>
			<p class="r"></p>
		</div><!--zjxq_input end-->
		<div class="home fl"><p></p><a href="/app/index">拒宅器</a></div>
		<div class="dy fr">
			<div class="error" style="display:none">请输入完整信息</div>
			<p class="l"></p>
			<span><input id="subEmail" type="text" <c:if test="${profile.subEmail}">value="${profile.email}"</c:if> initmsg="用email接收拒宅邀请" /></span>
			<a href="javascript:;">订阅</a>
		</div>
		<div class="message fr"><!--我的消息 begin-->
			<div class="message_num" style="display:none">
				<span></span>
			</div>
			<a href="/msg/showUnRead" title="查看我的拒宅消息">我的消息</a>
		</div><!--我的消息 end-->
	</div><!--top_mid end-->
</div><!--skin_top end-->