function selectCity(obj) {
	$.get('/base/selectCity', {
		proId : obj.value,
		random : Math.random()
	}, function(result) {
		$("#citys").html(result);
		selectTown($("#c_id").val());
	});
}
function selectTown(id) {
	$.get('/base/selectTown', {
		cityId : id,
		random : Math.random()
	}, function(result) {
		$("#towns").show();
		$("#towns").html(result);
	});
}
function profession(obj){
	if(obj.value==0){
		$("#profession_input").show();
		$("#profession_tip").html("");
	}else{
		$("#profession_input").hide();
	}
}
function show_updateDiv(str){
	$("#user_"+str).hide();
	$("#input_"+str).show();
	$("#ts_"+str).show();
}
function cancel_updateDiv(str){
	$("#user_"+str).show();
	$("#input_"+str).hide();
	$("#ts_"+str).hide();
	$("#error_"+str).html("");
}
function setNickname(){
	$("#ts_nickname").hide();
	var name=$("#nickname").val();
	if(!checkValLength(name, 1, 20)){
		$("#error_nickname").html("不要超过10个字哦!");
		return ;
	}
	jQuery.ajax({
		url: "/profile/setting/nickname",
		type: "post",
		data: {"nickName" : name},
		dataType: "json",
		success: function(result){
			if(result.success){
				$("#nickname_xg").html("");
				cancel_updateDiv('nickname');
				$("#new_nickname").html(name);
				$("#error_nickname").html("");
			}else{
				$("#error_nickname").html(result.errorInfo);
			}
		},
		statusCode: {
		    401: function() {
		    	window.location.href = "/login?turnTo=" + window.location.href;
		    }
		}
	});
}
function setGender(){
	$("#ts_gender").hide();
	var gender=$("#gender").val();
	jQuery.ajax({
		url: "/profile/setting/gender",
		type: "post",
		data: {"gender" : gender},
		dataType: "json",
		success: function(result){
			if(result.success){
				$("#gender_xg").html("");
				cancel_updateDiv('gender');
				if(gender==1){
					$("#new_gender").html('男');	
				}else{
					$("#new_gender").html('女');
				}
				$("#error_nickname").html("");
			}else{
				$("#error_gender").html(result.errorInfo);
			}
		},
		statusCode: {
		    401: function() {
		    	window.location.href = "/login?turnTo=" + window.location.href;
		    }
		}
	});
}

function parseMonthlyIncome(str){
//	var arr = new Array();
	return str.split("-");
//	if(str.s){
//		arr[0]=0;
//		arr[1]=str.substring(1);
//	}else if(str.indexOf(">")!=-1){
//		arr[0]=str.substring(1);
//		arr[1]=0;
//	}else{
//		arr[0]=0;
//		arr[1]=0;
//	}
//	return arr;
}

function setting(uid){
	var province=$("#province").val();
	var city=$("#city").val();
	var town=$("#town").val();
	var birthYear=$("#birthYear").val();
	var birthMonth=$("#birthMonth").val();
	var birthDay=$("#birthDay").val();
	var birthSecret="0";
	var professionId=$("#professionId").val();
	var profession=$("#profession").val();
	var feature=$("#feature").val();
	var blog=$("#blog").val();
	var height=$("#height").val();
	var bloodType=$("#bloodType").val();
	var education=$("#education").val();
	var house=$("#house").val();
	var car=$("#car").val();
	var home=$("#home").val();
	var monthlyIncome=$("#monthlyIncome").val();
	var arr=parseMonthlyIncome(monthlyIncome);
	
	if(birthYear==0||birthMonth==0||birthDay==0){
		$("#birthSecret")[0].focus();
		 $("#birth_tp").html("&nbsp;&nbsp;&nbsp;您还没有选择生日!");
	}
	
	if(professionId==0&&(profession==""||profession=="10个字以内描述")){
		 $("#profession_tip").html("请输入!");
		 return ;
	}
	if(professionId==0&&!checkValLength(profession, 1, 20)){
		$("#profession")[0].focus();
		 $("#profession_tip").html("不要超过10个字哦!");
		 return ;
	}
	if(!checkValLength(feature, 0, 140)){
		$("#feature")[0].focus();
		 $("#feature_tp").html("不要超过70个字哦!");
		 return ;
	}
	if(!checkValLength(blog, 0, 70)){
		$("#blog")[0].focus();
		 $("#blog_tp").html("请输入正确的格式！");
		 return ;
	}
	if(!checkValLength(home, 0, 20)){
		$("#home")[0].focus();
		 $("#home_tp").html("不要超过10个字哦!");
		 return ;
	}
	
	
	if(town==''||town==null){
		town=-1;
	}
	if($("#towns").css("display")=="none"){
		town="-1";
	}
	if($("#birthSecret").is(':checked')){
		birthSecret="1";
	}
	if(professionId!=0){
		profession="";
	}
	jQuery.ajax({
		url: "/profile/setting",
		type: "post",
		data: {"province":province,"city" : city,"town" : town,"birthYear" : birthYear,"birthMonth" : birthMonth,
			"birthDay" : birthDay,
			"birthSecret" : birthSecret,
			"professionId" : professionId,
			"profession" : profession,
			"feature" : feature,
			"blog":blog,
			"height":height,
			"bloodType":bloodType,
			"education":education,
			"house":house,
			"car":car,
			"home":home,
			"minMonthlyIncome":arr[0],
			"maxMonthlyIncome":arr[1]
			},
		dataType: "json",
		success: function(result){
			if(result.success){
				//保存成功后跳转
				window.location.href = "/home/"+uid;
			}else{
				$(".pj_error").html(result.errorInfo);
			}
		},
		statusCode: {
		    401: function() {
		    	window.location.href = "/login?turnTo=" + window.location.href;
		    }
		}
	});
	
	
}