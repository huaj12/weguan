<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="top_logo"><!--top_logo begin-->
	<h1></h1>
	<p>助你找伴儿出去玩</p>
	<span><a href="javascript:void(0);" onclick="javascript:request();">邀请好友</a></span>
</div><!--top_logo end-->
<div id="requestDiv" style="display: none">
	<div class="share_wb">
		<!--share_wb begin-->
		<div class="area">
			<!--area begin-->
			<p>
				<img id="request_picurl" width="120" height="120" src="${jz:static('/images/weibo_request.jpg')}" />
			</p>
			<span><textarea name="" id="request_content" cols="" rows=""></textarea> </span>
		</div>
		<!--area end-->
		<div class="btn">
			<!--btn begin-->
			<div class="sending" id="request_btn_sending" style="display: none">
				<!--sending begin-->
				<div class="loading_icon">
				<img src="${jz:static('/images/loading_icon.gif')}" />
				</div>
				<strong>发送中...</strong>
			</div>
			<span id="request_btn"><a href="javascript:void(0);" onclick="sendRequest();">分享</a> </span>
		</div>
		<!--btn end-->
	</div>
	<!--share_wb end-->
</div>