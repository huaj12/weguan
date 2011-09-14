<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<h2><p>我的拒宅兴趣</p><span>(${pager.totalResults})</span></h2>
<div class="interesting_list"><!--interesting_list begin-->
	<c:forEach var="userActView" items="${userActViewList}">
		<p class="hover"><span class="fl"></span><a href="#" class="key_words"><c:out value="${userActView.act.name}"/></a><span class="fr"></span><a href="#" class="close" title="删除兴趣"><img src="images/close_insteresting.png" /></a></p>
	</c:forEach>
</div><!--interesting_list end-->
<div class="arrow fr">
	<c:if test="${pager.hasPre}">
			<a href="#"><img src="images/arrow_left.png" /></a>
	</c:if>
	<c:if test="${pager.hasNext}">
		<a href="#"><img src="images/arrow_right.png" /></a>
	</c:if>
</div>