<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
	<c:when test="${footType == 'fixed'}"><c:set var="footClass" value="bottom_welcome" /></c:when>
	<c:when test="${footType == 'welcome'}"><c:set var="footClass" value="bottom_welcome_wel" /></c:when>
	<c:otherwise><c:set var="footClass" value="bottom" /></c:otherwise>
</c:choose>
<div class="${footClass}"><!--bottom_welcome begin-->
	<div class="bottom_area">
		<c:choose>
			<c:when test="${footType == 'welcome'}"><p><em>拒宅网©2012&nbsp;<a href="http://www.miibeian.gov.cn/" target="_blank">沪ICP备11031778号</a></em><a href="http://weibo.com/51juzhai" target="_blank">官方微博</a><a href="/aboutUs">关于我们</a></p><span><a href="/showusers">找伴儿</a><a href="/showideas">出去玩</a><b>|</b><a href="/showIdeas/pop_2/1">上海约会地点</a><a href="/showIdeas/pop_1/1">北京约会地点</a></span></c:when>
			<c:otherwise><p>拒宅网©2012&nbsp;<a href="http://www.miibeian.gov.cn/" target="_blank">沪ICP备11031778号</a></p><a href="/aboutUs">关于我们</a><a href="javascript:void(0);" target-uid="3" target-nickname="拒宅妹妹" id="feedback" has-login="${context.uid > 0}">意见反馈</a><a href="http://weibo.com/51juzhai" target="_blank">官方微博</a></c:otherwise>
		</c:choose>
	</div>
</div><!--bottom_welcome end-->
<script type="text/javascript">
	var host=document.domain;
	var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
	if(host=="test.51juzhai.com"){
		document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F8eb3ef477c849f4cc74c953bebe4d0e2' type='text/javascript'%3E%3C/script%3E"));
	}else{
		document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F0626a9e13c77bc0eeb042f151c2e0aa5' type='text/javascript'%3E%3C/script%3E"));
	}
</script>
