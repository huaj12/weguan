<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<c:choose>
	<c:when test="${error==null}">
		<div class="date_ta_box"><!--date_ta_box begin-->
			<h2><p>约</p><div class="name boy"><a href="#"><c:out value="${profile.nickname}" /></a></div><p>出去玩</p></h2>
			<div class="con"><!--con begin-->
				<form id="datingForm">
				<input type="hidden" name="uid" value="${profile.uid}" />
				<div class="x"><!--x begin-->
					<h3>约ta</h3>
					<div class="select w190"><!--select begin-->
						<span>
							<select name="actId" id="language">
								<c:forEach var="userActView" items="${userActViewList}">
									<option value="${userActView.act.id}"><c:out value="${userActView.act.name}" /></option>
								</c:forEach>
							</select>
						</span>
					</div><!--select end-->
				</div><!--x end-->
				<div class="x"><!--x begin-->
					<h3>费用</h3>
					<div class="select w190"><!--select begin-->
						<span>
							<select name="consumeType" id="language">
								<option value="1">我请客</option>
								<option value="2">AA制</option>
								<option value="3">求请客</option>
								<option value="4">不用花钱</option>
							</select>
						</span>
					</div><!--select end-->
				</div><!--x end-->
				<div class="x"><!--x begin-->
					<h3>联系方式</h3>
					<div class="select w70"><!--select begin-->
						<span>
							<select name="contactType" id="language">
								<option value="1">QQ</option>
								<option value="2">MSN</option>
								<option value="3">手机</option>
								<option value="4">GTALK</option>
							</select>
						</span>
					</div><!--select end-->
					<div class="input"><p class="l"></p><span class="w100"><input name="contactValue" type="text"/></span><p class="r"></p></div>	
					<strong>(只对ta可见)</strong>
				</div><!--x end-->
				</form>
				<div class="btn">
					<a href="javascript:void(0);" class="ok" onclick="javascript:date();">确定约ta</a>
					<a href="javascript:void(0);" class="cancel" onclick="javascript:closeDialog();">取消</a>
				</div>
				<div class="mzky error" style="display:none;">抱歉，发送失败！</div>
				<div class="mzky">每周可约10个人</div>
			</div><!--con end-->
		</div><!--date_ta_box end-->
	</c:when>
	<c:otherwise>
		<div class="tj_done_show_box"><!--tj_done_show_box begin-->
			<h2>${errorInfo}</h2>
			<a href="javascript:void(0);" class="done" onclick="javascript:closeDialog();">知道了</a>
		</div><!--tj_done_show_box end-->
	</c:otherwise>
</c:choose>
<script type="text/javascript">
	function closeDialog(){
		var showBox = $.dialog.list["openDating"];
		if(showBox != null){
			showBox.close();
		}
	}
	function date(){
		$.ajax({
			url : "/act/date",
			type : "post",
			cache : false,
			data : $("#datingForm").serialize(),
			dataType : "json",
			success : function(result) {
				if(result&&result.success){
					alert("成功");
				}else{
					$("div.con > div.error").text(result.errorInfo).show();
				}
			},
			statusCode : {
				401 : function() {
					window.location.href = "/login";
				}
			}
		});
	}
</script>