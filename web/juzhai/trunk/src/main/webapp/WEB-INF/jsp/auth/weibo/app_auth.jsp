<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>loading...</title>
</head>
<body background="${jz:static('/images/renrenbg.jpg')}">
	<script
		src="http://tjs.sjs.sinajs.cn/t35/apps/opent/js/frames/client.js"
		language="JavaScript"></script>
	<script>
			App.AuthDialog.show({
				client_id :'${tp.appKey}', //必选，appkey
				redirect_uri :'${tp.appUrl}', //必选，授权后的回调地址，例如：http://apps.weibo.com/giftabc
				height : 220
			//可选，默认距顶端120px
			});
	</script>
	<%-- <jsp:include page="/WEB-INF/jsp/common/app/foot.jsp" /> --%>
</body>
</html>
