<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="feedDiv" style="display: none">
	<div class="share_wb">
		<!--share_wb begin-->
		<div class="area">
			<!--area begin-->
			<p>
				<img id="feed_picurl" src="images/pic.png" />
			</p>
			<span><textarea name="" id="feed_content" cols="" rows=""></textarea> </span>
		</div>
		<!--area end-->
		<div class="btn">
			<!--btn begin-->
			<p>
				<b><input name="" type="checkbox" id="isFollow" value="1" /> </b><em>关注拒宅网</em>
			</p>
				<input id="feed_actId" value="" type="hidden" />
			<span><a href="#" onclick="sendfeed();">分享</a> </span>
		</div>
		<!--btn end-->
	</div>
	<!--share_wb end-->
</div>
