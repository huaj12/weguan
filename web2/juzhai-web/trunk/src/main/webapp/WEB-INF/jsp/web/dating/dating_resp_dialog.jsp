<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<c:choose>
	<c:when test="${errorCode==null}">
		<div class="show_box2"><!--show_box2 begin-->
			<h2><p>你的联系方式</p><span>只对ta可见)</span></h2>
			<form id="datingForm">
				<input type="hidden" name="datingId" value="${datingId}" />
				<div class="cantact"><!--cantact begin-->
					<div class="select"><!--select begin-->
						<span>
							<select name="contactType" id="language">
								<option value="1">QQ</option>
								<option value="2">MSN</option>
								<option value="3">手机</option>
								<option value="4">GTALK</option>
							</select>
						</span>
					</div><!--select end-->
					<div class="input"><p class="l"></p><span class="w120"><input name="contactValue" type="text" /></span><p class="r"></p></div>
				</div><!--cantact end-->
			</form>
			<div class="mzky error" style="display:none;">抱歉，发送失败！</div>
			<div class="btn"><a href="javascript:void(0);" onclick="javascript:submitRespDating(${datingId});return false;">接受邀请,并查看ta的联系方式</a></div>
		</div><!--show_box2 end-->
	</c:when>
	<c:when test="${errorCode=='30001'}">
		<div class="tj_done_show_box"><!--tj_done_show_box begin-->
			<h2>${errorInfo}</h2>
			<a href="/profile/index" class="done">完善资料</a>
		</div><!--tj_done_show_box end-->
	</c:when>
	<c:when test="${errorCode=='30002'||errorCode=='30003'||errorCode=='30004'}">
		<div class="tj_done_show_box"><!--tj_done_show_box begin-->
			<h2>${errorInfo}</h2>
			<a href="/home/datings/1" class="done">去看看</a>
		</div><!--tj_done_show_box end-->
	</c:when>
	<c:otherwise>
		<div class="tj_done_show_box"><!--tj_done_show_box begin-->
			<h2>${errorInfo}</h2>
			<a href="javascript:void(0);" class="done" onclick="javascript:closeDialog('openDatingResp');return false;">知道了</a>
		</div><!--tj_done_show_box end-->
	</c:otherwise>
</c:choose>