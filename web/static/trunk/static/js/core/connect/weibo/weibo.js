function invite(uids,actId){
	jQuery.ajax({
			url: "/plug/weibo/invite",
			type: "get",
			data: {
				"uids" : uids
			},
			dataType: "json",
			success: function(data){
				$("#invite_content").val(data.message);
				$("#invite_act_id").val(actId);
				$.dialog({
					lock : true,
					content :$("#weibo_ivite")[0],
					top : "50%"
				});
				
			},
			statusCode: {
			    401: function() {
			    	window.location.href = "/login";
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
				successCallback();
			}else{
				var content = $("#dialog-success").html().replace("{0}", "发送失败");
				showSuccess(null, content);
				successCallback();
			}
		},
		statusCode: {
		    401: function() {
		    	window.location.href = "/login";
		    }
		}
	});
}