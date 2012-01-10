$(document).ready(function() {
	//添加约
	$("div.system_tz > ul > li > span > a").bind("click", function(){
		var sysNoticeId = $(this).attr("sysnoticeId");
		var obj = this;
		var content = $("#dialog-confirm").html().replace("{0}", "确定要删除吗？");
		showConfirm(obj, "delSysNotice", content, function(){
			delSysNotice(obj, sysNoticeId);
		});
		
	});
});

function delSysNotice(obj, sysNoticeId){
	jQuery.ajax({
		url : "/notice/delSysNotice",
		type : "post",
		cache : false,
		data : {"sysNoticeId" : sysNoticeId},
		dataType : "json",
		success : function(result) {
			if (result && result.success) {
				$(obj).parent().parent().remove();
			} else {
				alert(result.errorInfo);
			}
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}