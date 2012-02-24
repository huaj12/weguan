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
		//检查所在地
		if($("#province-select").val() <= 0 || $("#city-select").val() <= 0 || ($("#town-select").is(":visible") && $("#town-select").val() < 0)){
			$("#location-error").text("请选择所在地!").show();
			return false;
		}else{
			$("#location-error").hide();
		}
		//检查生日
		if($("#birthYear").val() == 0 || $("#birthMonth").val() == 0 || $("#birthDay").val() == 0){
			$("#birth-error").text("请选择生日!").show();
			return false;
		}else{
			$("#birth-error").hide();
		}
		//检查职业
		var professionId = $("#professionId").val();
		var profession = $("#profession-input").find("input").val();
		if(professionId < 0){
			$("#profession-error").text("请选择职业!").show();
			return false;
		}
		if(professionId == 0 && (profession == ""|| profession == "10个字以内描述")){
			$("#profession-error").html("请输入职业!").show();
			return false;
		}
		if(professionId == 0 && !checkValLength(profession, 1, 20)){
			$("#profession-error").html("不要超过10个字哦!").show();
			return false;
		}
		return true;
	});
	
	$("div.save_btn > a").bind("click", function(){
		$("#guide-form").submit();
		return false;
	});
});