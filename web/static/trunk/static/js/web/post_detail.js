$(document).ready(function(){
	$("div.post-response > a.xy").click(function(){
		var postId = $(this).attr("post-id");
		var obj = $(this);
		responsePost(this, postId, function(){
			var currentCnt = parseInt(obj.next().find("font").text());
			obj.next().find("font").text(currentCnt + 1);
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
			deletePost(postId, function(){window.location.href = "/home/posts";});
		});
	});
	
	$("div.own_btn > a.edit").click(function(){
		var postId = $(this).attr("post-id");
		prepareModifyPost(postId);
	});
	
	$("div.zfa > a").click(function(){
		var postId = $(this).attr("post-id");
		prepareRepost(postId);
	});
});