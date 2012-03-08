<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><div class="face_show_box"><!--face_show_box begin-->
<div class="face_area"><img src="${jzr:static('/images/web2/face_show_face_r5_c1.jpg')}" /></div>
	<div class="area_right"><!--area_right begin-->
		<h2>您的头像未通过审核</h2>
		<p>为了保证社区氛围，只有通过头像审核的用户才能使用相关功能哦</p>
		<span>请勿上传卡通/宠物/明星等虚假头像</span>
		<a href="/profile/index/face">去更新头像</a>
	</div><!--area_right end-->
</div><!--face_show_box end-->
