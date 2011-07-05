<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:forEach items="${stuffViewList}" var="stuffView">

	<c:choose>
		<c:when test="${stuffView.typeName=='TEXT'}">
			<tr
				onmouseover="$('stuff${stuffView.poin}').style.visibility='visible';$('stuffRepost${stuffView.poin}').style.visibility='visible'"
				onmouseout="$('stuff${stuffView.poin}').style.visibility='hidden';$('stuffRepost${stuffView.poin}').style.visibility='hidden'">
				<td width="30">&nbsp;</td>
				<td width="70" height="50" align="center" valign="top"
					class="httext"><a href="${stuffView.creatorUserImageLink}"><img
					src="${stuffView.creatorUserImagePath}" width="50" height="50"
					title="${stuffView.creatorUserNickName}"
					alt="${stuffView.creatorUserNickName}"></a><br />
				<fmt:formatDate value="${stuffView.createdDateTime}" pattern="HH:mm" /><br />
				<span id="stuff${stuffView.poin}" style="visibility: hidden;"><c:if
					test="${pageType!='singlePage'}">
					<a href="${stuffView.stuffLink}" target="_blank">新开窗口</a>
				</c:if></span><br />
				<c:if test="${isAdminUser}">
					<a href="javascript:;" id="adminDeleteSpan${stuffView.poin}"
						onclick="deleteStuff('text',${stuffView.poin},'homepage')">删除</a>
				</c:if></td>
				<td valign="top" width="470">
				<div class="stuffcontent" id="StuffContent${stuffView.poin}"><span
					class="stuffTitle">${stuffView.textView.title}</span>
				<p class="stuffBody">${stuffView.textView.body}</p>
				<c:if test="${stuffView.repostCreatorNickName!=null}">
					<span>转贴自：<a href="${stuffView.repostUserHomePageLink}">${stuffView.repostCreatorNickName}</a></span>
				</c:if> <span id="stuffRepost${stuffView.poin}" style="visibility: hidden;"><c:if
					test="${hasLogin&&loginedUserPoin!=stuffView.creatorUserPoin}">
					<a href="javascript:;"
						onclick="repostStuff(${stuffView.poin},this)">转贴</a>
				</c:if></span></div>
				<div id="StuffEdit${stuffView.poin}" style="display: none;"></div>
				</td>
			</tr>
		</c:when>
		<c:when test="${stuffView.typeName=='LINK'}">
			<tr
				onmouseover="$('stuff${stuffView.poin}').style.visibility='visible';$('stuffRepost${stuffView.poin}').style.visibility='visible'"
				onmouseout="$('stuff${stuffView.poin}').style.visibility='hidden';$('stuffRepost${stuffView.poin}').style.visibility='hidden'">
				<td width="30">&nbsp;</td>
				<td width="70" height="50" align="center" valign="top"
					class="htlink"><a href="${stuffView.creatorUserImageLink}"><img
					src="${stuffView.creatorUserImagePath}" width="50" height="50"
					title="${stuffView.creatorUserNickName}"
					alt="${stuffView.creatorUserNickName}"></a> <br />
				<fmt:formatDate value="${stuffView.createdDateTime}" pattern="HH:mm" />
				<br />
				<span id="stuff${stuffView.poin}" style="visibility: hidden;">
				<c:if test="${pageType!='singlePage'}">
					<a href="${stuffView.stuffLink}" target="_blank">新开窗口</a>
				</c:if></span><br />
				<c:if test="${isAdminUser}">
					<a href="javascript:;" id="adminDeleteSpan${stuffView.poin}"
						onclick="deleteStuff('link',${stuffView.poin},'homepage')">删除</a>
				</c:if></td>
				<td valign="top" width="470">
				<div class="stuffcontent" id="StuffContent${stuffView.poin}"><a
					href="${stuffView.linkView.titleLink}"><span class="stuffTitle">${stuffView.linkView.title}</span></a><br />
				<p class="stuffBody">${stuffView.linkView.description}</p>
				<c:if test="${stuffView.repostCreatorNickName!=null}">
					<span>转贴自：<a href="${stuffView.repostUserHomePageLink}">${stuffView.repostCreatorNickName}</a></span>
				</c:if> <span id="stuffRepost${stuffView.poin}" style="visibility: hidden;"><c:if
					test="${hasLogin&&loginedUserPoin!=stuffView.creatorUserPoin}">
					<a href="javascript:;"
						onclick="repostStuff(${stuffView.poin},this)">转贴</a>
				</c:if></span></div>
				<div id="StuffEdit${stuffView.poin}" style="display: none;"></div>
				</td>
			</tr>
		</c:when>
		<c:when test="${stuffView.typeName=='QUOTE'}">
			<tr
				onmouseover="$('stuff${stuffView.poin}').style.visibility='visible';$('stuffRepost${stuffView.poin}').style.visibility='visible'"
				onmouseout="$('stuff${stuffView.poin}').style.visibility='hidden';$('stuffRepost${stuffView.poin}').style.visibility='hidden'">
				<td width="30">&nbsp;</td>
				<td width="70" height="50" align="center" valign="top"
					class="htquote"><a href="${stuffView.creatorUserImageLink}"><img
					src="${stuffView.creatorUserImagePath}" width="50" height="50"
					title="${stuffView.creatorUserNickName}"
					alt="${stuffView.creatorUserNickName}"></a><br />
				<fmt:formatDate value="${stuffView.createdDateTime}" pattern="HH:mm" /><br />
				<span id="stuff${stuffView.poin}" style="visibility: hidden;"><c:if
					test="${pageType!='singlePage'}">
					<a href="${stuffView.stuffLink}" target="_blank">新开窗口</a>
				</c:if></span><br />
				<c:if test="${isAdminUser}">
					<a href="javascript:;" id="adminDeleteSpan${stuffView.poin}"
						onclick="deleteStuff('quote',${stuffView.poin},'homepage')">删除</a>
				</c:if></td>
				<td valign="top" width="470">
				<div class="stuffcontent" id="StuffContent${stuffView.poin}">${stuffView.quoteView.content}
				<p>—<cite>${stuffView.quoteView.source}</cite></p>
				<c:if test="${stuffView.repostCreatorNickName!=null}">
					<span>转贴自：<a href="${stuffView.repostUserHomePageLink}">${stuffView.repostCreatorNickName}</a></span>
				</c:if> <span id="stuffRepost${stuffView.poin}" style="visibility: hidden;"><c:if
					test="${hasLogin&&loginedUserPoin!=stuffView.creatorUserPoin}">
					<a href="javascript:;"
						onclick="repostStuff(${stuffView.poin},this)">转贴</a>
				</c:if></span></div>
				<div id="StuffEdit${stuffView.poin}" style="display: none;"></div>
				</td>
			</tr>
		</c:when>
		<c:when test="${stuffView.typeName=='IMAGE'}">
			<tr
				onmouseover="$('stuff${stuffView.poin}').style.visibility='visible';$('stuffRepost${stuffView.poin}').style.visibility='visible'"
				onmouseout="$('stuff${stuffView.poin}').style.visibility='hidden';$('stuffRepost${stuffView.poin}').style.visibility='hidden'">
				<td width="30">&nbsp;</td>
				<td width="70" height="50" align="center" valign="top"
					class="htimage"><a href="${stuffView.creatorUserImageLink}"><img
					src="${stuffView.creatorUserImagePath}" width="50" height="50"
					title="${stuffView.creatorUserNickName}"
					alt="${stuffView.creatorUserNickName}"></a><br />
				<fmt:formatDate value="${stuffView.createdDateTime}" pattern="HH:mm" /><br />
				<span id="stuff${stuffView.poin}" style="visibility: hidden;"><c:if
					test="${pageType!='singlePage'}">
					<a href="${stuffView.stuffLink}" target="_blank">新开窗口</a>
				</c:if></span><br />
				<c:if test="${isAdminUser}">
					<a href="javascript:;" id="adminDeleteSpan${stuffView.poin}"
						onclick="deleteStuff('image',${stuffView.poin},'homepage')">删除</a>
				</c:if></td>
				<td valign="top" width="470">
				<div id="StuffEdit${stuffView.poin}" style="display: none;"></div>
				<div class="stuffcontent" id="StuffContent${stuffView.poin}">
				<table width="100%" border="0" cellspacing="0">
					<tr>
						<td><a href="${stuffView.imageView.imageLink}"
							target="_blank"><img class="border-img"
							onload="if(this.offsetWidth>456){this.width = 456;}"
							src="${stuffView.imageView.imageLink}" /></a>
						<p>${stuffView.imageView.description}</p>
						</td>
					</tr>
				</table>
				<c:if test="${stuffView.repostCreatorNickName!=null}">
					<span>转贴自：<a href="${stuffView.repostUserHomePageLink}">${stuffView.repostCreatorNickName}</a></span>
				</c:if> <span id="stuffRepost${stuffView.poin}" style="visibility: hidden;"><c:if
					test="${hasLogin&&loginedUserPoin!=stuffView.creatorUserPoin}">
					<a href="javascript:;"
						onclick="repostStuff(${stuffView.poin},this)">转贴</a>
				</c:if></span></div>
				</td>
			</tr>
		</c:when>
		<c:when test="${stuffView.typeName=='VIDEO'}">
			<tr
				onmouseover="$('stuff${stuffView.poin}').style.visibility='visible';$('stuffRepost${stuffView.poin}').style.visibility='visible'"
				onmouseout="$('stuff${stuffView.poin}').style.visibility='hidden';$('stuffRepost${stuffView.poin}').style.visibility='hidden'">
				<td width="30">&nbsp;</td>
				<td width="70" height="50" align="center" valign="top"
					class="htvideo"><a href="${stuffView.creatorUserImageLink}"><img
					src="${stuffView.creatorUserImagePath}" width="50" height="50"
					title="${stuffView.creatorUserNickName}"
					alt="${stuffView.creatorUserNickName}"></a><br />
				<fmt:formatDate value="${stuffView.createdDateTime}" pattern="HH:mm" /><br />
				<span id="stuff${stuffView.poin}" style="visibility: hidden;"><c:if
					test="${pageType!='singlePage'}">
					<a href="${stuffView.stuffLink}" target="_blank">新开窗口</a>
				</c:if></span><br />
				<c:if test="${isAdminUser}">
					<a href="javascript:;" id="adminDeleteSpan${stuffView.poin}"
						onclick="deleteStuff('video',${stuffView.poin},'homepage')">删除</a>
				</c:if></td>
				<td valign="top" width="470">
				<div class="stuffcontent" id="StuffContent${stuffView.poin}">${stuffView.videoView.compiledSource}
				<p>${stuffView.videoView.description}</p>
				<c:if test="${stuffView.repostCreatorNickName!=null}">
					<span>转贴自：<a href="${stuffView.repostUserHomePageLink}">${stuffView.repostCreatorNickName}</a></span>
				</c:if> <span id="stuffRepost${stuffView.poin}" style="visibility: hidden;"><c:if
					test="${hasLogin&&loginedUserPoin!=stuffView.creatorUserPoin}">
					<a href="javascript:;"
						onclick="repostStuff(${stuffView.poin},this)">转贴</a>
				</c:if></span></div>
				<div id="StuffEdit${stuffView.poin}" style="display: none;"></div>
				</td>
			</tr>
		</c:when>
		<c:when test="${stuffView.typeName=='MUSIC'}">
			<tr
				onmouseover="$('stuff${stuffView.poin}').style.visibility='visible';$('stuffRepost${stuffView.poin}').style.visibility='visible'"
				onmouseout="$('stuff${stuffView.poin}').style.visibility='hidden';$('stuffRepost${stuffView.poin}').style.visibility='hidden'">
				<td width="30">&nbsp;</td>
				<td width="70" height="50" align="center" valign="top"
					class="htmusic"><a href="${stuffView.creatorUserImageLink}"><img
					src="${stuffView.creatorUserImagePath}" width="50" height="50"
					title="${stuffView.creatorUserNickName}"
					alt="${stuffView.creatorUserNickName}"></a><br />
				<fmt:formatDate value="${stuffView.createdDateTime}" pattern="HH:mm" /><br />
				<span id="stuff${stuffView.poin}" style="visibility: hidden;"><c:if
					test="${pageType!='singlePage'}">
					<a href="${stuffView.stuffLink}" target="_blank">新开窗口</a>
				</c:if></span><br />
				<c:if test="${isAdminUser}">
					<a href="javascript:;" id="adminDeleteSpan${stuffView.poin}"
						onclick="deleteStuff('music',${stuffView.poin},'homepage')">删除</a>
				</c:if></td>
				<td valign="top" width="470">
				<div class="stuffcontent" id="StuffContent${stuffView.poin}"><object
					width="290" height="24" id="audioplayer${stuffView.poin}"
					data="/images/audio-player.swf"
					type="application/x-shockwave-flash">
					<param value="/images/audio-player.swf" name="movie" />
					<param
						value="playerID=${stuffView.poin}&soundFile=${stuffView.musicView.source}"
						name="FlashVars" />
					<param value="high" name="quality" />
					<param value="false" name="menu" />
					<param value="transparent" name="wmode" />
				</object>
				<p>${stuffView.musicView.description}</p>
				<c:if test="${stuffView.repostCreatorNickName!=null}">
					<span>转贴自：<a href="${stuffView.repostUserHomePageLink}">${stuffView.repostCreatorNickName}</a></span>
				</c:if> <span id="stuffRepost${stuffView.poin}" style="visibility: hidden;"><c:if
					test="${hasLogin&&loginedUserPoin!=stuffView.creatorUserPoin}">
					<a href="javascript:;"
						onclick="repostStuff(${stuffView.poin},this)">转贴</a>
				</c:if></span></div>
				<div id="StuffEdit${stuffView.poin}" style="display: none;"></div>
				</td>
			</tr>
		</c:when>
	</c:choose>
	<tr>
		<td width="30">&nbsp;</td>
		<td width="70" height="50" align="right" valign="top">&nbsp;</td>
		<td width="470">&nbsp;</td>
	</tr>
</c:forEach>
