$(document).ready(function(){
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
	
	$("span > a.send-message").bind("click", function(){
		var uid = $(this).attr("target-uid");
		var nickname = $(this).attr("target-nickname");
		openMessage(uid, nickname);
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