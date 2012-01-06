function invite(uids,actId){
	if(undefined==actId){
		actId=0;
	}
	jQuery.ajax({
			url: "/plug/weibo/invite",
			type: "get",
			data: {
				"uids" : uids
			},
			dataType: "html",
			success: function(data){
				openDialog(null, 'invite_dialog', data);
				$("#invite_act_id").val(actId);
			},
			statusCode: {
			    401: function() {
			    	window.location.href = "/login?turnTo=" + window.location.href;
			    }
			}
		});
}
function send_invite(){
	var content=$("#invite_content").val();
	var actId=$("#invite_act_id").val();
	if(actId==""||actId==null){
		actId=0;
	}
	jQuery.ajax({
		url: "/plug/weibo/invite/send",
		type: "post",
		data: {
			"content" : content,
			"actId" : actId
		},
		dataType: "json",
		success: function(result){
			if(result.success){
				var content = $("#dialog-success").html().replace("{0}", "发送成功");
				showSuccess(null, content);
			}else{
				var content = $("#dialog-success").html().replace("{0}", "发送失败");
				showSuccess(null, content);
			}
			closeDialog('invite_dialog');
		},
		statusCode: {
		    401: function() {
		    	window.location.href = "/login?turnTo=" + window.location.href;
		    }
		}
	});
}