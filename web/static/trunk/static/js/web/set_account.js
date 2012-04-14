$(document).ready(function(){
	
	function validateAccount(){
		var value = trimStr($("#form-account").find("input").val());
		if(!checkValLength(value, 6, 100) || !checkEmail(value)){
			$("#form-account").removeClass("right").addClass("wrong").find("em").text("请请输入正确格式的邮箱");
			return false;
		}
		return true;
	}
	
	function validatePwd(){
		var value = $("#form-pwd").find("input").val();
		if(value == ""){
			$("#form-pwd").removeClass("right").addClass("wrong").find("em").text("请输入密码");
			return false;
		}else if(!checkValLength(value, 6, 32)){
			$("#form-pwd").removeClass("right").addClass("wrong").find("em").text("密码控制在6~32位之间");
			return false;
		}else{
			$("#form-pwd").removeClass("wrong").addClass("right").find("em").text("");
			return true;	
		}
	}
	
	function validateConfirmPwd(){
		var pwd = $("#form-pwd").find("input").val();
		var confirmPwd = $("#form-confirm-pwd").find("input").val();
		if(confirmPwd == ""){
			$("#form-confirm-pwd").removeClass("right").addClass("wrong").find("em").text("请输入确认密码");
			return false;
		}
		if(pwd != confirmPwd){
			$("#form-confirm-pwd").removeClass("right").addClass("wrong").find("em").text("两次输入的密码不相符");
			return false;
		}else {
			$("#form-confirm-pwd").removeClass("wrong").addClass("right").find("em").text("");
			return true;	
		}
	}
	
	$("#form-account").find("input").blur(function(){
		//验证邮箱
		if(validateAccount()){
			//ajax
			jQuery.ajax({
				url: "/passport/validate/accountexist",
				type: "post",
				data: {"account" : trimStr($(this).val())},
				dataType: "json",
				success: function(result){
					if(result.result){
						$("#form-account").removeClass("right").addClass("wrong").find("em").text(result.errorInfo);
					}else{
						$("#form-account").removeClass("wrong").addClass("right").find("em").text("");
					}
				}
			});
		}
	});
	
	$("#form-pwd").find("input").blur(function(){
		//验证密码
		if(validatePwd()){
			validateConfirmPwd();
		}
	});
	$("#form-confirm-pwd").find("input").blur(function(){
		//验证密码确认
		if(validatePwd()){
			validateConfirmPwd();
		}
	});
	
	$("#register-form").submit(function(){
		if(!validateAccount()){
			$("#form-account").find("input").focus();
			return false;
		}
		if(!validatePwd()){
			$("#form-pwd").find("input").focus();
			return false;
		}
		if(!validateConfirmPwd()){
			$("#form-confirm-pwd").find("input").focus();
			return false;
		}
		return true;
	});
	
	$("#register-form").find("div.btn > a").bind("click", function(){
		$("#register-form").submit();
		return false;
	});
});