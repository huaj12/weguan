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
					<c:if test="${not empty idea.date}">
						<span class="time"><fmt:formatDate value="${idea.date}" pattern="MM-dd"/></span>
					</c:if>
					<c:if test="${not empty idea.place}">
						<span class="adress"><c:out value="${idea.place}" /></span>
					</c:if>
					<%-- <span class="link"><a href="${idea.link}" target="_blank">查看相关链接</a></span> --%>
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
<script type="text/javascript">
	/* $("form#send-idea").find("div.select_menu").each(function(){
    	var select = new SelectInput(this);
    	select.bindBlur();
    	select.bindClick();
    	select.bindSelect();
    });
    $("form#send-idea").find("div.tb").bind("click", function(){
		if($(this).hasClass("tb_click")){
			$(this).removeClass("tb_click");
			$(this).find('input[name="sendWeibo"]').val(false);
		}else{
			$(this).addClass("tb_click");
			$(this).find('input[name="sendWeibo"]').val(true);
		}
		return false;
	});
    $("form#send-idea").find("div.btn > a").click(function(){
    	var ideaId = $(this).attr("idea-id");
    	postIdea($("form#send-idea"), function(){
    		$("#idea-btn-" + ideaId).attr("class", "sended send_done").children("a").text("已想去").unbind("click");
			if($("#useCount-" + ideaId).is(":visible")){
				$("#useCount-" + ideaId).text(parseInt($("#useCount-" + ideaId).text()) + 1);
			}else{
				$("#useCountShow-" + ideaId).text("1人想去");
			}
    	});
    	return false;
    }); */
</script>