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
<title>橱窗内容管理</title>
<script type="text/javascript"
	src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script>
function addPostWindow(){
	var postId=$("#postId").val();
	jQuery.ajax({
		url : "/cms/add/postwindow",
		type : "post",
		data : {
			"postId" : postId
		},
		dataType : "json",
		success : function(result) {
			if (result.success!=null&&result.success) {
				location.reload();
			} else {
				alert(result.errorInfo);
			}
		},
		statusCode : {
			401 : function() {
				alert("请先登陆");
			}
		}
	});
}

function delPostWindow(id){
	if(confirm("是否取消该条推荐")){
	jQuery.ajax({
		url : "/cms/del/postwindow",
		type : "post",
		data : {
			"id" : id
		},
		dataType : "json",
		success : function(result) {
			if (result.success!=null&&result.success) {
				location.reload();
			} else {
				alert(result.errorInfo);
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
	<h2>当前橱窗内容</h2>
	<input value="" type="text" id="postId" />
	<input type="button" onclick="addPostWindow()" value="添加" />
	<form action="/cms/sort/postwindow" method="post">
	<table border="0"  id="DynaTable"  cellspacing="4">
		<tr style="background-color: #CCCCCC;">
			<td width="100">头像</td>
			<td width="300">内容</td>
			<td width="100">操作</td>
		</tr>
		<c:forEach var="view" items="${postWindowViews}" varStatus="c">
			<tr>
				<td><img src="${jzr:userLogo(view.profileCache.uid, view.profileCache.logoPic, 80)}"  width="70" height="70" />
				</td>
				<td><c:import
						url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp">
						<c:param name="purposeType" value="${view.postWindow.purposeType}" />
					</c:import>:<c:out value="${view.postWindow.content}" /></td>
				<td>
				<input type="hidden" value="${view.postWindow.id}" name="postWindowSortForm[${c.index }].id" />
				<input type="hidden" id="sequence_${c.index }" name="postWindowSortForm[${c.index}].sequence" value="${view.postWindow.sequence}"/>
				<a href="javascript:;" onclick="delPostWindow(${view.postWindow.id})">取消</a>
					<br/>
					<input onclick="moveup(this)"  id="moveUp_${c.index }" value="上移" type="button" />
					<input onclick="moveDown(this)" id="moveDown_${c.index }" value="下移" type="button" />
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