function addAct(id){
	//validation
	var actName = $("#"+id).attr("value");
	var actId = $("#"+id).attr("actId");
	if(!actName||actName==""||isNaN(actId)){
		alert("error");
		return;
	}
	jQuery.ajax({
		url: "/app/addAct",
		type: "post",
		data: {"actName": actName, "actId": actId},
		dataType: "json",
		success: function(result){
			if(result&&result.success){
				alert("保存成功");
			}else if(result){
				alert(result.errorInfo);
			}
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		},
		complete: function(){
			resetAddAct(id);
		}
	});
}

function resetAddAct(id){
	$("#"+id).attr("value","");
	$("#"+id).attr("actId","0");
}