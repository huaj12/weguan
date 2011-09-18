function addAct(inputObjId){
	//validation
	var actName = $("#"+inputObjId).attr("value");
	var actId = $("#"+inputObjId).attr("actId");
	if(!actName||actName==""||isNaN(actId)){
		alert("error");
		return;
	}
	jQuery.ajax({
		url: "/app/ajax/addAct",
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
			resetAddAct(inputObjId);
		}
	});
}

function resetAddAct(inputObjId){
	$("#"+inputObjId).attr("value","");
	$("#"+inputObjId).attr("actId","0");
}

