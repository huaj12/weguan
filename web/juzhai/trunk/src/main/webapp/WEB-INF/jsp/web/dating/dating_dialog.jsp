<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<c:choose>
	<c:when test="${errorCode==null}">
		<div class="date_ta_box"><!--date_ta_box begin-->
			<h2><p>约</p><div class="name <c:choose><c:when test='${profile.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><a href="#"><c:out value="${profile.nickname}" /></a></div><p>出去玩</p></h2>
			<div class="con"><!--con begin-->
				<form id="datingForm">
				<input type="hidden" name="uid" value="${profile.uid}" />
				<c:if test="${dating!=null}">
					<input type="hidden" name="datingId" value="${dating.id}" />
				</c:if>
				<div class="x"><!--x begin-->
					<h3>约ta</h3>
					<div class="select w190" id="uboxstyle"><!--select begin-->
						<span>
							<select name="actId" id="language">
								<option value="0">选择ta最新想去的</option>
								<c:forEach var="userActView" items="${userActViewList}">
									<option value="${userActView.act.id}" <c:if test="${dating!=null&&dating.actId==userActView.act.id}">selected="selected"</c:if>>
										<c:out value="${userActView.act.name}" />
									</option>
								</c:forEach>
							</select>
						</span>
					</div><!--select end-->
				</div><!--x end-->
				<div class="x"><!--x begin-->
					<h3>费用</h3>
					<div class="select w190" id="uboxstyle"><!--select begin-->
						<span>
							<select name="consumeType" id="language">
								<option value="1" <c:if test="${dating!=null&&dating.consumeType==1}">selected="selected"</c:if>>我请客</option>
								<option value="2" <c:if test="${dating!=null&&dating.consumeType==2}">selected="selected"</c:if>>AA制</option>
								<option value="3" <c:if test="${dating!=null&&dating.consumeType==3}">selected="selected"</c:if>>求请客</option>
								<option value="4" <c:if test="${dating!=null&&dating.consumeType==4}">selected="selected"</c:if>>不用花钱</option>
							</select>
						</span>
					</div><!--select end-->
				</div><!--x end-->
				<div class="x"><!--x begin-->
					<h3>联系方式</h3>
					<div class="select w70" id="uboxstyle"><!--select begin-->
						<span>
							<select name="contactType" id="language">
								<option value="1" <c:if test="${dating!=null&&dating.starterContactType==1}">selected="selected"</c:if>>QQ</option>
								<option value="2" <c:if test="${dating!=null&&dating.starterContactType==2}">selected="selected"</c:if>>MSN</option>
								<option value="3" <c:if test="${dating!=null&&dating.starterContactType==3}">selected="selected"</c:if>>手机</option>
								<option value="4" <c:if test="${dating!=null&&dating.starterContactType==4}">selected="selected"</c:if>>GTALK</option>
							</select>
						</span>
					</div><!--select end-->
					<div class="input"><p class="l"></p><span class="w100"><input name="contactValue" type="text" value="${dating.starterContactValue}"/></span><p class="r"></p></div>	
					<strong>(只对ta可见)</strong>
				</div><!--x end-->
				</form>
				<div class="btn">
					<a href="javascript:void(0);" class="ok" onclick="javascript:submitDating(${profile.uid});return false;">确定约ta</a>
					<a href="javascript:void(0);" class="cancel" onclick="javascript:closeDialog('openDating');return false;">取消</a>
				</div>
				<div class="mzky error" style="display:none;">抱歉，发送失败！</div>
				<div class="mzky">每周可约10个人</div>
			</div><!--con end-->
		</div><!--date_ta_box end-->
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
			<a href="javascript:void(0);" class="done" onclick="javascript:closeDialog('openDating');return false;">知道了</a>
		</div><!--tj_done_show_box end-->
	</c:otherwise>
</c:choose>