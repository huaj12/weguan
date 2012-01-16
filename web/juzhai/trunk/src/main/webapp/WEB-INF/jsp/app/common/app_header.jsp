<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="skin_top"><!--skin_top begin-->
	<div class="top_right"></div>
	<div class="top_left"></div>
	<div class="top_mid"><!--top_mid begin-->
		<div class="menu"><!--menu begin-->
			<span <c:if test="${page=='home'}">class="hover"</c:if>><p class="l"></p><p class="r"></p><a href="/app/index">拒宅灵感</a></span>
			<em></em>
			<span <c:if test="${page=='judge'}">class="hover"</c:if>><p class="l"></p><p class="r"></p><a href="/app/judge">宅人评价</a></span>
			<em></em>
			<span <c:if test="${page=='myAct'}">class="hover"</c:if>><p class="l"></p><p class="r"></p><a href="/app/myAct">我的拒宅</a></span>
			<em></em>
			<span <c:if test="${page=='friend'}">class="hover"</c:if>><p class="l"></p><p class="r"></p><a href="/app/showAllFriend">好友的拒宅</a></span>
			<em></em>
			<span <c:if test="${page=='rank'}">class="hover"</c:if>><p class="l"></p><p class="r"></p><a href="/app/showLive">拒宅大屏幕</a></span>
		</div><!--menu end-->
		<div class="dy fr"><p class="l"></p><span><input name="" id="addAct" type="text" onfocus="if(this.value=='输入拒宅项目,如:逛街')this.value=''" onblur="if(this.value=='')this.value='输入拒宅项目,如:逛街'" value="输入拒宅项目,如:逛街"/></span><a id="_queryActs" href="javascript:void(0);">找伴</a>
		<div class="ts" id="headAddActError" style="display:none"><!--ts begin-->
			<em>请输入内容</em>
		</div><!--ts end-->
		</div>
	</div><!--top_mid end-->
</div><!--skin_top end-->