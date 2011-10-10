<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript" src="${jz:static('/js/core/pager.js')}"></script>
<div class="page">
<c:if test="${pager.currentPage!=1}">
	<a href="javascript:;" onclick="page_content('${pager.url}&page=1','${pager.ajaxId}')" class="link"><p class="l"></p><strong class="m">首页</strong><p class="r"></p></a>
	<a href="javascript:;" onclick="page_content('${pager.url}&page=${pager.currentPage-1}','${pager.ajaxId}')" class="link"><p class="l"></p><strong class="m">上一页</strong><p class="r"></p></a>
</c:if>
<c:if test="${pager.totalPage>1}">
<c:forEach var="page" items="${pager.showPages}" >
<c:choose>
	<c:when test="${page==pager.currentPage}">
		<a href="javascript:;" class="active"><p class="l"></p><strong class="m">${page}</strong><p class="r"></p></a>	
	</c:when>
	<c:otherwise>
		<a href="javascript:;" onclick="page_content('${pager.url}&page=${page}','${pager.ajaxId}')" class="link"><p class="l"></p><strong class="m">${page}</strong><p class="r"></p></a>
	</c:otherwise>
</c:choose>
</c:forEach>
</c:if>
<c:if test="${pager.currentPage!=pager.totalPage&&pager.totalPage>1}">
<a href="javascript:;" onclick="page_content('${pager.url}&page=${pager.currentPage+1}','${pager.ajaxId}')" class="link"><p class="l"></p><strong class="m">下一页</strong><p class="r"></p></a>
<a href="javascript:;" onclick="page_content('${pager.url}&page=${pager.totalPage}','${pager.ajaxId}')" class="link"><p class="l"></p><strong class="m">尾页</strong><p class="r"></p></a></div>
</c:if>
