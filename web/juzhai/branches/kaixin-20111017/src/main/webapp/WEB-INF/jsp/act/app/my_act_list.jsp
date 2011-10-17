<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<h2><p>我的拒宅兴趣</p><span id="myActCount">(${pager.totalResults})</span></h2>
<div class="interesting_list"><!--interesting_list begin-->
	<c:forEach var="userActView" items="${userActViewList}">	
		<p onmouseover="javascript:myActHover(this, true);" onmouseout="javascript:myActHover(this, false);">
			<span class="fl"></span>
			<a href="javascript:;" class="key_words"><c:out value="${userActView.act.name}"/></a>
			<span class="fr"></span>
			<a href="javascript:;" class="close" title="删除兴趣" onclick="javascript:removeAct(this);" actid="${userActView.act.id}"></a>
		</p>
	</c:forEach>
</div><!--interesting_list end-->
<div class="arrow fr">
	<c:if test="${pager.hasPre}">
		<a href="javascript:;" class="l" onclick="javascript:pageMyAct(${pager.currentPage - 1});"></a>
	</c:if>
	<c:if test="${pager.hasNext}">
		<a href="javascript:;" class="r" onclick="javascript:pageMyAct(${pager.currentPage + 1});"></a>
	</c:if>
</div>