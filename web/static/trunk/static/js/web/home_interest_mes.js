$(document).ready(function() {
	$("div.btn > div.ygxq > a.delete").bind("click", function() {
		var uid = $(this).attr("uid");
		var obj = $(this);
		removeInterestConfirm(uid, this, function(){
			$(obj).parent().hide();
			$("#interest" + uid).show();
		});
	});
	$("div.btn > a.like").bind("click", function() {
		var uid = $(this).attr("uid");
		var obj = $(this);
		interest(this, uid, function(){
			$(obj).hide();
			$("#removeInterest" + uid).show();
		});
	});
	//添加约
	$("div.btn > a.yueta").bind("click", function(){
		var uid = $(this).attr("uid");
		openDating(uid, 0);
	});
	
	$("div.item > div.btn > a.mail").bind("click", function(){
		var uid = $(this).attr("target-uid");
		var nickname = $(this).attr("target-nickname");
		openMessage(uid, nickname);
	});
});

function submitDating(uid){
	date(function(){
		$("#dating"+uid).hide();
		$("#removeDating"+uid).show();
	});
}