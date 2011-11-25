<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>拒宅器</title>
		<c:set var="cssFile" value="/css/jz_${context.tpName}.css" />
		<link href="${jz:static(cssFile)}" rel="stylesheet" type="text/css" />
		<link href="${jz:static('/css/jquery.autocomplete.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<jsp:include page="/WEB-INF/jsp/common/app/${context.tpName}/top_logo.jsp" />
		<div class="main"><!--main begin-->
			<jsp:include page="/WEB-INF/jsp/common/app/app_header.jsp" />
			<div class="skin_body"><!--skin_body begin-->
				<div class="skin_top_bg"><!--content_bg begin-->
					<div class="content white"><!--content begin-->
						<div class="top"></div>
						<div class="mid"><!--mid begin-->
							<div class="my_page"><!--my_page begin-->
								<div class="more_friend"><a href="javascript:void(0);" onclick="javascript:history.go(-1);">返回上一页&gt;&gt;</a></div>
								<div class="head_area"><!--head_area begin-->
									<div class="photo"><a href="${jz:tpHomeUrl(profile.tpIdentity,context.tpId)}" target="_blank"><img src="${profile.logoPic}"  width="80" height="80"/></a></div>
									<h2><a href="${jz:tpHomeUrl(profile.tpIdentity,context.tpId)}" target="_blank" class="uname"><c:out value="${profile.nickname}"/></a><a href="/app/showLive" class="fxgd">发现更多>></a></h2>
									<p>ta在<c:choose><c:when test="${profile.city > 0}">${jz:cityName(profile.city)}</c:when><c:otherwise>地球</c:otherwise></c:choose><c:if test="${lastUpdateTime!=null}">&nbsp;&nbsp;最近更新于<fmt:formatDate value="${lastUpdateTime}" pattern="yyyy.MM.dd"/></c:if></p>
									<span><a onclick="showAbout('${profile.nickname}','${act.id}','${act.name}','${profile.uid}');" href="javascript:void(0);">给ta留言</a></span>
								</div><!--head_area end-->
								<c:if test="${sameActList.size() > 0}">
									<div class="gtah"><!--gtah begin-->
										<h3>你们的共同爱好</h3>
										<div class="clear"></div>
										<ul>
											<c:forEach var="act" items="${sameActList}">
												<li>
													<p><a href="/app/showAct/${act.id}"><img src="${jz:actLogo(act.id,act.logo,80)}"  width="80" height="80"/></a></p>
													<h2><a href="/app/showAct/${act.id}"><c:out value="${act.name}" /></a></h2>
												</li>
											</c:forEach>
										</ul>
									</div><!--gtah end-->
								</c:if>
								<div class="txqd"><!--hwg begin-->
									<jsp:include page="user_home_act_list.jsp" />
								</div><!--hwg end-->
							</div><!--my_page end-->
						</div><!--mid end-->
						<div class="bot"></div>
					</div><!--content end-->
				</div><!--content_bg end-->
			</div><!--skin_body end-->
			<div class="skin_bottom"></div>
		</div><!--main end-->
		<jsp:include page="/WEB-INF/jsp/common/app/script/script.jsp" />
		<jsp:include page="/WEB-INF/jsp/common/app/sendMessage.jsp" />
		<script type="text/javascript" src="${jz:static('/js/module/user_home.js')}"></script>
		<jsp:include page="/WEB-INF/jsp/common/app/script/app.jsp" />
		<jsp:include page="/WEB-INF/jsp/common/app/foot.jsp" />
	</body>
</html>