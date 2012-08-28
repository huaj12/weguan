$(document).ready(function() {
	$("a.send-message").click(function(){
		var uid = $(this).attr("target-uid");
		var nickname = $(this).attr("target-nickname");
		openMessage(uid, nickname);
		return false;
	});
	$("div.ta_btn > a").click(function(){
		var uid = $(this).attr("target-uid");
		var nickname = $(this).attr("target-nickname");
		openDate(uid, nickname);
		return false;
	});
	$("div.user-remove-interest > a.done").bind("click", function() {
		var uid = $(this).attr("uid");
		removeInterestConfirm(uid, this, function(){
			removeInterestCallback(uid);
		});
		return false;
	});
	
	$("div.user-add-interest > a").bind("click", function() {
		var uid = $(this).attr("uid");
		interest(this, uid, function(){
			interestCallback(uid);
		});
		return false;
	});
	
	$("a.user-remove-interest").bind("click", function() {
		var uid = $(this).attr("uid");
		removeInterestConfirm(uid, this, function(){
			removeInterestCallback(uid);
		});
		return false;
	});
	
	$("a.user-add-interest").bind("click", function() {
		var uid = $(this).attr("uid");
		interest(this, uid, function(){
			interestCallback(uid);
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
	
	$("div.my_head_area > div.jb > a#report-profile").bind("click", function(){
		var targetUid = $(this).attr("target-uid");
		report(targetUid,'3','',targetUid);
		return false;
	});
	
	$("div.my_head_area > div.pb > a.pinbi").bind("click", function(){
		bindShieldUid($(this));
		return false;
	});
});
function removeInterestCallback(uid){
	$("div.remove-interest-" + uid).hide();
	$("div.interest-" + uid).attr("style", "");
	$("a.remove-interest-" + uid).hide();
	$("a.interest-" + uid).attr("style", "");
}

function interestCallback(uid){
	$("div.interest-" + uid).hide();
	$("div.remove-interest-" + uid).attr("style", "");
	$("a.interest-" + uid).hide();
	$("a.remove-interest-" + uid).attr("style", "");
}