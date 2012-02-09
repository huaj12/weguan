$(document).ready(function(){
	$("a.query-btn").click(function(){
		search_user();
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
	$("span > a.message_u1").bind("click", function(){
		var uid = $(this).attr("target-uid");
		var nickname = $(this).attr("target-nickname");
		openMessage(uid, nickname);
	});
});

function search_user(){
	var cityId = $("input[name='city']").val();
	var sex = $("input[name='sex']").val();
	var minStringAge = $("input[name='minStringAge']").val();
	var maxStringAge = $("input[name='maxStringAge']").val();
	//TODO 验证年龄
//	if(minStringAge == ""){
//		minStringAge = 0;
//	}
//	if(maxStringAge == ""){
//		maxStringAge = 0;
//	}
	window.location.href = "/queryUser/" + cityId + "_" + sex + "_" + minStringAge + "_" + maxStringAge + "/1";
}