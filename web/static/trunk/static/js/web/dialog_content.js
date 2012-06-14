$(document).ready(function(){
	
	$("div.delete > a").bind("click", function(){
		var dialogId = $(this).attr("dialog-id");
		var targetName = $(this).attr("target-name");
		var obj = this;
		var content = $("#dialog-confirm").html().replace("{0}", "确定删除与 " + targetName + " 的所有私信么？");
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
		return false;
	});
	
	$("div.message_item > div.btn > a.repy").bind("click", function(){
		var targetUid = $(this).attr("target-uid");
		var targetName = $(this).attr("target-name");
		openMessage(targetUid, targetName);
		return false;
	});
	
	$("div.message_item > div.btn > a.jubao").bind("click", function(){
		var targetUid = $(this).attr("target-uid");
		var targetContent = $(this).attr("target-content");
		report(targetUid,'1',targetContent,targetUid);
		return false;
	});
	$("div.message_item > div.btn > a.pingbi").bind("click", function(){
		bindShieldUid($(this));
		return false;
	});
	
	$("div.message_repy > div.repy_area > div.text_area > a.send").bind("click", function(){
		$(this).hide();
		$(this).next().show();
		var targetUid = $(this).attr("target-uid");
		var content = $(this).prev().prev().children("textarea").val();
		var obj = this;
		if(!checkValLength(content, 1, 400)){
			$(obj).next().next().text("私聊内容字数控制在1-200个汉字内").show();
			$(obj).next().hide();
			$(obj).show();
			return false;
		}
		jQuery.ajax({
			url : "/home/replyMessage",
			type : "post",
			cache : false,
			data : {"targetUid" : targetUid, "content" : content},
			dataType : "html",
			success : function(result) {
				result = $.trim(result);
				var isJson = /^{.*}$/.test(result); 
				if(isJson){
					var jsonResult = (new Function("return " + result))();
					if(jsonResult.errorCode=="00004"){
						$(obj).next().next().html("<a href='/home/guide'class='txt' target='_blank'>"+jsonResult.errorInfo+"</a>").show();
					}else{	
						$(obj).next().next().text(jsonResult.errorInfo).show();
					}
				} else {
					$(obj).prev().prev().children("textarea").val("");
					$("div.repy_list_body").prepend(result);
					$("div.repy_list_item").first().fadeIn("slow");
				}
				$(obj).next().hide();
				$(obj).show();
			},
			statusCode : {
				401 : function() {
					window.location.href = "/login?turnTo=" + window.location.href;
				}
			}
		});
		return false;
	});
	
	bindReply();
	bindDelDialogContent();
	bindReport();
	bindShield();
});

function bindReply(){
	$("a.repy-btn").bind("click", function(){
		$body.animate({scrollTop: $("#respond").offset().top}, 500, function(){
			$("div.text_area > em > textarea").focus();
		});
		return false;
	});
}
function bindReport(){
	$("div.repy_box > span > div.btn > a.dialog-report").bind("click", function(){
		var dialogContent = $(this).attr("dialog-content");
		var targetUid = $(this).attr("target-uid");
		report(targetUid,'1',dialogContent,targetUid);
		return false;
	});
}
function bindShield(){
	$("div.repy_box > span > div.btn > a.dialog-pinbi").bind("click", function(){
		bindShieldUid($(this));
		return false;
	});
}

function bindDelDialogContent(){
	$("div.repy_box > span > div.btn > a.del-btn").bind("click", function(){
		var dialogContentId = $(this).attr("dialog-content-id");
		var targetUid = $(this).attr("target-uid");
		var obj = this;
		var content = $("#dialog-confirm").html().replace("{0}", "确定删除这条私信么？");
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
		return false;
	});
}