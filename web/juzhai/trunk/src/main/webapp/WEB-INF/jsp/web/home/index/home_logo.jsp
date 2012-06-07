<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content_box w285 h158" ><!--content begin-->
	<div class="t"></div>
	<div class="m">
		<c:if test="${empty loginUser.logoPic && (loginUser.logoVerifyState == 3 || loginUser.logoVerifyState == 0)}">
			<div class="tips_upload"><!--tips_upload begin-->
				<a href="/profile/index/face" class="txt"><c:choose><c:when test="${loginUser.logoVerifyState == 3}">头像未过审核</c:when><c:otherwise>你还没有头像</c:otherwise></c:choose>,大家看不到你</a>
				<a href="javascript:void(0);" onclick="javascript:$(this).parent().remove();return false;" class="close"></a>
			</div><!--tips_upload end-->
			<div></div>
		</c:if>
		<div class="right_title"><h2>我的拒宅</h2></div>
		<div class="my_jz_infor"><!--my_jz_infor begin-->
			<div class="infor">
				<p><c:choose><c:when test="${postCount > 0}">我发布了<a href="/home/posts">${postCount}条拒宅</a></c:when><c:otherwise>我还未发布拒宅</c:otherwise></c:choose></p>
				<em>我关注了<a href="/home/interests">${interestCount}人</a></em>
				<em>个人档案:${completion}%<a href="/profile/index"><c:choose><c:when test="${completion==100}">修改</c:when><c:otherwise>完善</c:otherwise></c:choose></a></em>
				<em>拒宅偏好:<a href="/profile/preference">设置</a></em>
			</div>
			<div class="face">
				<p><a href="/home/${loginUser.uid}"><img src="${jzr:userLogo(loginUser.uid,loginUser.newLogoPic,80)}"  width="80" height="80"/></a></p>
				<c:choose>
					<c:when test="${loginUser.logoVerifyState == 1}"><span>审核中...</span></c:when>
					<c:when test="${loginUser.logoVerifyState == 3}"><em><a href="/profile/index/face">未通过审核</a></em></c:when>
				</c:choose>
				<!-- <div class="clear"></div> -->
			</div>
		</div><!--my_jz_infor end-->
	</div>
	<div class="t"></div>
</div><!--content end-->