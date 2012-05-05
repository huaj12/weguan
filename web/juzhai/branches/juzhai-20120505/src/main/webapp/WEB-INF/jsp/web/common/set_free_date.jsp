<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="float_box" id="freeDateForm" style="display:none;"><!--float_box begin-->
	<div class="width960"><!--width960 begin-->
		<p></p>
		<div class="con"><!--con begin-->
			<div class="close"><a href="javascript:void(0)" onclick="javascript:closeFreeDate();return false;"></a></div>
			<div class="gp"><!--gp begin-->
				<div class="title">告诉大家你最近哪天有空</div>
				<div class="week_day"><!--week_day begin-->
					<c:forEach var="dateView" items="${dateViewList}" varStatus="status">
						<a href="javascript:void(0);" onclick="javascript:setFreeDate(this);return false;" dayoftheweek="${dateView.dayOfTheWeek}" stringdate="<fmt:formatDate value="${dateView.date}" pattern="yyyy-MM-dd" />" class="<c:if test='${status.first}'>today</c:if> <c:if test='${dateView.free}'>active</c:if>" title="<c:choose><c:when test="${dateView.free}">点击取消</c:when><c:otherwise>点击选择</c:otherwise></c:choose>">周<c:choose><c:when test="${dateView.dayOfTheWeek==1}">日</c:when><c:when test="${dateView.dayOfTheWeek==2}">一</c:when><c:when test="${dateView.dayOfTheWeek==3}">二</c:when><c:when test="${dateView.dayOfTheWeek==4}">三</c:when><c:when test="${dateView.dayOfTheWeek==5}">四</c:when><c:when test="${dateView.dayOfTheWeek==6}">五</c:when><c:when test="${dateView.dayOfTheWeek==7}">六</c:when></c:choose><c:if test='${status.first}'><br />今天</c:if></a>
					</c:forEach>
				</div><!--week_day end-->
				<div class="ok_btn"><a href="javascript:void(0)" onclick="javascript:closeFreeDate();return false;">确定</a></div>
			</div><!--gp end-->
		</div><!--con end-->
	</div><!--width960 end-->
</div><!--float_box end-->
<script type="text/javascript">
	function closeFreeDate(){
		$('#freeDateForm').animate({bottom:'+=-100'}, 800, function(){$('#freeDateForm').hide();});
	}

	function setFreeDate(obj){
		var freeDateString = $(obj).attr("stringdate");
		var isSet = !$(obj).hasClass("active");
		jQuery.ajax({
			url : "/home/" + (isSet?"setFreeDate":"unSetFreeDate"),
			type : "post",
			cache : false,
			data : {"freeDateString" : freeDateString},
			dataType : "json",
			context : $(obj),
			success : function(result) {
				if (result && result.success) {
					if(isSet){
						$(this).addClass("active").attr("title", "点击取消");
					}else{
						$(this).removeClass("active").attr("title", "点击选择");
					}
					try{
						updateFreeDateShow();
					}catch (e) {
					}
				} else {
					alert(result.errorInfo);
				}
			},
			statusCode : {
				401 : function() {
					window.location.href = "/login?turnTo=" + window.location.href;
				}
			}
		});
	}
</script>