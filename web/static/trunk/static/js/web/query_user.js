$(document).ready(function(){
	$("div.date > a").click(function(){
		var uid = $(this).attr("target-uid");
		var nickname = $(this).attr("target-nickname");
		openDate(uid, nickname);
		return false;
	});
	$("#city-select").each(function(){
		var citySelect = new CitySelectInput(this, function(cityId){return false;});
		citySelect.bindBlur();
		citySelect.bindClick();
    });
	
	$("a.query-btn").click(function(){
		search_user();
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
	
	$("span > a.send-message").bind("click", function(){
		var uid = $(this).attr("target-uid");
		openMessage(uid);
		return false;
	});
	
	var minAge = $("input[name='minAge']").attr("init-data");
	if(minAge != "0"){
		$("input[name='minAge']").val(minAge);
	}
	var maxAge = $("input[name='maxAge']").attr("init-data");
	if(maxAge != "0"){
		$("input[name='maxAge']").val(maxAge);
	}
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

function search_user(){
	var townId = $("input[name='townId']").val();
	var sex = $("input[name='sex']").val();
	var minAge = $("input[name='minAge']").val();
	var maxAge = $("input[name='maxAge']").val();
	window.location.href = "/queryusers/" + townId + "_" + sex + "_" + (minAge == "" ? 0 : minAge) + "_" + (maxAge == "" ? 0 : maxAge) + "/1";
}