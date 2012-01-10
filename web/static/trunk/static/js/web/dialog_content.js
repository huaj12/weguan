$(document).ready(function(){
	
	$("div.message_item > div.btn > a.delete").bind("click", function(){
		var dialogId = $(this).attr("dialog-id");
		var targetName = $(this).attr("target-name");
		var obj = this;
		var content = $("#dialog-confirm").html().replace("{0}", "确定删除所有与 " + targetName + " 的私信么？");
		showConfirm(this, "delDialog", content, function(){
			jQuery.ajax({
				url : "/home/deleteDialog",
				type : "post",
				cache : false,
				data : {"dialogId" : dialogId},
				dataType : "json",
				success : function(result) {
					if (result && result.success) {
						$(obj).parent().parent().parent().parent().remove();
					} else {
						alert("操作异常！");
					}
				},
				statusCode : {
					401 : function() {
						window.location.href = "/login?turnTo=" + window.location.href;
					}
				}
			});
		});
	});
	
	$("div.message_item > div.btn > a.repy").bind("click", function(){
		var targetUid = $(this).attr("target-uid");
		var targetName = $(this).attr("target-name");
		openMessage(targetUid, targetName);
	});
	
	bindReply();
	bindDelDialogContent();
});

function bindReply(){
	$body = (window.opera) ? (document.compatMode == "CSS1Compat" ? $('html') : $('body')) : $('html,body');// 这行是 Opera 的补丁, 少了它 Opera 是直接用跳的而且画面闪烁 by willin
	$("a#repy-btn").bind("click", function(){
		$body.animate({scrollTop: $("#respond").offset().top}, 500, function(){
			$("div.text_area > em > textarea").focus();
		});
		return false;
	});
}

function bindDelDialogContent(){
	$("div.repy_box > span > div.btn > a#del-btn").bind("click", function(){
		var dialogContentId = $(this).attr("dialog-content-id");
		var targetUid = $(this).attr("target-uid");
		var obj = this;
		var content = $("#dialog-confirm").html().replace("{0}", "确定删除么？");
		showConfirm(this, "delDialogContent", content, function(){
			jQuery.ajax({
				url : "/home/deleteDialogContent",
				type : "post",
				cache : false,
				data : {"dialogContentId" : dialogContentId, "targetUid" : targetUid},
				dataType : "json",
				success : function(result) {
					if (result && result.success) {
						if(result.result > 0){
							$(obj).parent().parent().parent().parent().remove();
						}else{
							window.location.href = "/home/dialog/1";
						}
					} else {
						alert("操作异常！");
					}
				},
				statusCode : {
					401 : function() {
						window.location.href = "/login?turnTo=" + window.location.href;
					}
				}
			});
		});
	});
}