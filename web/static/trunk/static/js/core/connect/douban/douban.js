function invite(names, uids, actId) {
	if (undefined == actId) {
		actId = 0;
	}
	if (undefined == uids) {
		uids = "";
	}
	jQuery.ajax({
		url : "/plug/show/invite",
		type : "get",
		data : {
			"uids" : uids,
			"names" : names
		},
		dataType : "html",
		success : function(data) {
			openDialog(null, 'invite_dialog', data);
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}
function send_invite() {
	var content = $("#invite_content").val();
	var fuids= $("#fuids").val();
	jQuery.ajax({
		url : "/plug/invite/send",
		type : "post",
		data : {
			"content" : content,
			"fuids" :fuids
		},
		dataType : "json",
		success : function(result) {
			if (result.success) {
				var content = $("#dialog-success").html()
						.replace("{0}", "发送成功");
				showSuccess(null, content);
			} else {
				$("#plug_invite_tip").html("发送失败");
			}
			closeDialog('invite_dialog');
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}