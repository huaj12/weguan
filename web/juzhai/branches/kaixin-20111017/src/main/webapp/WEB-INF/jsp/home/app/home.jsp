<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>拒宅器</title>
		<link href="${jz:static('/css/jz.css')}" rel="stylesheet" type="text/css" />
		<link href="${jz:static('/css/jquery.autocomplete.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="main"><!--main begin-->
			<jsp:include page="/WEB-INF/jsp/common/app/app_header.jsp" />
			<div class="skin_body"><!--skin_body begin-->
				<div class="skin_top_bg"><!--content_bg begin-->
					<jsp:include page="/WEB-INF/jsp/common/app/app_prompt.jsp" />
					<jsp:include page="/WEB-INF/jsp/common/app/app_point.jsp" />
					<div class="content white" style="display:none;"><!--content begin-->
					</div><!--content end-->
					<div class="loading_home"><!--loading begin-->
						<div class="top"></div>
						<div class="mid"><!--mid begin-->
							<span><img src="${jz:static('/images/loading.gif')}" /></span><p></p>
						</div><!--mid end-->
						<div class="bot"></div>
					</div><!--loading end-->
					<div class="check_box tz1 <c:choose><c:when test="${isAdvise==null||isAdvise}">tz_secleted</c:when><c:otherwise>tz_link</c:otherwise></c:choose>">
						<p></p>
						<span>添加兴趣时,告知同兴趣的同城好友</span>
					</div>
				</div><!--content_bg end-->
			</div><!--skin_body end-->
			<jsp:include page="/WEB-INF/jsp/common/app/app_bottom.jsp" />
		</div><!--main end-->
		<jsp:include page="/WEB-INF/jsp/common/app/script/script.jsp" />
		<script type="text/javascript" src="${jz:static('/js/module/home.js')}"></script>
		<%-- <c:if test="${actNames != null}">
			<script type="text/javascript">
				var firstFeed = function(){
					var actNames = "${actNames}";
					kaixinFeed(actNames);
				};
			</script>
		</c:if>
		<script type="text/javascript">
			$(document).ready(function(){
				try{
					if(firstFeed!=null){
						firstFeed();
					}
				}catch (e) {
				}
			});
		</script> --%>
		<script type="text/javascript" src="${jz:static('/js/base/kaixin_plugin.js')}"></script>
		<jsp:include page="/WEB-INF/jsp/common/app/foot.jsp" />
	</body>
</html>