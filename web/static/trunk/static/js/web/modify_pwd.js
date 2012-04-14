$(document).ready(function(){
	
	function validateOldPwd(){
		var value = $("#form-old-pwd").find("input").val();
		if(value == ""){
			$("#form-old-pwd").removeClass("right").addClass("wrong").find("em").text("请输入旧密码");
			return false;
		}else{
			$("#form-old-pwd").removeClass("wrong").addClass("right").find("em").text("");
			return true;	
		}
	}
	
	function validatePwd(){
		var value = $("#form-new-pwd").find("input").val();
		if(value == ""){
			$("#form-new-pwd").removeClass("right").addClass("wrong").find("em").text("请输入新密码");
			return false;
		}else if(!checkValLength(value, 6, 32)){
			$("#form-new-pwd").removeClass("right").addClass("wrong").find("em").text("密码控制在6-32个字符");
			return false;
		}else{
			$("#form-new-pwd").removeClass("wrong").addClass("right").find("em").text("");
			return true;	
		}
	}
	
	function validateConfirmPwd(){
		var pwd = $("#form-new-pwd").find("input").val();
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
	
	$("#form-old-pwd").find("input").blur(function(){
		//验证密码
		validateOldPwd();
	});
	$("#form-new-pwd").find("input").blur(function(){
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
	
	$("#modify-pwd-form").find("div.btn > a").bind("click", function(){
		if(!validateOldPwd()){
			$("#form-old-pwd").find("input").focus();
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
		jQuery.ajax({
			url: "/passport/modifypwd",
			type: "post",
			data: $("#modify-pwd-form").serialize(),
			dataType: "json",
			success: function(result){
				if(result && result.success){
					$("#modify-pwd-form")[0].reset();
					$("div.big_input").each(function(){
						$(this).removeClass("right");
					});
					var content = $("#dialog-success").html().replace("{0}", "修改成功！");
					showSuccess(null, content);
				}else{
					var errorCode = result.errorCode;
					switch (errorCode) {
						case "00002":
							alert(result.errorInfo);
							break;
						case "10006":
							$("#form-old-pwd").removeClass("right").addClass("wrong").find("em").text(result.errorInfo);
							break;
						case "10002":
							$("#form-new-pwd").removeClass("right").addClass("wrong").find("em").text(result.errorInfo);
							break;
						case "10003":
							$("#form-confirm-pwd").removeClass("right").addClass("wrong").find("em").text(result.errorInfo);
							break;
					}
				}
			},
			statusCode : {
				401 : function() {
					window.location.href = "/login?turnTo=" + window.location.href;
				}
			}
		});
		return false;
	});
});