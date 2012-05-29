<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的拒宅偏好 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
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
								<div class="my_infor"><!--my_infor begin-->
								<div class="ph"><!--ph begin-->
								<input type="hidden" value="${fn:length(filterViews)}" id="filterPreference_count" />
								<input type="hidden" value="${fn:length(views)}" id="preference_count" />
								<div class="quuestion"><h2>你对哪些人的拒宅信息感兴趣？</h2></div>
								<c:forEach items="${filterViews}" var="view" varStatus="index">
										<div class="ph_x"><!--ph_x begin-->
											<div id="answerDiv_${index.index}">
											</div>
											<input type="hidden" id="preferenceId_${index.index}" value="${view.preference.id}"/>
											<input type="hidden" id="inputType_${index.index}" value="${view.input.inputType}"/>
											<input type="hidden" id="preferenceType_${index.index}" value="${view.preference.type}"/>
											<h3>${view.preference.name}</h3>
									<c:choose>
										<c:when test="${view.input.inputType==0}">
												<div class="ck"><!--ck begin-->
												<c:forEach items="${view.input.options}" var="option">
													<div class="check_sex"><b><input name="userPreferences[${index.index}].answer" type="checkbox" <c:forEach items="${view.answer}" var="box"><c:if test="${box==option.value}"> checked="checked"</c:if></c:forEach>  value="${option.value }" /></b><p>${option.name}</p></div>
												</c:forEach>
												</div><!--ck end-->
											
										</c:when>
										<c:when test="${view.input.inputType==2}">
											<div class="input"><!--input begin-->
												<p class="l"></p><span class="width20"><input type="text" name="userPreferences[${index.index}].answer" id="minText_${index.index}" value="<c:out value="${view.answer[0]}"/>"/></span><p class="r"></p><em>到</em>
												</div><!--input end-->
												<div class="input"><!--input begin-->
												<p class="l"></p><span class="width20"><input type="text" name="userPreferences[${index.index}].answer" id="maxText_${index.index}" value="<c:out value="${view.answer[1]}"/>" /></span><p class="r"></p><em>岁</em>
											</div><!--input end-->
										</c:when>
									</c:choose>
											<div class="error" id="error_${index.index}"></div>
										</div><!--ph_x end-->
								</c:forEach>	
								<c:forEach items="${views}" var="view" varStatus="index">
										<div id="answerDiv_${index.index+fn:length(filterViews)}">
										</div>
										<input type="hidden" id="preferenceId_${index.index+fn:length(filterViews)}" value="${view.preference.id}"/>
										<input type="hidden" id="inputType_${index.index+fn:length(filterViews)}" value="${view.input.inputType}"/>
										<input type="hidden" id="preferenceType_${index.index+fn:length(filterViews)}" value="${view.preference.type}"/>
										<div class="quuestion">
													<h2>${view.preference.name}</h2>
													<c:if test="${view.preference.open}" >
														<div class="check_sex"><b><input name="userPreferences[${index.index+fn:length(filterViews) }].open" type="checkbox"   <c:if test="${view.userPreference.open}"> checked="checked" </c:if>  value="true" /></b><p>不对外显示</p></div>
													</c:if>
										</div>
										<c:choose>
											<c:when test="${view.input.inputType==2}">
											<div class="ph_x"><!--ph_x begin-->
												<div class="input"><!--input begin-->
												<p class="l"></p><span class="width20"><input type="text" name="userPreferences[${index.index+fn:length(filterViews) }].answer" id="minText_${index.index+fn:length(filterViews) }" value="<c:out value="${view.answer[0]}"/>"/></span><p class="r"></p><em>到</em>
												</div><!--input end-->
												<div class="input"><!--input begin-->
												<p class="l"></p><span class="width20"><input type="text" name="userPreferences[${index.index+fn:length(filterViews) }].answer" id="maxText_${index.index+fn:length(filterViews) }" value="<c:out value="${view.answer[1]}"/>" /></span><p class="r"></p><em>岁</em>
												</div><!--input end-->
												<c:if test="${!view.preference.openDescription}" ><div class="error"  id="error_${index.index+fn:length(filterViews) }"></div></c:if>
											</div><!--ph_x end-->
											</c:when>
											<c:when test="${view.input.inputType==0}">
											<div class="ph_x"><!--ph_x begin-->
												<ul>
													<c:forEach items="${view.input.options}" var="option">
														<li><b><input name="userPreferences[${index.index+fn:length(filterViews) }].answer" type="checkbox" <c:forEach items="${view.answer}" var="box"><c:if test="${box==option.value}"> checked="checked"</c:if></c:forEach>  value="${option.value }" /></b><p>${option.name}</p></li>
													</c:forEach>	
												</ul>
												<c:if test="${!view.preference.openDescription}" ><div class="error"  id="error_${index.index+fn:length(filterViews) }"></div></c:if>
											</div><!--ph_x end-->
											</c:when>
											<c:when test="${view.input.inputType==1}">
												<div class="ph_x"><!--ph_x begin-->
												<div class="selt"><!--selt begin-->
												<select name="userPreferences[${index.index+fn:length(filterViews) }].answer" >
													<option value="">请选择</option>
													<c:forEach items="${view.input.options}" var="option">
														<option value="${option.value }"  <c:if test="${view.answer[0]==option.value}">  selected="selected" </c:if>>${option.name}</option>
													</c:forEach>
												</select>
												</div><!--selt end-->
												<c:if test="${!view.preference.openDescription}" ><div class="error"  id="error_${index.index+fn:length(filterViews) }"></div></c:if>
												</div><!--ph_x end-->
											</c:when>
											<c:when test="${view.input.inputType==3}">
											<div class="ph_x"><!--ph_x begin-->
												<div class="textarea"><textarea  name="userPreferences[${index.index+fn:length(filterViews) }].answer"  cols="" rows="">${view.answer[0]}</textarea></div>
												<c:if test="${!view.preference.openDescription}" ><div class="error"  id="error_${index.index+fn:length(filterViews) }"></div></c:if>
											</div><!--ph_x end-->
											</c:when>
										</c:choose>
										<div class="ph_x" <c:if test="${!view.preference.openDescription}" > style="display: none;"</c:if> ><!--ph_x begin-->
											<c:if test="${view.preference.openDescription}" >
												<div class="input"><!--input begin-->
												<p class="l"></p>
												<span class="width250"><input name="userPreferences[${index.index+fn:length(filterViews) }].description" type="text"  init-tip="补充说明（可不填）" value="${view.userPreference.description}" /></span><p class="r"></p>
												</div><!--input end-->
												<div class="error"  id="error_${index.index+fn:length(filterViews) }"></div>
											</c:if>
										</div><!--ph_x end-->
										<div class="clear"></div>
								</c:forEach>
								<div class="save_btn"><!--save_btn begin-->
									<a href="javascript:void(0);" class="save">保存</a>
								</div><!--save_btn end-->
								
								<div class="clear"></div>
								</div><!--ph end-->
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
