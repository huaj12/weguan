<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<ul>
	<c:forEach var="act" items="${actList}">
		<li onmouseover="javascript:mouseHover(this, true);" onmouseout="javascript:mouseHover(this, false);">
			<p class="l"></p><p class="r"></p>
			<a href="javascript:void(0);" onclick="javascript:wantTo(this);" actid="${act.id}" class="iwg_big" >❤ 我想去</a>
			<div class="photo1"><a href="/app/showAct/${act.id}"><img src="${jz:actLogo(act.id,act.logo,80)}"  width="80" height="80"/></a></div>
			<h2><a href="/app/showAct/${act.id}" class="v2"><c:out value="${act.name}" /></a></h2>
			<span>${jz:truncate(feed.act.intro,54,'...')}</span>
			<strong>共<a href="/app/showAct/${act.id}">${act.popularity}</a>人想去</strong>
		</li>
	</c:forEach>
</ul>
<div class="fxgd"><a href="/app/showCategoryActs">发现更多&gt;&gt;</a></div>