<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>头像管理</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script>
		function passLogoAll(){
			var ids="";
			$('input[name=uids]').each(function(){
				if(this.value!=null&&this.value!=''){
				 ids=ids+this.value+",";
				}
			});
			jQuery.ajax({
				url : "/cms/profile/passLogos",
				type : "get",
				data : {
					"uids" : ids
				},
				dataType : "json",
				success : function(result) {
					if (result.success!=null&&result.success) {
						alert("批量处理成功");
					} else {
						alert("批量处理失败");
					}
				},
				statusCode : {
					401 : function() {
						alert("请先登陆");
					}
				}
			});
		}
	function passLogo(uid, obj){
		var nickname = $(obj).attr("nickname");
		//if(confirm("确认要通过 " + nickname + " 的头像吗？")){
			jQuery.ajax({
				url : "/cms/profile/passLogo",
				type : "post",
				data : {"uid" : uid},
				dataType : "json",
				success : function(result) {
					if (result.success!=null&&result.success) {
						$("#pass-logo-" + uid).removeAttr("onclick").text("已通过");
					} else {
						alert(result.errorInfo);
					}
				},
				statusCode : {
					401 : function() {
						alert("请先登陆");
					}
				}
			});
	//	}
	}
	function denyLogo(uid){
		jQuery.ajax({
			url : "/cms/profile/denyLogo",
			type : "post",
			data : {"uid" : uid},
			dataType : "json",
			success : function(result) {
				if (result.success!=null&&result.success) {
					$("#deny-logo-" + uid).removeAttr("onclick").text("已拒绝");
					$("#uids-" + uid).val("");
				} else {
					alert(result.errorInfo);
				}
			},
			statusCode : {
				401 : function() {
					alert("请先登陆");
				}
			}
		});
	}
	function addHighQuality(uid){
		jQuery.ajax({
			url : "/cms/profile/addHighQuality",
			type : "post",
			data : {"uid" : uid},
			dataType : "json",
			success : function(result) {
				if (result.success!=null&&result.success) {
					$("#add-high-quality-" + uid).removeAttr("onclick").text("已加入");
				} else {
					alert(result.errorInfo);
				}
			},
			statusCode : {
				401 : function() {
					alert("请先登陆");
				}
			}
		});
	}
	function isRealPic(imgUrl){
		jQuery.ajax({
			url : "/cms/profile/realPic",
			type : "get",
			data : {"imgUrl" : imgUrl},
			dataType : "json",
			success : function(result) {
				if (result.result!=null) {
					if(result.result){
						alert("真实图片");
					}else{
						alert("网络图片");
					}
				} else {
					alert("搜索图片失败");
				}
			},
			statusCode : {
				401 : function() {
					alert("请先登陆");
				}
			}
		});
	}
	function ignoreLogo(uid){
		jQuery.ajax({
			url : "/cms/profile/ignoreLogo",
			type : "post",
			data : {"uid" : uid},
			dataType : "json",
			success : function(result) {
				if (result.success!=null&&result.success) {
					$("#ignore-logo-" + uid).removeAttr("onclick").text("已忽略");
				} else {
					alert(result.errorInfo);
				}
			},
			statusCode : {
				401 : function() {
					alert("请先登陆");
				}
			}
		});
	}
</script>
</head>
<body>
	<h2>
		<c:choose>
			<c:when test="${type == 'listIgnoreLogo'}">已忽略</c:when>
			<c:when test="${type == 'listVerifyingLogo'}">待审核 <input type="button" value="通过本页头像" onclick="passLogoAll();" /></c:when>
			<c:when test="${type == 'listVerifiedLogo'}">已通过</c:when>
			<c:when test="${type == 'listUnVerifiedLogo'}">未通过</c:when>
		</c:choose>头像列表
	</h2>
	<table border="0" cellspacing="4">
		<tr>
			<td colspan="7">
				<c:forEach var="pageId" items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/profile/${type}?pageId=${pageId}">${pageId}</a>
						</c:when>
						<c:otherwise>
							<strong>${pageId}</strong>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</td>
		</tr>
		<tr style="background-color: #CCCCCC;">
			<td width="50">id</td>
			<td width="150">用户昵称</td>
			<td width="50">性别</td>
			<td width="100">城市</td>
			<td width="100">关注</td>
			<td width="180">用户头像</td>
			<td width="200">处理</td>
		</tr>
		<c:forEach var="profile" items="${profileList}" >
			<tr>
				<td>${profile.uid}</td>
				<td><c:out value="${profile.nickname}" /></td>
				<td><c:choose><c:when test="${profile.gender == 1}">男</c:when><c:otherwise>女</c:otherwise></c:choose></td>
				<td><c:out value="${jzd:cityName(profile.city)}" /></td>
				<td><a href="javascript:void(0);" id="robot-interest-${profile.uid}" class="robot-interest"  city-id="${profile.city}" post-id="${profile.uid}" target-uid="${profile.uid}">关注</a></td>
				<td><img src="${jzr:userLogo(profile.uid, profile.newLogoPic, 180)}" width="180" height="180"/></td>
				<td>
					<c:if test="${type != 'listVerifiedLogo'}">
						<a href="javascript:void(0);" onclick="passLogo(${profile.uid}, this)" nickname="${jzu:htmlOut(profile.nickname)}" id="pass-logo-${profile.uid}">通过</a><br />
						<a href="javascript:void(0);" onclick="addHighQuality(${profile.uid})" id="add-high-quality-${profile.uid}">加入优质用户</a><br />
					</c:if>
					<c:if test="${type == 'listVerifyingLogo'}">
						<a href="javascript:void(0);" onclick="denyLogo(${profile.uid})" id="deny-logo-${profile.uid}">拒绝</a><br />
						<a href="javascript:void(0);" onclick="isRealPic('${jzr:userLogo(profile.uid, profile.newLogoPic, 180)}')">验证真实性</a>
						<a href="javascript:void(0);" onclick="ignoreLogo('${profile.uid}')" id="ignore-logo-${profile.uid}">忽略</a><br />
					</c:if>
					<a href="/home/${profile.uid}" target="_blank">发私信</a><br/>
					<input type="hidden" value="${profile.uid}" id="uids-${profile.uid}" name="uids"/>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="7">
				<c:forEach var="pageId" items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/profile/${type}?pageId=${pageId}">${pageId}</a>
						</c:when>
						<c:otherwise>
							<strong>${pageId}</strong>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</td>
		</tr>
	</table>
	<script type="text/javascript" src="${jzr:static('/js/cms/common/base.js')}"></script>
</body>
</html>