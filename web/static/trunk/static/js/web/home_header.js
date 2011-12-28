$(document).ready(function() {
	//兴趣的人
	$("div.ta_user_btn > a.like").bind("click", function() {
		var uid = $(this).attr("uid");
		var obj = $(this);
		interest(this, uid, function(){
			$(obj).hide();
			$("div.ta_user_btn > div.cancel_like").show();
		});
	});
	$("div.ta_user_btn > div.cancel_like > a.delete").bind("click", function() {
		var uid = $(this).attr("uid");
		var obj = $(this);
		var content = $("#dialog-remove-interest").html();
		showConfirm(this, "removeInterest", content, function(){
			removeInterest(uid, function(){
				$(obj).parent().hide();
				$("div.ta_user_btn > a.like").show();
			});
		});
	});
	
	//删除约
	$("div.dated > a.removeDating").bind("click", function() {
		var datingId = $("div.dated").attr("datingid");
		var content = $("#dialog-remove-dating").html();
		showConfirm(this, "removeDating", content, function() {
			removeDating(datingId, function() {
				$("div.dated").hide();
				$("a.date").show();
			});
		});
	});
	//添加约
	$("a.date").bind("click", function(){
		var uid = $(this).attr("uid");
		openDating(uid, 0);
	});
	//修改约
	$("div.dated > a.modifyDating").bind("click", function(){
		var uid = $("div.dated").attr("uid");
		var datingId = $("div.dated").attr("datingid");
		openDating(uid, datingId);
	});
});

function submitDating(uid){
	date(function(result){
		$("a.date").hide();
		$("div.dated").attr("datingid", result.datingId);
		$("div.dated > div.datefor > p > font._act").text(result.actName);
		$("div.dated > div.datefor > p > font._contact").text("");
		$("div.dated > a.modifyDating").show();
		$("div.dated").show();
	});
}