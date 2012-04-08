<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:choose>
	<c:when test="${not empty profile && not empty content}">
		<div class="sen_area"><!--sen_area begin-->
			<div class="sen4"><!--sen1 begin-->
				<div class="photo"><a href="http://www.51juzhai.com/?app"><img src="${jzr:userLogo(profile.uid,profile.logoPic,180)}" /></a></div>
				<div class="photo_right">
					<p>不久后，你会在<b>${content}</b>偶遇ta</p>
				</div>
				<div class="logo"><a href="http://www.51juzhai.com/?app">去拒宅网偶遇更多&gt;&gt;</a></div>
			</div><!--sen1 end-->
		</div><!--sen_area end-->
	</c:when>
	<c:otherwise>
		<div class="sen_area"><!--sen_area begin-->
			<div class="error"><!--error begin-->
				<em><img src="${jzr:static('/images/promotion/error_icon.jpg')}" /></em>
				<p><img src="${jzr:static('/images/promotion/error_txt.jpg')}" /></p>
				<a href="http://www.51juzhai.com">先去拒宅网看看吧</a>
			</div><!--error end-->
		</div><!--sen_area end-->
	</c:otherwise>
</c:choose>