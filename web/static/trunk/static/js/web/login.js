$(document).ready(function(){
	
	function validateAccount(){
		var value = trimStr($("#form-account").find("input").val());
		if(!checkValLength(value, 6, 100) || !checkEmail(value)){
			$("div.login_error").text("请输入正确的邮箱地址").show();
			return false;
		}
		return true;
	}
	
	function validatePwd(){
		var value = $("#form-pwd").find("input").val();
		if(value == ""){
			$("div.login_error").text("请输入密码").show();
			return false;
		}
		return true;
	}
	
	$("#login-form").submit(function(){
		if(!validateAccount()){
			$("#form-account").find("input").focus();
			return false;
		}
		if(!validatePwd()){
			$("#form-pwd").find("input").focus();
			return false;
		}
		return true;
	});
	
	$("#login-form").find("div.btn > a").bind("click", function(){
		$("#login-form").submit();
		return false;
	});
});