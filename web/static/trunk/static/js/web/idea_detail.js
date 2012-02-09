$(document).ready(function(){
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
	$("div.fb_area > div.fb_btn > a").click(function(){
		var ideaId = $(this).attr("idea-id");
		var send = $(this).parent().hide();
		var sending = send.prev().show();
		postIdea(ideaId, send, sending, function(){
			sending.attr("class", "send_done").children("a").text("已发布");
		});
	});
});