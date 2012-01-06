<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="jzr"
	uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<c:choose>
	<c:when test="${!empty act &&!empty logo}">
		<div class="share_box">
			<!--share_box begin-->
			<h2>邀请好友去&nbsp;${act.name }</h2>
			<div class="share_con">
				<!--share_con begin-->
				<p>
					<img src="${logo}" />
				</p>
				<div class="share_right">
					<!--share_right begin-->
					<div class="text_area">
						<textarea name="" id="invite_content" cols="" rows=""></textarea>
						<input type="hidden" id="invite_act_id" value="${act.id}" />
					</div>
					<div class="btn">
						<em>通过微博发布</em><a href="javascript:void(0);"
							onclick="send_invite()">发布</a>
					</div>
				</div>
				<!--share_right end-->
			</div>
			<!--share_con end-->
		</div>
		<!--share_box end-->
	</c:when>
	<c:otherwise>
		<div class="share_box2">
			<!--share_box2 begin-->
			<h2>邀请好友加入拒宅</h2>
			<div class="share_con">
				<!--share_con begin-->
				<div class="text_area">
					<textarea name="" id="invite_content" cols="" rows="">${message}</textarea>
					<input type="hidden" id="invite_act_id" value="${actId }" />
				</div>
				<div class="btn">
					<em>通过微博发布</em><a href="javascript:void(0);"
						onclick="send_invite()">发布</a>
				</div>
			</div>
			<!--share_con end-->
		</div>
		<!--share_box2 end-->
	</c:otherwise>
</c:choose>