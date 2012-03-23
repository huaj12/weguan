$(document).ready(function(){
	
	$("a.detail-response").click(function(){
		var postId = $(this).attr("post-id");
		var nickname = $(this).attr("nickname");
		var postContent = $(this).attr("post-content");
		var obj = this;
		openResponse(obj, postId, nickname, postContent, function(){
			$(obj).unbind("click").addClass("done").text("已响应");
		});
		return false;
	});
	
	$("a.send-message").bind("click", function(){
		var uid = $(this).attr("target-uid");
		var nickname = $(this).attr("target-nickname");
		openMessage(uid, nickname);
		return false;
	});
	$("div.user-remove-interest > a.done").bind("click", function() {
		var uid = $(this).attr("uid");
		removeInterestConfirm(uid, this, function(){
			$("div.remove-interest-" + uid).hide();
			$("div.interest-" + uid).attr("style", "");
		});
		return false;
	});
	$("div.user-add-interest > a").bind("click", function() {
		var uid = $(this).attr("uid");
		interest(this, uid, function(){
			$("div.interest-" + uid).hide();
			$("div.remove-interest-" + uid).attr("style", "");
		});
		return false;
	});
	
	$("div.own_btn > a.delete").click(function(){
		var postId = $(this).attr("post-id");
		var content = $("#dialog-confirm").html().replace("{0}", "确定删除此条拒宅么？");
		showConfirm(this, "removePost", content, function(){
			deletePost(postId, function(){window.location.href = "/home/posts";});
		});
		return false;
	});
	
	$("div.own_btn > a.edit").click(function(){
		var postId = $(this).attr("post-id");
		prepareModifyPost(postId);
		return false;
	});
	
	$("div.zfa > a").click(function(){
		var postId = $(this).attr("post-id");
		prepareRepost(postId);
		return false;
	});
	
	if($("form#comment-form").length > 0){
		var commentWidget = new CommentWidget($("form#comment-form"), $("div.comment-list"));
		commentWidget.bindReply();
		commentWidget.bindAllReplyLink();
		commentWidget.bindAllDelLink();
	}
	
});