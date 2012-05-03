
$(document).ready(function(){
	var selectBox = new SelectBoxInput($("div.xz_menu")[0]);
	selectBox.bindBlur();
	selectBox.bindClick();
	selectBox.bindSelect();
	var selectEducations = new SelectInput($("div[name='educations']")[0]);
	selectEducations.bindBlur();
	selectEducations.bindClick();
	selectEducations.bindSelect();
	var selectMonthlyIncome = new SelectInput($("div[name='monthlyIncome']")[0]);
	selectMonthlyIncome.bindBlur();
	selectMonthlyIncome.bindClick();
	selectMonthlyIncome.bindSelect();
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
			$("#more").show();
			$("#simple").hide();
		}else{
			$("#simple").show();
			$("#more").hide();
			$(this).text("更多搜索");
			$("div.search_more_area").hide();
			selectBox.resetSelect();
			selectEducations.resetSelect();
			selectMonthlyIncome.resetSelect();
			$("input[name='minStringHeight']").val("");
			$("input[name='maxStringHeight']").val("");
		}
	});
	$("div.s_input > a").click(function(){
		var queryString = $("input[name='queryString']").val();
		window.location.href = "/searchposts/"+queryString+"_all/1";
		return false;
	});
});
function moreSeach(){
	var constellationId=$("input[name='constellation']").val();
	var educations=$("input[name='educations']").val();
	var monthlyIncome=$("input[name='monthlyIncome']").val();
	var minStringHeight=$("input[name='minStringHeight']").val();
	var maxStringHeight=$("input[name='maxStringHeight']").val();
	if(constellationId=="0"&&educations==0&&monthlyIncome=="0-0"&&minStringHeight==""&&maxStringHeight==""){
		$("div.more_search > a").text("更多搜索");
		$("#simple").show();
		$("#more").hide();
	}else{
		$("#more").show();
		$("#simple").hide();
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
	var minStringAge = trimStr($("input[name='minStringAge']").val());
	var maxStringAge = trimStr($("input[name='maxStringAge']").val());
	var constellationId=$("input[name='constellation']").val();
	var educations=$("input[name='educations']").val();
	var monthlyIncome=$("input[name='monthlyIncome']").val();
	var arr=parseMonthlyIncome(monthlyIncome);
	var minStringHeight=trimStr($("input[name='minStringHeight']").val());
	var maxStringHeight=trimStr($("input[name='maxStringHeight']").val());
	if(minStringHeight==""){
		minStringHeight="0";
	}
	if(maxStringHeight==""){
		maxStringHeight="0";
	}
	if(minStringAge==""){
		minStringAge="0";
	}
	if(maxStringAge==""){
		maxStringAge="0";
	}
	if(!isNum(minStringAge)||minStringAge<0||!isNum(maxStringAge)||maxStringAge<0){
		alert('请输入正确的年龄');
		return ;
	}

	if(!isNum(minStringHeight)||minStringHeight<0||!isNum(maxStringHeight)||maxStringHeight<0){
		alert('请输入正确的身高');
		return ;
	}
	window.location.href = "/searchusers/" + townId + "_" + sex + "_" + minStringAge + "_" + maxStringAge + "_"+minStringHeight+"_"+maxStringHeight+"_"+constellationId+"_"+educations+"_"+arr[0]+"_"+arr[1]+"/1";
}