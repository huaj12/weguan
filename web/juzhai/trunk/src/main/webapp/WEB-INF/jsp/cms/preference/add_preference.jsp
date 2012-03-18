<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>添加偏好</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script>
	var i=0;
	function select_input(obj){
		i=0;
		document.getElementById("addDiv").style.display="none";
		document.getElementById("optionDiv").innerHTML="";
		if(obj.value==0){
			addInput();
			document.getElementById("addDiv").style.display="";
		}else if(obj.value==1){
			addInput();
			document.getElementById("addDiv").style.display="";
		}
	}
	function addInput(){
		var option=document.getElementById("optionDiv");
		var input = document.createElement("input");   
        input.name="input.options["+i+"].name";
        input.id="input_id_"+i;
        option.appendChild(input);
        var hidden_input=document.createElement("input");
        hidden_input.name="input.options["+i+"].value";
        hidden_input.value=i;
        hidden_input.id="hidden_input_id_"+i;
        hidden_input.type="hidden";
        option.appendChild(hidden_input);
        i++;
	}
	function deleteInput(){
		if(i==1){
			return ;
		}
		var option=document.getElementById("optionDiv");
		option.removeChild(document.getElementById("hidden_input_id_"+(i-1)));
		option.removeChild(document.getElementById("input_id_"+(i-1)));
		i--;
	}
</script>
</head>
<body>
	<h2>
		添加偏好
		<font color="red">${msg}</font>
	</h2>
	<form action="/cms/add/preference" method="post">
	<table>
		<tr>
			<td>标题</td>
			<td><input name="name" type="text"/> </td>
		</tr>
		<tr>
			<td>输入框类型</td>
			<td> 
				<select name="input.inputType" onchange="select_input(this)">	
					<option value="0">复选</option>
					<option value="1">单选</option>
					<option value="2">min-max</option>
					<option value="3">文本框</option>
				</select>
				<div id="addDiv" style="display: none">
					<a href="#" onclick="addInput()" >继续添加</a>
					<a href="#" onclick="deleteInput()" >删除按钮</a>
				</div>
				<div id="optionDiv">
				</div>
			</td>
		</tr>
		<tr>
			<td>偏好类型</td>
			<td> 
				<select name="type">
					<option value="0">用于显示</option>
					<option value="1">用于筛选</option>
					<option value="2">用于匹配</option>
				</select>
			</td>
		</tr>
		<tr>
			<td></td>
			<td> 
				<input type="checkbox" name="open" value="true"/>让用户选择是否隐藏该偏好
			</td>
		</tr>
		<tr>
			<td></td>
			<td> 
				<input type="submit"  value="提交"/>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>