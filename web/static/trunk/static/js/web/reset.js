$(document).ready(function(){
	function validatePwd(){
		var value = $("#form-pwd").find("input").val();
		if(value == ""){
			$("#form-pwd").removeClass("right").addClass("wrong").find("em").text("请输入密码");
			return false;
		}else if(!checkValLength(value, 6, 32)){
			$("#form-pwd").removeClass("right").addClass("wrong").find("em").text("密码控制在6-32个字符");
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
			$("#form-confirm-pwd").removeClass("right").addClass("wrong").find("em").text("两次密码输入不一致");
			return false;
		}else {
			$("#form-confirm-pwd").removeClass("wrong").addClass("right").find("em").text("");
			return true;	
		}
	}
	
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
	
	$("#reset-form").submit(function(){
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
	
	$("#reset-form").find("div.btn > a").bind("click", function(){
		$("#reset-form").submit();
		return false;
	});
});