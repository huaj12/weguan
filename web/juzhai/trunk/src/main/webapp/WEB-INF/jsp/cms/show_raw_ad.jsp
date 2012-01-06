<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr"
	uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>导入团购信息</title>
<style type="text/css">
</style>
</head>
<body>
	<h2>导入团购信息</h2>
	<div align="center">
		<form action="/cms/raw/ad/import" enctype="multipart/form-data" method="post">
			<input type="file" name="rawAd"/>
			<input type="submit" value="导入"/>
		</form>
		${msg}
	</div>
</body>
</html>