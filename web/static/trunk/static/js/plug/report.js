function report(uid,contentType,content,contentId) {
	jQuery.ajax({
		url : "/plug/report/show",
		type : "get",
		data : {
			"uid" : uid,
			"contentType" : contentType,
			"content" : content,
			"contentId":contentId
		},
		dataType : "html",
		success : function(data) {
			openDialog(null, 'report_dialog', data);
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}

function save_report(){
	var description=$("input[name=description]").val();
	if(getByteLen(description)>400){
		$("#report_description_tip").html("字数控制在200字以内").stop(true, true).show()
		.fadeOut(4000);
	}
	jQuery.ajax({
		url: "/plug/report/save",
		type: "post",
		data:  $("#report_form").serialize(),
		dataType: "json",
		success: function(result){
			if(result&&result.success){
				var content = $("#dialog-success").html().replace("{0}", "提交成功！");
				showSuccess(null, content);
			}else{
				alert(result.errorInfo);
			}
			closeDialog('report_dialog');
		},
		statusCode: {
		    401: function() {
		    	window.location.href = "/login?turnTo=" + window.location.href;
		    }
		}
	});
}