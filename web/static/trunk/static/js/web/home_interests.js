$(document).ready(function() {
	$("div.item > div.close > a").bind("click", function() {
		var uid = $(this).attr("uid");
		var obj = $(this);
		var content = $("#dialog-remove-interest").html();
		showConfirm(this, "removeInterest", content, function(){
			removeInterest(uid, function(){
				$(obj).parent().parent().remove();
			});
		});
	});
	//添加约
	$("div.btn > a.yueta").bind("click", function(){
		var uid = $(this).attr("uid");
		openDating(this, uid, 0);
	});
});

function submitDating(uid){
	date(function(){
		$("#dating"+uid).hide();
		$("#removeDating"+uid).show();
	});
}