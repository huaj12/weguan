$(document).ready(function(){
	$("a.robot-interest").bind("click", function(){
		var cityId = $(this).attr("city-id");
		var uid = $(this).attr("target-uid");
		var postId = $(this).attr("post-id");
		jQuery.ajax({
			url : "/cms/robot/interest",
			type : "post",
			data : {"cityId" : cityId,"targetUid":uid},
			dataType : "json",
			success : function(result) {
				if (result && result.success) {
					$("#robot-interest-"+postId).text("已关注").unbind("click");
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
		return false;
	});
	
	$("a.robot-comment").bind("click", function(){
		var text=prompt("请输入留言内容","");
		var cityId = $(this).attr("city-id");
		var postId = $(this).attr("post-id");
		if(text==null||text==""){
			alert("请输入留言内容");
			return;
		}
		jQuery.ajax({
			url : "/cms/robot/comment",
			type : "post",
			data : {"cityId" : cityId,"postId":postId,"text":text},
			dataType : "json",
			success : function(result) {
				if (result && result.success) {
					$("#robot-comment-"+postId).text("已留言").unbind("click");
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
		return false;
	});
	
	
	$("a.robot-response").bind("click", function(){
		var cityId = $(this).attr("city-id");
		var postId = $(this).attr("post-id");
		jQuery.ajax({
			url : "/cms/robot/response",
			type : "post",
			data : {"cityId" : cityId,"postId":postId},
			dataType : "json",
			success : function(result) {
				if (result && result.success) {
					$("#robot-response-"+postId).text("已响应").unbind("click");
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
		return false;
	});
});