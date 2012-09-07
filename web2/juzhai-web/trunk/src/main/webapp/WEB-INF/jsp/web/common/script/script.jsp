<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery.lazyload.min.js')}"></script>
<script type="text/javascript" src="${jzr:static('/js/jquery/waypoints.min.js')}"></script>
<link href="${jzr:static('/css/skins/simple.css')}" rel="stylesheet" type="text/css" />
<jsp:include page="/WEB-INF/jsp/web/common/dialog/dialog.jsp" />
<script type="text/javascript" src="${jzr:static('/js/artDialog/jquery.artDialog.js')}"></script>
<!-- <link href="${jzr:static('/css/select2css.css')}" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${jzr:static('/js/select2css/select2css.js')}"></script> -->
<script type="text/javascript" src="${jzr:static('/js/core/core.js')}"></script>
<script type="text/javascript" src="${jzr:static('/js/web/common/base.js')}"></script>
<script type="text/javascript" src="${jzr:static('/js/web/common/header.js')}"></script>
<c:if test="${not empty isQplus && isQplus}">
	<script type="text/javascript" src="http://cdn.qplus.com/js/qplus.api.js"></script>
	<script>
			qplus.onReady(function(){
					    	qplus.on( "app.pushParam", function(json){
							   	if(json.pushParam!=null&&json.pushParam!=""){
							   		location.href=json.pushParam;
							   	}
							});
			});
			
			function qPlusShare(msg,title,pic,desc,shareBtn){
				qplus.system.shareApp({
					msg:msg,
					title:title,
					pic:pic,
					desc:desc,
					shareBtn:shareBtn
				});
			}
	</script>					
</c:if>
