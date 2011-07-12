<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${blog.title}</title>
<!-- <link href="${stuffRssLink}" rel="alternate" title="微观分享"
	type="application/rss+xml" /> -->
<link href="${staticService}/style/common.css" rel="stylesheet" type="text/css" />
<link href="${staticService}/style/head.css" rel="stylesheet" type="text/css" />
<link href="${staticService}/style/user/user.css" rel="stylesheet" type="text/css" />
<link id="cssskin"
	href="${staticService}/style/user/skin${blog.styleTemplate}.css"
	rel="stylesheet" type="text/css" />
<!--[if IE]>
<style type="text/css">
#ToolBox{
position: absolute;
top: expression( eval(document.compatMode && document.compatMode=='CSS1Compat') ? document.documentElement.scrollTop + 240 + 'px': document.body.scrollTop + 240 + 'px' );
}
</style>
<![endif]-->
<jsp:include page="../Script.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${staticService}/script/user/share.js"></script>
<script type="text/javascript" src="${staticService}/script/user/modify_profile.js"></script>
<script type="text/javascript" src="${staticService}/script/user/audio-player.js"></script>
</head>
<body>
<c:if test="${isOwn}">
	<div id="ToolBox">
	<ul>
		<li><a class="add" href="javascript:;"
			onclick="startShare(this);" onmouseover="startShare(this);"
			id="b-add"></a></li>
		<li><a class="config" href="javascript:;"
			onclick="showModifyProfileDialog('ProfileBox')"></a></li>
	</ul>
	<ul id="ShareBox">
		<li><a href="javascript:;" class="text"
			onclick="startShareStuff('text');"
			onmouseover="this.className='text_h'"
			onmouseout="this.className='text'"></a>日志</li>
		<li><a href="javascript:;" class="image"
			onclick="startShareStuff('uploadimage');"
			onmouseover="this.className='image_h'"
			onmouseout="this.className='image'"></a>照片</li>
		<li><a href="javascript:;" class="link"
			onclick="startShareStuff('link');"
			onmouseover="this.className='link_h'"
			onmouseout="this.className='link'"></a>链接</li>
		<li><a href="javascript:;" class="quote"
			onclick="startShareStuff('quote');"
			onmouseover="this.className='quote_h'"
			onmouseout="this.className='quote'"></a>摘录</li>
		<li><a href="javascript:;" class="video"
			onclick="startShareStuff('video');"
			onmouseover="this.className='video_h'"
			onmouseout="this.className='video'"></a>视频</li>
		<li><a href="javascript:;" class="music"
			onclick="startShareStuff('music');"
			onmouseover="this.className='music_h'"
			onmouseout="this.className='music'"></a>音乐</li>
	</ul>
	<c:if test="${isOwn}">
		<iframe name="emptyProfile" width="0" height="0" frameborder="0"
			onload="javascript:returnProfileInfo(this);" id="emptyProfile"></iframe>
		<div style="display: none" id="ProfileBox">
		<form target="emptyProfile" action="${httpService}/home/modify.html" method="post"
			enctype="multipart/form-data">
		<input type="hidden" value="${userLoginName}" name="loginName" />
		<ul class="user">
			<li class="title">个人信息修改</li>
			<li><label><span class="label">用户名：</span><span
				class="username">${userNickName}</span></label></li>
			<li><label><span class="label">新密码：</span><input
				type="password" name="password" maxlength="15" /><br />
			<span id="invalidPasswordError" style="display: none">密码长度必须在6-18位。</span></label></li>
			<li><label><span class="label">确&nbsp;&nbsp;&nbsp;认：</span><input
				type="password" name="pwdConf" maxlength="15" /><br />
			<span id="error" style="display: none">两次输入的密码不一致。</span></label></li>
			<li><label><span class="label">邮&nbsp;&nbsp;&nbsp;箱：</span><input
				type="text" name="emailAddress" id="emailAddress"
				value="${emailAddress}" /><br />
			<span id="duplicateEmailError" style="display: none">邮箱已经存在。</span><span
				id="invalidEmailError" style="display: none">邮箱格式不正确。</span></label></li>
			<li><label><span class="label fl"><img
				src="${avatarView.imagePath}" /></span><input type="file" size="14"
				name="avatar" /><br />
			微观会把图片缩放为 50*50<br />
			<span id="avatarSizeError" style="display: none">头像必须小于15M。</span><span
				id="avatarTypeError" style="display: none">头像仅支持png、jpg和jpeg格式。</span></label>
			<div class="clear"></div>
			</li>
		</ul>
		<ul class="blog">
			<li class="title">博客设置</li>
			<li><label><span class="label">标&nbsp;&nbsp;&nbsp;题：</span><input
				type="text" name="blogTitle" value="${blogTitle}" /></label></li>
			<li><label><span class="label">描&nbsp;&nbsp;&nbsp;述：</span><input
				type="text" name="blogAbout" value="${blogAbout}" /></label></li>
			<li><label><span class="label">皮&nbsp;&nbsp;&nbsp;肤：</span>
				<select name="template" onchange="doSelect(this);">
					<option value="1" <c:if test="${1==blog.styleTemplate}">selected="true"</c:if>>白色</option>
					<option value="2" <c:if test="${2==blog.styleTemplate}">selected="true"</c:if>>蓝色</option>
					<option value="3" <c:if test="${3==blog.styleTemplate}">selected="true"</c:if>>咖啡色</option>
					<option value="4" <c:if test="${4==blog.styleTemplate}">selected="true"</c:if>>黑色</option>
					<option value="5" <c:if test="${5==blog.styleTemplate}">selected="true"</c:if>>绿色</option>
					<option value="6" <c:if test="${6==blog.styleTemplate}">selected="true"</c:if>>红色</option>
				</select>
			</label></li>
			<li class="buttons">
			<button type="submit">更新设置</button>
			&nbsp;
			<button type="button"
				onclick="cancelModify('ProfileBox',${blog.styleTemplate})">关闭</button>
			</li>
		</ul>
		</form>
		<div class="clear"></div>
		</div>
	</c:if></div>
</c:if>
<div id="Site"><!--#include virtual="${appContextPath}/header.html"--> <jsp:include
	page="TitleBarPage.jsp"></jsp:include><iframe name="empty" width="0"
	height="0" frameborder="0" onload="javascript:returnInfo(this);"
	id="empty" style="display: none;"></iframe>
<div id="StuffFormPanel"></div>
<div id="StuffList"><jsp:include page="StuffContentPage.jsp"
	flush="true"></jsp:include></div>
<div id="SiteFoot">
<div class="rss"><a href="${stuffRssLink}"><img
	src="${staticService}/images/i-rss.gif" alt="rss" /></a></div>
<div class="inform"><a href="http://admin.weguan.com/">官方通告</a></div>
</div>
</div>
<jsp:include page="../GoogleAnalytics.jsp" flush="true"></jsp:include>
<script src="http://weguan.uservoice.com/pages/general/widgets/tab.js?alignment=right&amp;color=FFFFFF" type="text/javascript"></script>
</body>
</html>