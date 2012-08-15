$(document).ready(function(){
	new LocationWidget();
	
	$("select[name='professionId']").change(function(){
		if($(this).val() == 0){
			$("#profession-input").show();
		}else{
			$("#profession-input").hide();
		}
	});
	
	$("#guide-form").submit(function(){
		//检查性别
		if($('input:radio[name="gender"]:checked').val()==""||$('input:radio[name="gender"]:checked').val()==null){
			$("#gender-error").show().find("b").text("请选择性别!");
			return false;
		}else{
			$("#gender-error").hide();
		}
		//检查所在地
		if($("#province-select").val() <= 0 || $("#city-select").val() <= 0 || ($("#town-select").is(":visible") && $("#town-select").val() < 0)){
			$("#location-error").show().find("b").text("请选择所在地!");
			return false;
		}else{
			$("#location-error").hide();
		}
		//检查生日
		if($("#birthYear").val() == 0 || $("#birthMonth").val() == 0 || $("#birthDay").val() == 0){
			$("#birth-error").show().find("b").text("请选择生日!");
			return false;
		}else{
			$("#birth-error").hide();
		}
		//检查职业
		var professionId = $("#professionId").val();
		var profession = $("#profession-input").find("input").val();
		if(professionId < 0){
			$("#profession-error").show().find("b").text("请选择职业!");
			return false;
		}
		if(professionId == 0 && (profession == ""|| profession == "10个字以内描述")){
			$("#profession-error").show().find("b").text("请输入职业!");
			return false;
		}
		if(professionId == 0 && !checkValLength(profession, 1, 20)){
			$("#profession-error").show().find("b").text("不要超过10个字哦!");
			return false;
		}
		return true;
	});
	
	$("div.save_btn > a").bind("click", function(){
		$("#guide-form").submit();
		return false;
	});
	
	var windowCnt = $("#window-box > ul").attr("window-count");
	var windowBox = $("#window-box");
	$("div.arrow_left > a").bind("click", function(){
		if(!windowBox.is(':animated')){
			var cLeft = windowBox.position().left;
			if(5 > cLeft){
				windowBox.animate({
					left: '+=150'
				}, 500);
			}
		}
		return false;
	});
	
	$("div.arrow_right > a").bind("click", function(){
		if(!windowBox.is(':animated')){
			var cLeft = windowBox.position().left;
			if(((windowCnt-5) * -150 + 5) < cLeft){
				windowBox.animate({
					left: '-=150'
				}, 500);
			}
		}
		return false;
	});
	
	$("a.go-login").click(function(){
		$("div.welcome").remove();
		$("div.qrxx").show();
		return false;
	});
});