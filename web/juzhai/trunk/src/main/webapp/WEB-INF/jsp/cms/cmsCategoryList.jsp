<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>类别管理</title>
<script type="text/javascript"
	src="${jz:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script>
	function deletecat(id) {
		if (id != null) {
			$.get('/cms/cmsDeleteCategory', {
				catId : id,
				random : Math.random()
			}, function(result) {
				if (result && result.success) {
					location.reload();
				} else {
					alert(result.errorInfo);
				}
			});
		}
	}
	var moveRow = false;
	function moveup(obj) {
		moveRow = obj.parentNode.parentNode;
		if (moveRow) {
			var prevRow = moveRow.previousSibling;
			while (prevRow.nodeName == '#text') {
				prevRow = prevRow.previousSibling;
			}
			if (prevRow) {
				document.getElementById("DynaTable").childNodes[1]
						.insertBefore(moveRow, prevRow);
			}
		}
		tableSort();
	}
	function moveDown(obj) {
		moveRow = obj.parentNode.parentNode;
		if (moveRow) {
			var nexRow = moveRow.nextSibling;
			while (nexRow.nodeName == '#text') {
				nexRow = nexRow.nextSibling;
			}
			if (nexRow) {
				var nnextRow = nexRow.nextSibling;
				while (nnextRow.nodeName == '#text') {
					nnextRow = nnextRow.nextSibling;
				}
				if (nnextRow) {
					document.getElementById("DynaTable").childNodes[1]
							.insertBefore(moveRow, nnextRow);
				} else {
					document.getElementById("DynaTable").childNodes[1]
							.appendChild(moveRow);
				}
			}

		}
		tableSort();
	}
	function addCatName() {
		var obj = document.getElementById("DynaTable");
		var rowIndex = obj.rows.length;
		var objTR = obj.insertRow(-1);
		var objTD = objTR.insertCell(-1);
		var objTD1 = objTR.insertCell(-1);
		var objTD2 = objTR.insertCell(-1);
		var objTD3 = objTR.insertCell(-1);
		objTD.innerHTML = "<input type=\"hidden\" id=\"sequence_"+(rowIndex-1)+"\" value=\""+rowIndex+"\" name=\"categoryFroms["+(rowIndex-1)+"].sequence\" /><input type='text' name=\"categoryFroms["+(rowIndex-1)+"].name\" value=\""
				+ $("#addCatName").val() + " \" >";
		objTD1.innerHTML = "<input name=\"categoryFroms["+(rowIndex-1)+"].hide\" type=\"checkbox\" value=\"1\" />隐藏";
		objTD2.innerHTML = "<input onclick=\"moveup(this)\" id=\"moveUp_"+(rowIndex-1)+"\"value=\"上移\"  type=\"button\"/> <input onclick=\"moveDown(this)\"  id=\"moveDown_"+(rowIndex-1)+"\" value=\"下移\" type=\"button\"/>";
		objTD3.innerHTML = "<a href=\"#\" onclick='deleteRow(this)'>删除</a>";
		tableSort();
	}
	function deleteRow(obj) {
		var tr = this.getRowObj(obj);
		if (tr != null) {
			tr.parentNode.removeChild(tr);
		} else {
			throw new Error("the given object is not contained by the table");
		}
		tableSort();
	}


	function getRowObj(obj) {
		var i = 0;
		while (obj.tagName.toLowerCase() != "tr") {
			obj = obj.parentNode;
			if (obj.tagName.toLowerCase() == "table")
				return null;
		}
		return obj;
	}
	function tableSort(){
		var objtable = document.getElementById("DynaTable");
		var rowIndex = objtable.rows.length;
		for(var i=0;i<rowIndex-1;i++){
		   moveRow = document.getElementById("sequence_"+i).parentNode.parentNode;
		   $("#sequence_"+i).val(moveRow.rowIndex);
			if(1==moveRow.rowIndex){
				$("#moveUp_"+i).attr("disabled","disabled");
			}else{
				$("#moveUp_"+i).removeAttr("disabled");
			}
			if(rowIndex==(moveRow.rowIndex+1)){
				$("#moveDown_"+i).attr("disabled","disabled");
			}else{
				$("#moveDown_"+i).removeAttr("disabled");
			}
		}
	}
	
</script>
</head>
<body>
	<h2>类别管理</h2>
	<div align="center">
		<input type="text" value="" id="addCatName" /> <input type="button"
			onclick="addCatName();" value="添加分类" />
	</div>
	<form action="/cms/cmsUpdateCategory" method="get">
		<table border="0" id="DynaTable" cellspacing="4">
			<tr style="background-color: #CCCCCC;">
				<td width="100">分类名</td>
				<td width="100">icon</td>
				<td width="100">是否隐藏</td>
				<td width="200">排序</td>
				<td width="250">删除</td>
			</tr>
			<c:forEach var="cat" items="${cats}" varStatus="c">
				<tr>
					<td><input type="hidden" value="${cat.id}"
						name="categoryFroms[${c.index }].id" />
						<input type="hidden" id="sequence_${c.index }" value="${cat.sequence}"
						name="categoryFroms[${c.index }].sequence" />
						<input
						name="categoryFroms[${c.index }].name" value="${cat.name}" />
					</td>
					<td><input
						name="categoryFroms[${c.index }].icon" value="${cat.icon}" /></td>
					<td><input name="categoryFroms[${c.index }].hide"
						 type="checkbox" value="1"
						<c:if test="${cat.sequence==0}">checked="checked"</c:if> />隐藏</td>
					<td><input onclick="moveup(this)"  id="moveUp_${c.index }" value="上移" type="button" />
						<input onclick="moveDown(this)" id="moveDown_${c.index }" value="下移" type="button" /></td>
					<td><a onclick="deletecat('${cat.id}')" href="#">删除</a>(分类下有内容则无法删除)
					</td>
				</tr>
			</c:forEach>
		</table>
		<input type="submit" value="保存" />
	</form>
	<script>
	tableSort();
	</script>
</body>
</html>