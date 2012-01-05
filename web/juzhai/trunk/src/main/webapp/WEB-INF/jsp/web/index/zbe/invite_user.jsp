<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${inviteUserList!=null}">
	<div class="zbr_yq"><!--zbr_yq begin-->
		<div class="title">邀请好友加入拒宅</div>
		<div class="friend_list" currentpage="1" totalpage="${totalPage}"><!--friend_list begin-->
			<ul>
				<jsp:include page="invite_user_list.jsp" />
			</ul>
		</div><!--friend_list end-->
		<div class="btn_area"><!--btn_area begin-->
			<p><a href="javascript:void(0);" id="showMoreInvite">查看更多</a></p>
			<p id="showMoreInviteLoading" style="display: none;">正在加载中...</p>
			<span><a href="javascript:void(0);">邀请他们</a></span>
			<em style="display:none;">每次邀请不要超过10个人哦</em>
		</div><!--btn_area end-->
	</div><!--zbr_yq end-->
</c:if>