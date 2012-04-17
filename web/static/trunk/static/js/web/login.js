$(document).ready(function(){
	
	function validateAccount(){
		var value = trimStr($("#form-account").find("input").val());
		if(!checkValLength(value, 6, 100) || !checkEmail(value)){
			$("div.login_error").text("请输入正确格式的邮箱").show();
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
	
	function validateVerifyCode(){
		var value = $("#from-verify-code").find("input").val();
		if(null != value && value == ""){
			$("#from-verify-code").removeClass("right").addClass("wrong").find("em").text("请输入验证码");
			return false;
		}
		return true;
	}
	
	$("div.hyz > a").click(function(){
		var imageUrl = $("div.yzm > img").attr("url") + new Date().getTime();
		$("div.yzm > img").attr("src", imageUrl);
		return false;
	});
	
	$("#login-form").submit(function(){
		if(!validateAccount()){
			$("#form-account").find("input").focus();
			return false;
		}
		if(!validatePwd()){
			$("#form-pwd").find("input").focus();
			return false;
		}
		if(!validateVerifyCode()){
			$("#from-verify-code").find("input").focus();
			return false;
		}
		return true;
	});
	
	$("#login-form").find("div.btn > a").bind("click", function(){
		$("#login-form").submit();
		return false;
	});
});