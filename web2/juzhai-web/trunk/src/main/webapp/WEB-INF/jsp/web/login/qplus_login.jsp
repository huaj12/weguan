<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="jzr"
	uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Q+授权 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
	<script type="text/javascript" src="http://cdn.qplus.com/js/qplus.api.js"></script>
</head>
<body>
	<script>
		qplus.user.auth('${loginParams}',function(json){
			if(json.param==null||json.param=='undefined'){
				location.href='/';
			}else{
				location.href='${url}?'+json.param;	
			}
			
		});
	</script>
</body>
</html>
