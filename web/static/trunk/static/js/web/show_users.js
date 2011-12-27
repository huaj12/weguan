$(document).ready(function() {
	$("#genderSelect > span > select").bind("change", function(){
		var genderType = $(this).children('option:selected').val();
		window.location.href="/showUsers/" + genderType + "/1";
	});
	
	//添加约
	$("div.btn > a.yueta").bind("click", function(){
		var uid = $(this).attr("uid");
		openDating(uid, 0);
	});
	
	$("div.btn > div.ygxq > a.delete").bind("click", function() {
		var uid = $(this).attr("uid");
		var obj = $(this);
		var content = $("#dialog-remove-interest").html();
		showConfirm(this, "removeInterest", content, function(){
			removeInterest(uid, function(){
				$(obj).parent().hide();
				$("#interest" + uid).show();
			});
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
});

function submitDating(uid){
	date(function(){
		$("#dating"+uid).hide();
		$("#removeDating"+uid).show();
	});
}