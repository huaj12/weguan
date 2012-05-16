var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="detail"]', {
			resizeType : 1,
			uploadJson : '/idea/kindEditor/upload',
			allowPreviewEmoticons : false,
			allowImageUpload : true,
			items : [ 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor',
					'bold', 'italic', 'underline',
					'removeformat', '|', 'justifyleft', 'justifycenter',
					'justifyright', 'insertorderedlist', 'insertunorderedlist',
					'|', 'emoticons', 'image', 'link' ]
		});
});
$(document).ready(function(){
	new LocationWidget();
	registerInitMsg($("input[name='place']"));
	registerInitMsg($("input[name='link']"));
	registerInitMsg($("input[name='startDay']"));
	registerInitMsg($("input[name='endDay']"));
	
	$("#charge-select").bind("change", function(){
		if("0"==$("#charge-select").val()){
			$("#charge-input").hide();
		}else{
			$("#charge-input").show();
		}
		
		return false;
	});
	
	$("div.send_btn>a").bind("click", function(){
		userCreateIdea();
		return false;
	});
});

function uploadImage() {
	$("div.pub_x > div.load_pic_btn").hide();
	$("div.pub_x > div.loading_pic_btn").show();
	$("#pic_tip").hide();
	var options = {
		url : "/idea/logo/upload",
		type : "POST",
		dataType : "json",
		iframe : "true",
		success : function(result) {
			if (result.success) {
				$("div.pic_img").show();
				$("div.pic_img> img").attr("src", result.result[0]);
				$("input[name='pic']").val(result.result[1]);
				$("div.loading_pic_btn").hide();
				$("div.load_pic_btn> form>a").text("重新上传图片");
				$("div.load_pic_btn").show();
			} else if (result.errorCode == "00003") {
				window.location.href = "/login?turnTo=" + window.location.href;
			} else {
				$("div.loading_pic_btn").hide();
				$("#pic_tip").text(result.errorInfo);
				$("#pic_tip").show();
				$("div.load_pic_btn> form>a").text("重新上传图片");
				$("div.load_pic_btn").show();
			}
		},
		error : function(data) {
			$("div.pub_x > div.loading_pic_btn").hide();
			$("#pic_tip").text("上传失败");
			$("#pic_tip").show();
			$("div.load_pic_btn> form>a").text("重新上传图片");
			$("div.load_pic_btn").show();
		}
	};
	$("#uploadImgForm").ajaxSubmit(options);
	return false;
}

function userCreateIdea(){
	var categoryId=$("input[name='categoryId']").val();
	var ideaId=$("input[name='ideaId']").val();
	var content=$("input[name='content']").val();
	var pic=$("input[name='pic']").val();
	var startDay=$("input[name='startDay']").val();
	var startHour=$("select[name='startHour']").val();
	var startMinute=$("select[name='startMinute']").val();
	var endDay=$("input[name='endDay']").val();
	var endHour=$("select[name='endHour']").val();
	var endMinute=$("select[name='endMinute']").val();
	var province=$("select[name='province']").val();
	var city=$("select[name='city']").val();
	var town=$("select[name='town']").val();
	var place=$("input[name='place']").val();
	var detail = editor.html();
	var charge=$("input[name='charge']").val();
	var link=$("input[name='link']").val();
	var placeDes=$("input[name='place']").attr("init-tip");
	var linkDes=$("input[name='link']").attr("init-tip");
	var endDayDes=$("input[name='endDay']").attr("init-tip");
	var startDayDes=$("input[name='startDay']").attr("init-tip");
	if(trimStr(place)==trimStr(placeDes)){
		place="";
	}
	if(trimStr(link)==trimStr(linkDes)){
		link="";
	}
	if(trimStr(startDay)==trimStr(startDayDes)){
		startDay="";
	}
	if(trimStr(endDay)==trimStr(endDayDes)){
		endDay="";
	}
	
	if(!checkValLength(content, 4, 160)){
		$("input[name='content']")[0].focus();
		$("#content_tip").show().text("标题请控制在2-80字以内");
		$("input[name='content']").parent().parent().addClass("wrong"); 
		return ;
	}else{
		$("#content_tip").hide();
		$("input[name='content']").parent().parent().removeClass("wrong"); 
	}
	
	if(pic==null||pic==""){
		$("input[name='pic']")[0].focus();
		$("#pic_tip").show().text("请上传封面");
		return ;
	}else{
		$("#pic_tip").hide();
	}
	
	if(categoryId=="3"||categoryId=="6"){
			if(trimStr(startDay)==""){
				$("input[name='startDay']")[0].focus();
				$("input[name='startDay']").parent().parent().addClass("wrong"); 
				$("#startDate_tip").show().text("请输入活动开始日期");
				return ;
			}else{
				$("input[name='startDay']").parent().parent().removeClass("wrong"); 
				$("#startDate_tip").hide();
			}
			
			if(trimStr(endDay)==""){
				$("input[name='endDay']")[0].focus();
				$("input[name='endDay']").parent().parent().addClass("wrong"); 
				$("#endDate_tip").show().text("请输入活动结束日期");
				return ;
			}else{
				$("input[name='endDay']").parent().parent().removeClass("wrong"); 
				$("#endDate_tip").hide();
			}
			if(province==0){
				$("select[name='province']")[0].focus();
				$("select[name='province']").parent().parent().addClass("wrong");
				$("#place_tip").show().text("请输入省份");
				return ;
			}else{
				$("select[name='province']").parent().parent().removeClass("wrong");
				$("#place_tip").hide();
			}
			
			if(city==0){
				$("select[name='city']")[0].focus();
				$("select[name='city']").parent().parent().addClass("wrong");
				$("#place_tip").show().text("请输入城市");
				return ;
			}else{
				$("select[name='city']").parent().parent().removeClass("wrong");
				$("#place_tip").hide();
			}
			
			if(town==-1){
				$("select[name='town']")[0].focus();
				$("select[name='town']").parent().parent().addClass("wrong");
				$("#place_tip").show().text("请输入区");
				return ;
			}else{
				$("select[name='town']").parent().parent().removeClass("wrong");
				$("#place_tip").hide();
			}
			
			if(trimStr(place)==""){
				$("input[name='place']")[0].focus();
				$("input[name='place']").parent().parent().addClass("wrong");
				$("#place_tip").show().text("详细地址");
				return ;
			}else{
				$("input[name='place']").parent().parent().removeClass("wrong");
				$("#place_tip").hide();
			}
			
			if(trimStr(detail)==""){
				$("textarea[name='detail']")[0].focus();
				$("textarea[name='detail']").parent().parent().addClass("wrong");
				$("#detail_tip").show().text("简介不能为空");
				return ;
			}else{
				$("textarea[name='detail']").parent().parent().removeClass("wrong");
				$("#detail_tip").hide();
			}
		}
		
		if(trimStr(charge)!=""){
			if(!isNum(charge)){
				$("input[name='charge']")[0].focus();
				$("input[name='charge']").parent().parent().addClass("wrong");
				$("#charge_tip").show().text("费用必须填写数字！");
				return ;
			}else{
				$("input[name='charge']").parent().parent().removeClass("wrong");
				$("#charge_tip").hide();
			}
		}
		
		if(!checkValLength(place, 0, 40)){
			$("input[name='place']")[0].focus();
			$("#place_tip").show().text("详细地址请在40字以内");
			$("input[name='place']").parent().parent().addClass("wrong"); 
			return ;
		}else{
			$("#place_tip").hide();
			$("input[name='place']").parent().parent().removeClass("wrong"); 
		}
		
		if(!checkValLength(place, 0, 40000)){
			$("textarea[name='detail']")[0].focus();
			$("#detail_tip").show().text("介绍太多了!");
			$("textarea[name='detail']").parent().parent().addClass("wrong"); 
			return ;
		}else{
			$("#detail_tip").hide();
			$("textarea[name='detail']").parent().parent().removeClass("wrong"); 
		}
		
		if(!checkValLength(link, 0, 300)){
			$("input[name='link']")[0].focus();
			$("#link_tip").show().text("相关连接太长！");
			$("input[name='link']").parent().parent().addClass("wrong"); 
			return ;
		}else{
			$("#link_tip").hide();
			$("input[name='link']").parent().parent().removeClass("wrong"); 
		}
		 if(getByteLen(startHour)==1){
			 startHour="0"+startHour;
		 }
		 if(getByteLen(startMinute)==1){
			 startMinute="0"+startMinute;
		 }
		 if(getByteLen(endHour)==1){
			 endHour="0"+endHour;
		 }
		 if(getByteLen(endMinute)==1){
			 endMinute="0"+endMinute;
		 }
		var  startDateString=startDay+" "+startHour+":"+startMinute+":00";
		var endDateString=endDay+" "+endHour+":"+endMinute+":00";
	jQuery.ajax({
		url: "/idea/create",
		type: "post",
		data: {"ideaId":ideaId,"categoryId" : categoryId,"content":content,"pic":pic,"startDateString":startDateString,"endDateString":endDateString,"province":province,"city":city,"town":town,"place":place,"detail":detail,"charge":charge,"link":link},
		dataType: "json",
		success: function(result){
			if(result&&result.success){
				alert("添加成功");
			}else{
				if(result.errorCode=='180001'){
					$("#content_tip").show().text(result.errorInfo);
					$("input[name='content']").parent().parent().addClass("wrong"); 
				}else if(result.errorCode=='180003'){
					$("input[name='pic']")[0].focus();
					$("#pic_tip").show().text(result.errorInfo);
				}else if(result.errorCode=='180011'){
					$("select[name='city']")[0].focus();
					$("select[name='city']").parent().parent().addClass("wrong");
					$("#place_tip").show().text(result.errorInfo);
				}else if(result.errorCode=='180012'){
					$("select[name='town']")[0].focus();
					$("select[name='town']").parent().parent().addClass("wrong");
					$("#place_tip").show().text(result.errorInfo);
				}else if(result.errorCode=='180005'||result.errorCode=='180007'){
					$("input[name='place']")[0].focus();
					$("#place_tip").show().text(result.errorInfo);
					$("input[name='place']").parent().parent().addClass("wrong"); 
				}else if(result.errorCode=='180006'||result.errorCode=='180008'){
					$("textarea[name='detail']")[0].focus();
					$("#detail_tip").show().text(result.errorInfo);
					$("textarea[name='detail']").parent().parent().addClass("wrong"); 
				}else if(result.errorCode=='180009'){
					$("input[name='link']")[0].focus();
					$("#link_tip").show().text(result.errorInfo);
					$("input[name='link']").parent().parent().addClass("wrong"); 
				}
				else{
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