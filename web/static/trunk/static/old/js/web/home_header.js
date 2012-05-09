$(document).ready(function() {
	//兴趣的人
	$("div.ta_user_btn > a.like").bind("click", function() {
		var uid = $(this).attr("uid");
		var obj = $(this);
		interest(this, uid, function(){
			$(obj).hide();
			$("div.ta_user_btn > div.cancel_like").show();
		});
		return false;
	});
	$("div.ta_user_btn > div.cancel_like > a.delete").bind("click", function() {
		var uid = $(this).attr("uid");
		var obj = $(this);
		removeInterestConfirm(uid, this, function(){
			$(obj).parent().hide();
			$("div.ta_user_btn > a.like").show();
		});
		return false;
	});
	
	//删除约
	$("div.dated > a.removeDating").bind("click", function() {
		var datingId = $("div.dated").attr("datingid");
		removeDatingConfirm(datingId, this, function(){
			$("div.dated").hide();
			$("a.date").show();
		});
		return false;
	});
	//添加约
	$("a.date").bind("click", function(){
		var uid = $(this).attr("uid");
		openDating(uid, 0);
		return false;
	});
	//修改约
	$("div.dated > a.modifyDating").bind("click", function(){
		var uid = $("div.dated").attr("uid");
		var datingId = $("div.dated").attr("datingid");
		openDating(uid, datingId);
		return false;
	});
	
	$("div.kongxian > a").bind("click", function(){
		var obj = $("div#freeDateForm");
		if(!obj.is(":visible")){
			obj.show().animate({bottom:"+=100"}, 1000);
		}
		return false;
	});
	
	$("a.open-dialog").bind("click", function(){
		var uid = $(this).attr("target-uid");
		var nickname = $(this).attr("target-nickname");
		openMessage(uid, nickname);
		return false;
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

function updateFreeDateShow(){
	var showString = "";
	var todayOfWeek = $("div.week_day > a.today").attr("dayoftheweek");
	$("div.week_day > a").each(function(){
		if($(this).hasClass("active")){
			var dayOfWeek = $(this).attr("dayoftheweek");
			if(showString != ""){
				showString += ",";
			}
			if($(this).hasClass("today")){
				showString += "今天";
			}else if (dayOfWeek == todayOfWeek + 1
					|| (dayOfWeek == 1 && todayOfWeek == 7)) {
				showString += "明天";
			} else if (dayOfWeek > todayOfWeek || dayOfWeek == 1) {
				showString += "本" + $(this).text();
			} else {
				showString += "下" + $(this).text();
			}
		}
	});
	if(showString == ""){
		showString = "还未标注空闲时间";
	}else{
		showString += " 有空";
	}
	$("div.kongxian > font").text(showString);
}