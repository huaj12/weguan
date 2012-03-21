<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的个人资料-拒宅网</title>
<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="warp">
		<!--warp begin-->
		<div class="main">
			<!--main begin-->
			<c:set var="page" value="index" scope="request" />
			<c:set var="messageHide" value="true" scope="request" />
			<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
			<form  id="preferenceForm">
			<div class="content"><!--content begin-->
				<div class="t"></div>
				<div class="m"><!--m begin-->
				<div class="set"><!--set begin-->
				<c:set var="page" value="preference" scope="request" />
				<jsp:include page="/WEB-INF/jsp/web/profile/common/left.jsp" /><!--set_left end-->
				<div class="set_right"><!--set_right begin-->
				<div class="title"><h2>你对哪些人的拒宅信息感兴趣？</h2></div>
				<div class="my_infor"><!--my_infor begin-->
				<div class="infor"><!--infor begin-->
				<input type="hidden" value="${fn:length(views)}" id="preference_count" />
				<c:forEach items="${views}" var="view" varStatus="index">
				<div class="infor_x"><!--infor_x begin-->
				<div id="answerDiv_${index.index}">
					
				</div>
				<h3>${view.preference.name}：</h3>
				<input type="hidden" id="preferenceId_${index.index }" value="${view.preference.id}"/>
				<input type="hidden" id="inputType_${index.index }" value="${view.input.inputType}"/>
				<c:choose>
					<c:when test="${view.input.inputType==0}">
					<div class="select"><!--select begin-->
						<c:forEach items="${view.input.options}" var="option">
						<div class="dis_age"><b><input name="userPreferences[${index.index }].answer" type="checkbox" <c:forEach items="${view.answer}" var="box"><c:if test="${box==option.value}"> checked="checked"</c:if></c:forEach>  value="${option.value }" /></b><p>${option.name }</p></div>
						</c:forEach>
					</div><!--select end-->
					</c:when>
					<c:when test="${view.input.inputType==1}">
					<div class="select"><!--select begin-->
					<span>	
							<select>
							
						<c:forEach items="${view.input.options}" var="option">
							<option value="${option.value}" <c:if test="${option.value==view.userPreference.answer}"> selected="selected"</c:if> >${option.name}</option>
						</c:forEach>			
							</select>
					</span>
					</div><!--select end-->
					</c:when>
					<c:when test="${view.input.inputType==2 }">
					<div class="input"><!--input begin-->
					<p class="l"></p><span class="width70"><input type="text" name="userPreferences[${index.index }].answer" id="minText_${index.index }" value="<c:out value="${view.answer[0]}"/>"/></span><p class="r"></p><em>到</em>
					</div><!--input end-->
					<div class="input"><!--input begin-->
					
					<p class="l"></p><span class="width70"><input type="text" name="userPreferences[${index.index }].answer" id="maxText_${index.index }" value="<c:out value="${view.answer[1]}"/>" /></span><p class="r"></p><em>岁</em>
					
					</div><!--input end-->
					</c:when>
					<c:otherwise>
						<div class="pj_box">
											<!--pj_box begin-->
											<textarea rows="4" cols="40" name="">${view.userPreference.answer}</textarea>
						</div>
					</c:otherwise>
				</c:choose>
				<div class="error" id="error_${index.index }" style="margin-top:0px;"></div>
				</div><!--infor_x end-->
				</c:forEach>
				<div class="save_btn"><!--save_btn begin-->
				
				<a href="javascript:void(0);" class="save">保存</a>
				
				</div><!--save_btn end-->
				
				<div class="clear"></div>
				
				</div><!--infor end-->
				
				
				
				</div><!--my_infor end-->
				
				</div><!--set_right end-->
				
				</div><!--set end-->
				
				
				</div><!--m end-->
				
				<div class="b"></div>
				
				</div><!--content end-->
			</form>
			
		</div>
		<!--main end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<script type="text/javascript" src="${jzr:static('/js/web/preference.js')}"></script>
		<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
	</div>
	<!--warp end-->
</body>
</html>
