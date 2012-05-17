<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form id="send-idea" onsubmit="javascript:return false;">
	<input type="hidden" name="ideaId" value="${idea.id}" />
	<div class="send_idea"><!--send_idea begin-->
		<c:if test="${not empty idea.pic}">
			<div class="photo"><img src="${jzr:ideaPic(idea.id, idea.pic, 200)}"/></div>
		</c:if>
		<div class="sd_right"><!--sd_rihgt begin-->
			<div class="select_menu" name="purposeType"><!--select_menu begin-->
				<p><a href="javascript:void(0);">请选择</a></p>
				<div></div>
				<div class="select_box"><!--select_box begin-->
					<span>
						<a href="javascript:void(0);" value="0">我想去</a>
						<a href="javascript:void(0);" value="1" class="selected">我想找伴儿去</a>
						<a href="javascript:void(0);" value="2">我想找一个男生</a>
						<a href="javascript:void(0);" value="3">我想找一个女生</a>
					</span>
					<em></em>
				</div><!--select_box end-->
			</div><!--select_menu end-->
			<div class="error" style="display: none;"></div>
			<div>
				<div class="ms"><c:out value="${idea.content}" /></div>
				<div class="infor"><!--infor begin-->
					<c:if test="${idea.startTime == null && idea.endTime != null}">
						<span class="time"><fmt:formatDate value="${idea.endTime}" pattern="MM-dd"/></span>
					</c:if>
					<c:if test="${not empty idea.place}">
						<span class="adress"><c:out value="${idea.place}" /></span>
					</c:if>
					<%-- <span class="link"><a href="${idea.link}" <c:if test="${empty isQplus || !isQplus}">target="_blank"</c:if>>查看相关链接</a></span> --%>
				</div><!--infor end-->
			</div>
			<div class="btn"><a href="javascript:void(0);" idea-id="${idea.id}">发布拒宅</a></div>
			<!-- <div class="sending" style="display:none;"><a href="javascript:void(0);">发布中</a></div> -->
			<c:if test="${context.tpId > 0}">
				<div class="tb tb_click">
					<input type="hidden" name="sendWeibo" value="true"/>
					<span></span>
					<p>同步到:</p>
					<c:choose>
						<c:when test="${context.tpName == 'weibo'}"><em class="wb"></em></c:when>
						<c:when test="${context.tpName == 'douban'}"><em class="db"></em></c:when>
						<c:when test="${context.tpName == 'qq'}"><em class="qq"></em></c:when>
					</c:choose>
				</div>
			</c:if>
		</div><!--sd_rihgt end-->
	</div><!--send_idea end-->
</form>