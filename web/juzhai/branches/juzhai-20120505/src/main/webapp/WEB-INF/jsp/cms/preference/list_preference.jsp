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
	function delete_preference(id){
		if(confirm("是否删除该条拒宅")){
			jQuery.ajax({
				url : "/cms/delete/preference",
				type : "get",
				data : {
					"id" : id
				},
				dataType : "json",
				success : function(result) {
					if (result.success!=null&&result.success) {
						location.reload();
					} else {
						alert("屏蔽失败");
					}
				},
				statusCode : {
					401 : function() {
						alert("请先登陆");
					}
				}
			});
		}
	}
	
	function load_preference(){
		if(confirm("请确定此时没有用户操作！")){
			jQuery.ajax({
				url : "/cms/load/preference",
				type : "get",
				dataType : "json",
				success : function(result) {
					if (result.success!=null&&result.success) {
						alert("更新成功");
					} else {
						alert("操作失败");
					}
				},
				statusCode : {
					401 : function() {
						alert("请先登陆");
					}
				}
			});
		}
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
	
	function updateSortPreference(){
			jQuery.ajax({
				url : "/cms/update/sort/preference",
				type : "post",
				data :$("#sortPreferenceForm").serialize(),
				dataType : "json",
				success : function(result) {
					if (result.success!=null&&result.success) {
						location.reload();
					} else {
						alert("操作失败");
					}
				},
				statusCode : {
					401 : function() {
						alert("请先登陆");
					}
				}
			});
	}
</script>
</head>
<body>
	<h2>偏好设置排序   <a href="#" onclick="load_preference();">更新前台偏好设置列表</a></h2>
	<form  id="sortPreferenceForm">
		<table border="0" id="DynaTable" cellspacing="4">
			<tr style="background-color: #CCCCCC;">
				<td width="100">题目名称</td>
				<td width="200">排序</td>
				<td width="250">删除</td>
			</tr>
			<c:forEach items="${prefernces}" var="p" varStatus="c">
			<tr>
				<td>${p.name}</td>
				<td><input type="text" readonly="readonly" id="sequence_${c.index }" value="${p.sequence}"
						name="preferenceForms[${c.index}].sequence" />
						</br>
						<td><input onclick="moveup(this)"  id="moveUp_${c.index }" value="上移" type="button" />
						<input onclick="moveDown(this)" id="moveDown_${c.index }" value="下移" type="button" /></td>
				
				</td>
				<td>
				<a href="#" onclick="delete_preference('${p.id}')">屏蔽</a>
				<a href="/cms/show/update/preference?id=${p.id}" >修改</a>
				</td>
			</tr>	
			</c:forEach>
		</table>
		<input type="button" onclick="updateSortPreference();" value="保存" />
	</form>
	<script>
	tableSort();
	</script>
</body>
</html>