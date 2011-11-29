<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>拒宅兴趣</title>
<script type="text/javascript" src="${jz:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script type="text/javascript" src="${jz:static('/js/My97DatePicker/WdatePicker.js')}"></script>
<script type="text/javascript">
	function showResult(){
		var queryDate = $("#queryDate").val();
		if(queryDate==null||queryDate==""){
			alert("请选择日期");
		}
		jQuery.ajax({
			url: "/cms/showRecommndWantStats",
			type: "post",
			data: {"queryDate": queryDate},
			dataType: "json",
			success: function(result){
				$("#result").text(result.result);
			},
			statusCode: {
			    401: function() {
			      alert("未登录");
			    }
			}
		});
	}
</script>
</head>
<body>
	<h2>推荐点击统计</h2>
	<table>
		<tr>
			<td><input type="text" id="queryDate" onclick="WdatePicker();" value="${queryDate}" /></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="查询" onclick="javascript:showResult();"/></td>
		</tr>
	</table>
	<table border="0" cellspacing="4">
		<tr style="background-color: #CCCCCC;">
			<td id="result"></td>
		</tr>
	</table>
</body>
</html>