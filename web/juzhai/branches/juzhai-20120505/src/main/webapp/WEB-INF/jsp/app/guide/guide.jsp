<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>新手引导</title>
		<link href="${jz:static('/css/jz.css')}" rel="stylesheet" type="text/css" />
		<link href="${jz:static('/css/jquery.autocomplete.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<jsp:include page="/WEB-INF/jsp/app/common/${context.tpName}/top_logo.jsp" />
		<div class="main"><!--main begin-->
			<div class="skin_top_new"></div>
			<div class="skin_body"><!--skin_body begin-->
				<div class="skin_top_bg_new"><!--content_bg begin-->
					<jsp:include page="/WEB-INF/jsp/app/common/app_guide_header.jsp" />
					<div class="content white mt50"><!--content begin-->
						<div class="top"></div>
						<div class="mid"><!--mid begin-->
							<c:choose>
								<c:when test="${step==1}"><c:set var="classSuffix" value="travel" /></c:when>
								<c:when test="${step==2}"><c:set var="classSuffix" value="sport" /></c:when>
								<c:when test="${step==3}"><c:set var="classSuffix" value="joy" /></c:when>
								<c:when test="${step==4}"><c:set var="classSuffix" value="travel" /></c:when>
								<c:when test="${step==5}"><c:set var="classSuffix" value="leisure" /></c:when>
								<c:when test="${step==6}"><c:set var="classSuffix" value="food" /></c:when>
							</c:choose>
							<div class="basic_yd sec_${classSuffix}"><!--basic_yd begin-->
								<h3 class="fl"><img src="${jzr:userLogo(profile.uid,profile.logoPic,80)}"  width="80" height="80"/></h3>
								<div class="sec fr"><!--sec begin-->
									<%--  <h2>选择你最近感兴趣的${actCategory.name}项目</h2> --%>
									<h2>我最近想找朋友去:(添加最多5个兴趣)</h2>
									<div id="acts">
										<c:forEach var="act" items="${actList}">
											<p><span class="fl"></span><a href="javascript:;" class="key_words" actId="${act.id}" title="点击添加"><c:out value="${act.name}" /></a><span class="fr"></span><em></em></p>
										</c:forEach>
									</div>
									<div class="add_insterest">
										<strong><input id="actName" type="text" /></strong>
										<a href="javascript:;" class="add">添加</a>
										<div id="actNameError" class="error" style="display:none"></div>
										<div class="goon_add" style="display:none;">请继续添加，或点击下一步!</div>
									</div>
								</div><!--sec end-->
							</div><!--basic_yd end-->
						</div><!--mid end-->
						<div class="bot"></div>
						<!-- <div class="round_num">
							<c:forEach begin="1" end="${totalStep}" var="index">
								<span <c:if test="${step==index}">class="hover"</c:if>></span>
							</c:forEach>
						</div> -->
						<form id="nextForm" action="/app/guide/next" method="post">
						</form>
						<div class="next_btn"><a href="javascript:;" class="next"><c:choose><c:when test="${step == totalStep}">进入拒宅</c:when><c:otherwise>下一步</c:otherwise></c:choose></a></div>
					</div><!--content end-->
					<div class="check_box tz <c:choose><c:when test="${isAdvise==null||isAdvise}">tz_secleted</c:when><c:otherwise>tz_link</c:otherwise></c:choose>">
						<p></p>
						<span>添加兴趣时,告知同兴趣的同城好友</span>
					</div>
				</div><!--content_bg end-->
			</div><!--skin_body end-->
			<div class="skin_bottom"></div>
		</div><!--main end-->
		<jsp:include page="/WEB-INF/jsp/app/common/script/script.jsp" />
		<script type="text/javascript" src="${jz:static('/js/module/guide.js')}"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				
			});
		</script>
		<jsp:include page="/WEB-INF/jsp/app/common/script/app.jsp" />
		<jsp:include page="/WEB-INF/jsp/app/common/foot.jsp" />
	</body>
</html>
