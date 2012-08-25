<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<c:if test="${loginUser.gender==1&&!isopenrescueboy}">
	<div class="content_box w285 h158"><!--content begin-->
		<div class="t"></div>
		<div class="m">
			<div class="right_title"><h2>宅男自救器（抢先版）</h2></div>
				<div class="save_self_show">
					<p><img src="${jzr:static('/images/web2/save_self_pic.jpg')}" width="70" height="70" /></p>
					<span>闷骚宅男的脱宅利器,帮助你更有效的拒宅</span>
					<a href="/rescueboy">去看看 &lg;&lt;</a>
				</div>
		</div>
		<div class="t"></div>
	</div><!--content end-->
</c:if>