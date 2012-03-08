<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="face_show_box"><!--face_show_box begin-->
	<div class="face_area"><img src="${jzr:static('/images/web2/face_show_face_r1_c1.jpg')}" /></div>
	<div class="area_right"><!--area_right begin-->
		<h2>请上传真实头像</h2>
		<p>为了保证社区氛围，只有通过头像审核的用户才能使用相关功能哦</p>
		<span></span>
		<a href="/profile/index/face">去更新头像</a>
	</div><!--area_right end-->
</div><!--face_show_box end-->