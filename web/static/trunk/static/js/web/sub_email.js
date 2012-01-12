$(document).ready(function(){
	$("#updateEmail").submit(function(){
		var email = $("#subEmail").val();
		if(!email || email == ""){
			$("#emailError").text("请输入邮箱地址").show();
			return false;
		}
		if(!checkEmail(email)){
			$("#emailError").text("请输入正确的邮箱地址").show();
			return false;
		}
		return true;
	});
	
	$("#updateEmailBtn").bind("click", function(){
		$("#updateEmail").submit();
	});
});