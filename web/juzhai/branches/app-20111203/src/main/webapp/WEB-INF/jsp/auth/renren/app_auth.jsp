<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>loading...</title>
	</head>
	<body background="${jz:static('/images/renrenbg.jpg')}">
		<jsp:include page="/WEB-INF/jsp/common/app/script/script.jsp" />
		<script type="text/javascript" src="${jz:static('/js/core/app/renren/renren_plugin.js')}"></script>
		<script type="text/javascript">
			  var uiOpts = {
				  url : "http://graph.renren.com/oauth/authorize",
				  display : "iframe",
				  params : {"response_type":"token","client_id":"${tp.appId}","scope":"publish_feed"},
				  onSuccess: function(r){
				    top.location = "${tp.appUrl}";
				  },
				  onFailure: function(r){} 
			  };
			  Renren.ui(uiOpts);
		</script>
		<%-- <jsp:include page="/WEB-INF/jsp/common/app/foot.jsp" /> --%>
	</body>
</html>
