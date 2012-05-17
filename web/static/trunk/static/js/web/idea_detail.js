$(document).ready(function(){
	$("#city-select").each(function(){
		var citySelect = new CitySelectInput(this, function(cityId){
			var gender = $("input[name='genderType']").val();
			var ideaId = $("div.wgo_title").attr("idea-id");
			window.location.href = "/idea/" + ideaId + "/user/" + cityId + "_" + gender + "/1";
			return false;
		});
		citySelect.bindBlur();
		citySelect.bindClick();
    });
	
	$("#gender-select").find("span > a").bind("click", function(){
		var cityId = $("input[name='cityId']").val();
		var gender = $(this).attr("value");
		var ideaId = $("div.wgo_title").attr("idea-id");
		window.location.href = "/idea/" + ideaId + "/user/" + cityId + "_" + gender + "/1";
		return false;
	});
	
	$("div.date > a").click(function(){
		var sendBtn = this;
		$(sendBtn).unbind("click").attr("class", "sending").text("发送中");
		var targetUid = $(this).attr("target-uid");
		var ideaId = $(this).attr("idea-id");
		sendDate(targetUid, null, ideaId, this, function(){
			$(sendBtn).attr("class", "date_done").text("已约");
		});
		return false;
	});
	$("a.send-message").bind("click", function(){
		var uid = $(this).attr("target-uid");
		var nickname = $(this).attr("target-nickname");
		openMessage(uid, nickname);
		return false;
	});
	$("div.user-remove-interest > a.done").bind("click", function() {
		var uid = $(this).attr("uid");
		removeInterestConfirm(uid, this, function(){
			$("div.remove-interest-" + uid).hide();
			$("div.interest-" + uid).attr("style", "");
		});
		return false;
	});
	$("div.user-add-interest > a").bind("click", function() {
		var uid = $(this).attr("uid");
		interest(this, uid, function(){
			$("div.interest-" + uid).hide();
			$("div.remove-interest-" + uid).attr("style", "");
		});
		return false;
	});
	
	$("div.idea_js > a").click(function(){
		$("div.idea_js").removeClass("h200");
		$(this).hide();
	});
});