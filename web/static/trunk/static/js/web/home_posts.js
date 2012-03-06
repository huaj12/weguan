$(document).ready(function() {
	$("div.ta_btn > a").click(function(){
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
	$("div.con_btn > a.delete").click(function(){
		var postId = $(this).attr("post-id");
		var content = $("#dialog-confirm").html().replace("{0}", "确定删除此条拒宅么？");
		showConfirm(this, "removePost", content, function(){
			deletePost(postId, function(){window.location.href = "/home/posts";});
		});
		return false;
	});
	
	$("div.con_btn > a.edit").click(function(){
		var postId = $(this).attr("post-id");
		prepareModifyPost(postId);
		return false;
	});
	$("div.zfa > a").click(function(){
		var postId = $(this).attr("post-id");
		prepareRepost(postId);
		return false;
	});
	$("div.post-response > a.xy").click(function(){
		var postId = $(this).attr("post-id");
		var obj = $(this);
		responsePost(this, postId, function(){
			var currentCnt = parseInt(obj.next().find("font").text());
			obj.next().find("font").text(currentCnt + 1);
			obj.text("已" + obj.text()).unbind("click").parent().addClass("done");
		});
		return false;
	});
	$("a#inviteEditProfile").click(function(){
		var uid = $(this).attr("uid");
		var obj = $(this);
		$.ajax({
			url : "/home/inviteEditProfile",
			type : "post",
			cache : false,
			data : {"uid" : uid},
			dataType : "json",
			success : function(result) {
				if(result&&result.success){
					obj.unbind("click").attr("class", "done").text("已邀请");
				}else{
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
	
	$("a#inviteUploadLogo").click(function(){
		var uid = $(this).attr("uid");
		var obj = $(this);
		$.ajax({
			url : "/home/inviteUploadLogo",
			type : "post",
			cache : false,
			data : {"uid" : uid},
			dataType : "json",
			success : function(result) {
				if(result&&result.success){
					obj.unbind("click").attr("class", "done").text("已邀请");
				}else{
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