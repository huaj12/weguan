$(document).ready(function(){
	moreSeach();
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
	$("span > a.message_u1").bind("click", function(){
		var uid = $(this).attr("target-uid");
		var nickname = $(this).attr("target-nickname");
		openMessage(uid, nickname);
		return false;
	});
	$("div.more_search > a").click(function(){
		if($(this).text()=="更多搜索"){
			$(this).text("简单搜索");
			$("div.search_more_area").show();
			$("#more").html('<a href=\'javascript:void(0);\' class=\'query-btn\'>搜索</a>');
			$("#simple").html('');
		}else{
			$("#simple").html('<a href=\'javascript:void(0);\' class=\'query-btn\'>搜索</a>');
			$("#more").html('');
			$(this).text("更多搜索");
			$("div.search_more_area").hide();
			var selectBox = new SelectBoxInput($("div.xz_menu")[0]);
			selectBox.resetSelect();
			var selectEducations = new SelectInput($("div[name='educations']")[0]);
			selectEducations.resetSelect();
			var selectMonthlyIncome = new SelectInput($("div[name='monthlyIncome']")[0]);
			selectMonthlyIncome.resetSelect();
			$("input[name='minStringHeight']").val("");
			$("input[name='maxStringHeight']").val("");
		}
		$("a.query-btn").click(function(){
			search_user();
			return false;
		});
	});
	$("div.s_input > a").click(function(){
		$("#findpostsForm").submit();
		return false;
	});
	
	$("div.tags > a").click(function(){
		window.location.href="/findposts?queryString="+$(this).text();
		return false;
	});
	
	
	
});
function moreSeach(){
	var constellationId=$("input[name='constellation']").val();
	var educations=$("input[name='educations']").val();
	var monthlyIncome=$("input[name='monthlyIncome']").val();
	var minStringHeight=$("input[name='minStringHeight']").val();
	var maxStringHeight=$("input[name='maxStringHeight']").val();
	if(constellationId==""&&educations==0&&monthlyIncome=="0-0"&&minStringHeight==""&&maxStringHeight==""){
		$("div.more_search > a").text("更多搜索");
		$("#simple").html('<a href=\'javascript:void(0);\' class=\'query-btn\'>搜索</a>');
		$("div.search_more_area").hide();
	}else{
		$("#more").html('<a href=\'javascript:void(0);\' class=\'query-btn\'>搜索</a>');
		$("div.more_search > a").text("简单搜索");
		$("div.search_more_area").show();
	}
	return false;
}
function parseMonthlyIncome(str){
	return str.split("-");
}
function search_user(){
	var townId = $("input[name='town']").val();
	var sex = $("input[name='sex']").val();
	var minStringAge = $("input[name='minStringAge']").val();
	var maxStringAge = $("input[name='maxStringAge']").val();
	var constellationId=$("input[name='constellation']").val();
	var educations=$("input[name='educations']").val();
	var monthlyIncome=$("input[name='monthlyIncome']").val();
	var arr=parseMonthlyIncome(monthlyIncome);
	var minStringHeight=$("input[name='minStringHeight']").val();
	var maxStringHeight=$("input[name='maxStringHeight']").val();
	window.location.href = "/seachusers/" + townId + "_" + sex + "_" + minStringAge + "_" + maxStringAge + "_"+minStringHeight+"_"+maxStringHeight+"_"+constellationId+"_"+educations+"_"+arr[0]+"_"+arr[1]+"/1";
}