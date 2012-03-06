$(document).ready(function() {
	//敲门
	$("a.qm_sbtn").bind("click", function() {
		var uid = $(this).attr("uid");
		var obj = $(this);
		interest(this, uid, function(){
			$(obj).hide();
			$("#hasInterest" + uid).show();
		});
		return false;
	});
	//取消敲门
	$("div.cancel_qm > a.delete").bind("click", function() {
		var uid = $(this).attr("uid");
		var obj = $(this);
		removeInterestConfirm(uid, this, function(){
			$(obj).parent().hide();
			$("#interest" + uid).show();
		});
		return false;
	});
	
	//添加约
	$("a.yueta_sbtn").bind("click", function(){
		var uid = $(this).attr("uid");
		openDating(uid, 0);
		return false;
	});
	
	$("div.title > span > select#genderSelect").bind("change", function(){
		var actId = $(this).attr("actid");
		var cityId = $(this).attr("cityid");
		var genderType = $(this).children('option:selected').val();
		window.location.href="/act/" + actId + "/users_" + genderType + "_" + cityId + "/1";
	});
	
	$("div.title > span > select#citySelect").bind("change", function(){
		var actId = $(this).attr("actid");
		var genderType = $(this).attr("gendertype");
		var cityId = $(this).children('option:selected').val();
		window.location.href="/act/" + actId + "/users_" + genderType + "_" + cityId + "/1";
	});
	
	$("a.message_sbtn").bind("click", function(){
		var uid = $(this).attr("target-uid");
		var nickname = $(this).attr("target-nickname");
		openMessage(uid, nickname);
		return false;
	});
});

function submitDating(uid){
	date(function(){
		$("#date"+uid).hide();
		$("#hasDate"+uid).removeAttr("style");
	});
}