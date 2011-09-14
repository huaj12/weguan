<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<div class="top"></div>
<div class="mid"><!--mid begin-->
	<div class="jz_box"><!--jz_box begin-->
		<c:choose>
			<c:when test="feed.feedType.name=='SPECIFIC'">
				<div class="photo fl"><img src="${jz:url('/images/pic.png')}" /></div>
				<div class="infor fl"><!--infor begin-->
					<h2><span class="u">其实不想走</span><span class="w">最近想去</span><span class="v">打台球打台球打台球打</span></h2>
					<p>2011.12.25&nbsp;&nbsp;&nbsp;&nbsp;上海浦东</p>
					<a href="#" class="want">我也想去</a><a href="#" class="dwant">没兴趣</a>
				</div><!--infor end-->
			</c:when>
			<c:when test="feed.feedType.name=='GRADE'">
				<div class="photo fl"><img src="images/pic.png" /></div>
				<div class="df fl"><!--infor begin-->
					<h2><span class="w">你觉得</span><span class="u">其实不想走</span><span class="w">有多宅？</span></h2>
					<div class="star"><!--star begin-->
						<span class="hover"></span>
						<span class="hover"></span>
						<span class="hover"></span>
						<span class="link"></span>
						<span class="link"></span>
						<p class="zai">很宅！很宅！</p>
					</div><!--star end-->
				</div><!--infor end-->
			</c:when>
			<c:otherwise>
			<!-- 提示页面 -->
			</c:otherwise>
		</c:choose>
	</div><!--jz_box end-->
</div><!--mid end-->
<div class="bot"></div>
<c:if test="${feed!=null&&feed.feedType.name=='GRADE'}">
	<div class="next_btn"><a href="#">跳  过</a></div>
</c:if>