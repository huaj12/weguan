<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="sen_area"><!--sen_area begin-->
	<div class="sen4"><!--sen1 begin-->
		<div class="photo"><img src="${jzr:userLogo(profile.uid,profile.newLogoPic,180)}" /></div>
		<div class="photo_right">
			<p>不久后，你会在<b>${content}</b>偶遇ta</p>
			<span><a href="http://www.51juzhai.com/home/${profile.uid}"></a></span>
		</div>
		<div class="logo"><a href="http://www.51juzhai.com"></a></div>
	</div><!--sen1 end-->
</div><!--sen_area end-->
