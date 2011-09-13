<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<div class="skin_top"><!--skin_top begin-->
	<div class="top_right"></div>
	<div class="top_left"></div>
	<div class="top_mid"><!--top_mid begin-->
		<div class="top_high_line"></div>
		<div class="zjxq fl"><a href="#">我最近想去</a></div>
		<div class="zjxq_input fl">
			<p class="l"></p><span><input id="addAct" name="actName"  type="text" actId="0" /></span>
			<a href="#" class="add" id="addActBtn">+</a>
			<a href="#" class="zhao">召</a>
			<a href="#" class="yao">邀</a>
			<p class="r"></p>
		</div>
		<div class="home fl"><p></p><a href="#">拒宅器</a></div>
		<div class="dy fr"><p class="l"></p><span><input id="subEmail" name="email" type="text" /></span><a href="#" id="subEmailBtn" onclick="subEmail('subEmail')">订阅</a></div>
		<div class="message fr"><a href="#">我的消息</a></div>
	</div><!--top_mid end-->
</div><!--skin_top end-->