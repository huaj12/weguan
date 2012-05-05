<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>更新偏好</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script>
	var i='${fn:length(view.input.options)}';
	var type='${view.input.inputType}';
	function select_input(obj){
		type=obj.value;
		i=0;
		document.getElementById("min_max_div").style.display="none";
		document.getElementById("addDiv").style.display="none";
		document.getElementById("optionDiv").innerHTML="";
		if(obj.value==0){
			addInput();
			document.getElementById("addDiv").style.display="";
		}else if(obj.value==1){
			addInput();
			document.getElementById("addDiv").style.display="";
		}else if(obj.value==2){
			document.getElementById("min_max_div").style.display="";
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
        if(type==0){       
	    	var box_input = document.createElement("input");   
			box_input.name="defaultAnswer";
			box_input.id="input_box_id_"+i;
			box_input.type="checkbox";
			box_input.value=i;
	        option.appendChild(box_input);	
 		}
        i++;
	}
	function deleteInput(){
		if(i==1){
			return ;
		}
		var option=document.getElementById("optionDiv");
		option.removeChild(document.getElementById("hidden_input_id_"+(i-1)));
		option.removeChild(document.getElementById("input_id_"+(i-1)));
		if(type==0){
			option.removeChild(document.getElementById("input_box_id_"+(i-1)));
		}
		i--;
	}
	
	function add(){
		if(type==0){
			document.getElementById("min_max_div").innerHTML="";
		}else if(type==2){
			document.getElementById("optionDiv").innerHTML="";
		}else if(type==1){
			document.getElementById("min_max_div").innerHTML="";
		}
		jQuery.ajax({
			url : "/cms/update/preference",
			type : "post",
			data :$("#preferenceForm").serialize(),
			dataType : "json",
			success : function(result) {
				if (result.success!=null&&result.success) {
					location.href="/cms/list/preference";
				} else {
					alert("更新失败");
				}
			},
			statusCode : {
				401 : function() {
					alert("请先登陆");
				}
			}
		});
		
	}
	function cleanInput(){
		$('input[name="defaultAnswer"]').each(function(){
			this.checked=false;
		});
	}
</script>
</head>
<body>
	<h2>
		更新偏好
	</h2>
	<c:choose>
		<c:when test="${view.input.inputType==1}">
			<c:set value="radio" var="boxType"></c:set>
		</c:when>
		<c:otherwise>
			<c:set value="checkbox" var="boxType"></c:set>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${empty view }">
			返回上级页面重试
		</c:when>
		<c:otherwise>
		<form id="preferenceForm">
	<table>
		<tr>
			<td>标题</td>
			<td><input name="name" type="text" value="${view.preference.name }"/>
				<input name="id" type="hidden" value="${view.preference.id}" />
			 </td>
		</tr>
		<tr>
			<td>输入框类型</td>
			<td> 
				<select name="input.inputType" onchange="select_input(this)">	
					<option value="0" <c:if test="${view.input.inputType==0}">selected="selected" </c:if>>复选</option>
					<option value="1" <c:if test="${view.input.inputType==1}">selected="selected" </c:if>>单选</option>
					<option value="2" <c:if test="${view.input.inputType==2}">selected="selected" </c:if>>min-max</option>
					<option value="3" <c:if test="${view.input.inputType==3}">selected="selected" </c:if>>文本框</option>
				</select>
				
				<div id="addDiv" <c:if test="${view.input.inputType==2||view.input.inputType==3 }">style="display: none"</c:if> >
					<a href="#" onclick="addInput()" >继续添加</a>
					<a href="#" onclick="deleteInput()" >删除按钮</a>
					<a href="#" onclick="cleanInput()" >清空选项</a>
				</div>
				<div id="min_max_div" <c:if test="${view.input.inputType!=2}">style="display: none"</c:if>>
				默认值：
				<input type="text" value="${view.defaultValues[0] }" name="defaultAnswer"/>
				<input type="text" value="${view.defaultValues[1] }" name="defaultAnswer"/>
				</div>
				<div id="optionDiv">
					<c:forEach items="${view.input.options}" var="option" varStatus="index">
						<input value="${option.name}"  id="input_id_${index.index}" name="input.options[${index.index}].name" type="text"/>
						<input type="${boxType}"  name="defaultAnswer" id="input_box_id_${index.index}" <c:forEach items="${view.defaultValues }" var="box"><c:if test="${box==option.value}"> checked="checked"</c:if></c:forEach> value="${option.value}" />
						<input value="${option.value}" id="hidden_input_id_${index.index}" name="input.options[${index.index}].value" type="hidden"/>
					</c:forEach>
				</div>
			</td>
		</tr>
		<tr>
			<td>偏好类型</td>
			<td> 
				<select name="type">
					<option value="0" <c:if test="${view.preference.type==0}">selected="selected" </c:if> >用于显示</option>
					<option value="1" <c:if test="${view.preference.type==1}">selected="selected" </c:if>>用于筛选</option>
					<option value="2" <c:if test="${view.preference.type==2}">selected="selected" </c:if>>用于匹配</option>
				</select>
			</td>
		</tr>
		<tr>
			<td></td>
			<td> 
				<input type="checkbox" name="open" <c:if test="${view.preference.open}">checked="checked"</c:if> value="true"/>让用户选择是否隐藏该偏好
			</td>
		</tr>
		<tr>
			<td></td>
			<td> 
				<input type="checkbox" name="openDescription" <c:if test="${view.preference.openDescription}">checked="checked"</c:if> value="true"/>是否有补充说明
			</td>
		</tr>
		<tr>
			<td></td>
			<td> 
				<input type="button" onclick="add();"  value="提交"/>
			</td>
		</tr>
	</table>
	</form>
		</c:otherwise>
	</c:choose>
</body>
</html>