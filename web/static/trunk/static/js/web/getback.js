$(document).ready(function(){
	
	function validateAccount(){
		var value = trimStr($("#form-account").find("input").val());
		if(!checkValLength(value, 6, 100) || !checkEmail(value)){
			$("#form-account").removeClass("right").addClass("wrong").find("em").text("请输入正确格式的邮箱");
			return false;
		}
		$("#form-account").removeClass("wrong").addClass("right").find("em").text("");
		return true;
	}
	
	$("#form-account").find("input").blur(function(){
		//验证邮箱
		validateAccount();
	});
	
	$("#getback-form").submit(function(){
		if(!validateAccount()){
			$("#form-account").find("input").focus();
			return false;
		}
		return true;
	});
	
	$("#getback-form").find("div.btn > a").bind("click", function(){
		$("#getback-form").submit();
		return false;
	});
});