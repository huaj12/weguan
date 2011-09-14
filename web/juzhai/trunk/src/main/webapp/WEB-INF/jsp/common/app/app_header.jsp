<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<div class="skin_top"><!--skin_top begin-->
	<div class="top_right"></div>
	<div class="top_left"></div>
	<div class="top_mid"><!--top_mid begin-->
		<div class="top_high_line"></div>
		<div class="zjxq fl"><a href="#">我最近想去</a></div>
		<div class="zjxq_input fl">
			<div class="select_menu"><!--select_menu begin-->
				<a href="#">打台球</a>
				<a href="#">打篮球</a>
				<a href="#">打三国杀</a>
				<a href="#">打高尔夫</a>
				<a href="#">打三国杀</a>
				<a href="#">打高尔夫</a>
				<a href="#">打三国杀</a>
				<a href="#">打高尔夫</a>
			</div><!--select_menu end-->
			<div class="error"><p>请输入完整信息信息</p></div>
			<p class="l"></p><span><input id="addAct" name="actName"  type="text" actId="0" /></span>
			<a href="#" class="add" id="addActBtn">+</a>
			<a href="#" class="zhao">召</a>
			<a href="#" class="yao">邀</a>
			<p class="r"></p>
		</div>
		<div class="home fl"><p></p><a href="#">拒宅器</a></div>
		<div class="dy fr">
			<div class="error">请输入完整信息</div>
			<p class="l"></p><span><input id="subEmail" name="email" type="text" /></span>
			<a href="#" id="subEmailBtn" onclick="subEmail('subEmail')">订阅</a>
		</div>
		<div class="message fr">
			<div class="message_num">
				<span>100111</span>
			</div>
			<a href="#">我的消息</a>
		</div>
	</div><!--top_mid end-->
</div><!--skin_top end-->