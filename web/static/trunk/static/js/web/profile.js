$(document).ready(function(){
	if($("#towns").css("display")=="none"){
		selectCity($("#city")[0]);
	}
});
function selectProvince(obj) {
	$.get('/base/selectProvince', {
		proId : obj.value,
		random : Math.random()
	}, function(result) {
		$("#citys").html(result);
		selectCity($("#c_id")[0]);
	});
}
function selectCity(obj) {
	$.get('/base/selectCity', {
		cityId : obj.value,
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
	if(town==''||town==null){
		town=-1;
	}
	if(province==""||province==0){
		 $("#province")[0].focus();
		 $("#location_tip").show().text("请选择省份");
		 return ;
	}else{
		$("#location_tip").hide();
	}
	
	if(city==""||city==0){
		 $("#city")[0].focus();
		 $("#location_tip").show().text("请选择城市");
		 return ;
	}else{
		$("#location_tip").hide();
	}
	if($("#towns").is(":visible")&&town==-1){
			 $("#town")[0].focus();
			 $("#location_tip").show().text("请选择所在区");
			 return ;
	}else{
		$("#location_tip").hide();
	}
	if(birthYear==0||birthMonth==0||birthDay==0){
		 $("#birthSecret")[0].focus();
		 $("#birth_tp").show().text("请选择生日!");
		 return ;
	}else{
		$("#birth_tp").hide();
	}
	if(professionId==-1){
		 $("#profession_tip").show().text("请选择职业!");
		 return ;
	}else{
		$("#profession_tip").hide();
	}
	if(professionId==0&&(profession==""||profession=="10个字以内描述")){
		 $("#profession_tip").show().text("请输入!");
		 return ;
	}else{
		$("#profession_tip").hide();
	}
	if(professionId==0&&!checkValLength(profession, 1, 20)){
		$("#profession")[0].focus();
		 $("#profession_tip").show().text("职业描述，不要超过10个字哦!");
		 return ;
	}else{
		$("#profession_tip").hide();
	}
	if(!checkValLength(feature, 0, 140)){
		$("#feature")[0].focus();
		 $("#feature_tp").show().text("自我评价请控制在70字以内");
		 return ;
	}else{
		$("#feature_tp").hide();
	}
	if(!checkValLength(blog, 0, 70)){
		$("#blog")[0].focus();
		 $("#blog_tp").show().text("请输入正确格式的个人主页");
		 return ;
	}else{
		$("#blog_tp").hide();
	}
	if(!checkValLength(home, 0, 20)){
		$("#home")[0].focus();
		 $("#home_tp").show().text("家乡名称请不要超过10个字");
		 return ;
	}else{
		$("#home_tp").hide();
	}
	if($("#birthSecret").is(':checked')){
		birthSecret="1";
	}
	if(professionId!=0){
		profession="";
	}
	if($("#towns").css("display")=="none"){
		town="-1";
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
				if(result.errorCode=='20008'||result.errorCode=='20009'||result.errorCode=='20023'){
					$("#location_tip").show().text(result.errorInfo);
				}else if(result.errorCode=='20010'||result.errorCode=='20011'||result.errorCode=='20012'){
					$("#birth_tp").show().text(result.errorInfo);
				}else if(result.errorCode=='20013'||result.errorCode=='20014'||result.errorCode=='20015'){
					$("#profession_tip").show().text(result.errorInfo);
				}else if(result.errorCode=='20016'||result.errorCode=='20017'||result.errorCode=='20022'){
					$("#feature_tp").show().text(result.errorInfo);
				}else if(result.errorCode=='20019'||result.errorCode=='20024'){
					$("#blog_tp").show().text(result.errorInfo);
				}else if(result.errorCode=='20020'){
					$("#home_tp").show().text(result.errorInfo);
				}else{
					alert(result.errorInfo);	
				}
				
			}
		},
		statusCode: {
		    401: function() {
		    	window.location.href = "/login?turnTo=" + window.location.href;
		    }
		}
	});
	
	
}