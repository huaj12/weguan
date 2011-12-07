<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String actionName = request.getParameter("actionName");
%>

<div class="nextpage">
	<s:if test='pagerManager.hasPre'>
		<a href="javascript:;" onclick="page_content('<%=actionName %>.action?type=<s:property value='type'/>&pagerManager.currentPage=<s:property value='pagerManager.currentPage-1' />');"><img src="/images/back.gif" />上一页</a>
	</s:if>
	<s:else>
		<a href="javascript:;"><img src="/images/back.gif" />上一页</a>
	</s:else>
	<s:iterator value="pagerManager.showPages" id="index" status="pagerStatus">
		<s:if test="#index != pagerManager.currentPage">
			<a href="javascript:;" onclick="page_content('<%=actionName %>.action?type=<s:property value='type'/>&pagerManager.currentPage=<s:property value='index' />');"><s:property value="index" /></a>
		</s:if>
		<s:else>
			<a href="javascript:;"><strong><s:property value="index" /></strong></a>
		</s:else>
		<s:if test="!#pagerStatus.last"> | </s:if>
	</s:iterator>
	<s:if test='pagerManager.hasNext'>
		<a href="javascript:;" onclick="page_content('<%=actionName %>.action?type=<s:property value='type'/>&pagerManager.currentPage=<s:property value='pagerManager.currentPage+1' />')">下一页<img src="/images/forward.gif" /></a>
	</s:if>
	<s:else>
		<a href="javascript:;">下一页<img src="/images/forward.gif" /></a>
	</s:else>
</div>