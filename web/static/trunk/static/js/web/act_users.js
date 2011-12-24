$(document).ready(function() {
	//添加项目
	$("div.link_t > a").bind("click", function() {
		var uid = $(this).attr("uid");
		var obj = $(this);
		interest(this, uid, function(){
			$(obj).hide();
			$("#hasInterest" + uid).show();
		});
	});
	
	//添加约
	$("div.date_t > a").bind("click", function(){
		var uid = $(this).attr("uid");
		openDating(uid, 0);
	});
	
	$("div.title > span > select").bind("change", function(){
		var actId = $(this).attr("actid");
		var genderType = $(this).children('option:selected').val();
		window.location.href="/act/" + actId + "/users/" + genderType + "/1";
	});
});

function submitDating(uid){
	date(function(){
		$("#date"+uid).hide();
		$("#hasDate"+uid).removeAttr("style");
	});
}