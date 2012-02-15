<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
	<c:when test="${footType == 'welcome'}"><c:set var="footClass" value="bottom_welcome" /></c:when>
	<c:otherwise><c:set var="footClass" value="bottom" /></c:otherwise>
</c:choose>
<div class="${footClass}"><!--bottom_welcome begin-->
	<div class="bottom_area">
		<p>拒宅网©2011&nbsp;<a href="http://www.miibeian.gov.cn/" target="_blank">沪ICP备11031778号</a></p><a href="http://weibo.com/51juzhai" target="_blank">官方微博</a><a href="/showUsers">找伴儿</a><a href="/showIdeas">出去玩</a><a href="/aboutUs">关于我们</a>
	</div>
</div><!--bottom_welcome end-->
<!-- <script type="text/javascript">
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F0626a9e13c77bc0eeb042f151c2e0aa5' type='text/javascript'%3E%3C/script%3E"));
</script> -->