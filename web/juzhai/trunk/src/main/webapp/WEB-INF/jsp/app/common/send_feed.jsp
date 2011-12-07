<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/jsp/app/common/artDialog/artDialog.jsp" />
<div id="feedDiv" style="display: none">
	<div class="share_wb">
		<!--share_wb begin-->
		<div class="area">
			<!--area begin-->
			<p>
				<img id="feed_picurl" src="" />
			</p>
			<span><textarea name="" id="feed_content" cols="" rows=""></textarea>
			</span>
		</div>
		<!--area end-->
		<div class="btn">
			<!--btn begin-->
			<p>
				<b><input name="" type="checkbox" id="isFollow" value="1" /> </b><em>关注拒宅网</em>
			</p>
			<div class="sending" id="feed_btn_sending" style="display: none">
				<!--sending begin-->
				<div class="loading_icon">
					<img src="${jz:static('/images/loading_icon.gif')}" />
				</div>
				<strong>发送中...</strong>
			</div>
			<!--sending end-->
				<input id="feed_actId" value="" type="hidden" /> <span id="feed_btn"><a
				href="javascript:void(0);" onclick="sendfeed();">分享</a> </span>
		</div>
		<!--btn end-->
	</div>
	<!--share_wb end-->
</div>
