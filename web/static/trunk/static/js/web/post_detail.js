$(document).ready(function(){
	$("div.post-response > a").click(function(){
		var postId = $(this).attr("post-id");
		var obj = $(this);
		responsePost(this, postId, function(){
			var currentCnt = parseInt(obj.find("font").text());
			obj.find("font").text(currentCnt + 1);
			obj.text("已" + obj.text()).unbind("click").parent().addClass("done");
		});
	});
	$("a.send-message").bind("click", function(){
		var uid = $(this).attr("target-uid");
		var nickname = $(this).attr("target-nickname");
		openMessage(uid, nickname);
	});
	$("div.user-remove-interest > a.done").bind("click", function() {
		var uid = $(this).attr("uid");
		removeInterestConfirm(uid, this, function(){
			$("div.remove-interest-" + uid).hide();
			$("div.interest-" + uid).attr("style", "");
		});
	});
	$("div.user-add-interest > a").bind("click", function() {
		var uid = $(this).attr("uid");
		interest(this, uid, function(){
			$("div.interest-" + uid).hide();
			$("div.remove-interest-" + uid).attr("style", "");
		});
	});
	
	$("div.own_btn > a.delete").click(function(){
		var postId = $(this).attr("post-id");
		var content = $("#dialog-confirm").html().replace("{0}", "确定删除此条拒宅么？");
		showConfirm(this, "removePost", content, function(){
			jQuery.ajax({
				url : "/post/deletePost",
				type : "post",
				cache : false,
				data : {"postId" : postId},
				dataType : "json",
				success : function(result) {
					if (result && result.success) {
						window.location.href = "/home";
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
		});
	});
});