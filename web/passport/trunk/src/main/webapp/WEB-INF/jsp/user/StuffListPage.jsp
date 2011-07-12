<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
function toggleTools(id,show){
	var v = show?'visible':'hidden';
	$('tools'+id).style.visibility=v;
	//$('stuffRepost'+id).style.visibility=v
}
</script>
<c:if test="${fn:length(stuffViewList)==0  && showType=='creator'}">
	<div id="NoStuff">
	<p>恭喜！你已经拥有一个自己的微观主页了！</p>
	<p>•&nbsp;<a href="javascript:;" onclick="startShare($('b-add'))">添加第一个微观</a></p>
	<p>•&nbsp;<a href="javascript:;"
		onclick="showModifyProfileDialog('ProfileBox')">设置页面风格和修改个人资料</a></p>
	<p>快试试看吧！</p>
	</div>
</c:if>
<ul>
	<c:forEach items="${stuffViewList}" var="stuffView">
		<c:if test="${null!=stuffView.groupedDateTime}">
			<li>
			<div class="time_group"><fmt:formatDate
				value="${stuffView.groupedDateTime}" pattern="M/d E" /></div>
			</li>
		</c:if>
		<li>
		<div class="stuff ${stuffView.typeName.typeName}"
			onmouseover="toggleTools(${stuffView.poin},true)"
			onmouseout="toggleTools(${stuffView.poin},false)">
		<div class="stuff_info"><a class="filter"
			href="${stuffView.stuffTypeNameLink}"><span>过滤</span></a><span
			class="time"><fmt:formatDate
			value="${stuffView.createdDateTime}" pattern="HH:mm" /></span><span
			id="tools${stuffView.poin}" class="tools"><c:if
			test="${showType=='creator'}">
			<a
				href="javascript:editStuff('${stuffView.typeName.typeName}',${stuffView.poin},'${pageType}');">编辑</a>
			<br />
		</c:if><c:if test="${pageType!='singlePage'}">
			<a href="${stuffView.stuffLink}" target="_blank">新开窗口</a>
		</c:if><c:if test="${hasLogin&&showType!='creator'}">
			<a href="javascript:;" onclick="repostStuff(${stuffView.poin},this)">转贴</a>
		</c:if><c:if test="${stuffView.typeName=='MUSIC'}">
			<a class="download" href="${stuffView.musicView.source}">点击下载</a>
		</c:if><c:if test="${contextUser.adminUser&&contextUser.poin!=pageUser.poin}">
			<a href="javascript:;" id="adminDeleteSpan${stuffView.poin}"
				onclick="deleteStuff('${stuffView.typeName.typeName}',${stuffView.poin},'homepage')">删除</a>
		</c:if></span></div>
		<div id="StuffContent${stuffView.poin}" class="content"><c:choose>
			<c:when test="${stuffView.typeName=='TEXT'}">
				<h4 class="text_title">${stuffView.textView.title}</h4>
				<p class="text_body">${stuffView.textView.body}</p>
			</c:when>
			<c:when test="${stuffView.typeName=='QUOTE'}">
				<p class="quote_body">${stuffView.quoteView.content}</p>
				<p><span class="quote_line">—</span><span class="quote_form">${stuffView.quoteView.source}</span></p>
			</c:when>
			<c:when test="${stuffView.typeName=='MUSIC'}">
				<object width="290" height="24" type="application/x-shockwave-flash"
					data="${staticService}/images/audio-player.swf" id="audioplayer${stuffView.poin}">
					<param name="movie" value="${staticService}/images/audio-player.swf" />
					<param name="FlashVars"
						value="playerID=${stuffView.poin}&soundFile=${stuffView.musicView.source}" />
					<param name="quality" value="high" />
					<param name="menu" value="false" />
					<param name="wmode" value="transparent" />
				</object>
				<p class="music_des">${stuffView.musicView.description}</p>
			</c:when>
			<c:when test="${stuffView.typeName=='IMAGE'}">
				<a href="${stuffView.imageView.imageLink}"><img
					class="image_img" src="${stuffView.imageView.imageLink}" alt=""
					onload="if(this.offsetWidth>532){this.width = 532;}" /></a>
				<p>${stuffView.imageView.description}</p>
			</c:when>
			<c:when test="${stuffView.typeName=='LINK'}">
				<a class="link_title" href="${stuffView.linkView.titleLink}">${stuffView.linkView.title}</a>
				<p class="link_des">${stuffView.linkView.description}</p>
			</c:when>
			<c:when test="${stuffView.typeName=='VIDEO'}">
				<p class="video_code">${stuffView.videoView.compiledSource}</p>
				<p class="video_des">${stuffView.videoView.description}</p>
			</c:when>
		</c:choose><c:if test="${stuffView.repostCreatorNickName!=null}">
			<span>转贴自：<a href="${stuffView.repostUserHomePageLink}">${stuffView.repostCreatorNickName}</a></span>
		</c:if></div>
		<div id="StuffEdit${stuffView.poin}" class="edit_content"
			style="display: none;"></div>
		<div class="clear"></div>
		</div>
		</li>
	</c:forEach>
</ul>