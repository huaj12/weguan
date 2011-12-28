var editor;
KindEditor.ready(function(K) {
	editor = K.create('textarea[name="detail"]', {
		resizeType : 1,
		uploadJson : '/act/kindEditor/upload',
		allowPreviewEmoticons : false,
		allowImageUpload : true,
		items : [ 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor',
				'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter',
				'justifyright', 'insertorderedlist', 'insertunorderedlist',
				'|', 'emoticons', 'image', 'link' ]
	});
});

$(document).ready(function() {
	$("div.upload > div.load_done > a").bind("click", function(){
		$(this).parent().removeAttr("filePath").hide();
		$("div.upload > div.sc_btn").show();
	});
});

function uploadImage() {
	$("div.upload > div.sc_btn").hide();
	$("div.upload > div.error").hide();
	$("div.upload > div.loading").show();
	var options = {
		url : "/act/logo/upload",
		type : "POST",
		dataType : "json",
		success : function(result) {
			$(".loading").hide();
			if (result.success) {
				$("div.upload > div.load_done > p > img").attr("src", result.result[0]);
				$("div.upload > div.loading").hide();
				$("div.upload > div.load_done").attr("filePath", result.result[1]).show();
			} else if (result.error == "00003") {
				window.location.href = "/login";
			} else {
				$("div.upload > div.loading").hide();
				$("div.upload > div.error").text(result.errorInfo).show();
				$("div.upload > div.sc_btn").show();
			}
		},
		error : function(data) {
			$("div.upload > div.loading").hide();
			$("div.upload > div.error").text("上传失败").show();
			$("div.upload > div.sc_btn").show();
		}
	};
	$("#uploadImgForm").ajaxSubmit(options);
	return false;
}

function selectCity(obj) {
	$.get('/base/selectCity', {
		proId : obj.value,
		random : Math.random()
	}, function(result) {
		$("#citys").html(result);
		if($("#c_id")[0]){
			selectTown($("#c_id").val());
			$("#towns").show();
		}else{
			$("#towns").hide();
		}
	});
}

function selectTown(id) {
	$.get('/base/selectTown', {
		cityId : id,
		random : Math.random()
	}, function(result) {
		$("#towns").html(result);
	});
}

function addRawAct() {
	var name = $("#name").val();
	var detail = editor.html();
	var filePath = $(".load_done").attr("filePath");
	var categoryId = $("#category_ids").val();
	var address = $("#address").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var province = $("#province").val();
	var city = $("#city").val();
	var town=$("#town").val();
	//判断是否有town
	if($("#towns").css("display")=="none"){
		town="-1";
	}
	if (address == "详细地址") {
		address = "";
	}
	if (!checkValLength(name, 2, 20)) {
		$("#name")[0].focus();
		$("#name_tip").html("项目名控制在1－10个中文内！").stop(true, true).show().fadeOut(
				4000);
		return;
	}
	var detailCount = editor.text();
	var detail_length = getByteLen(detailCount);
	if (detail_length > 8000) {
		editor.focus();
		$("#detail_tip").html("详细信息不能超过4000个中文当前" + (detail_length / 2) + "字")
				.stop(true, true).show().fadeOut(4000);
		return;
	}
	 if (filePath == null || filePath == "") {
		$("#logo")[0].focus();
		$("#logo_tip").text("项目图片不能为空").stop(true, true).show().fadeOut(4000);
		return;
	}
	if (!checkValLength(address, 0, 60)) {
		$("#address")[0].focus();
		$("#address_tip").html("详细地址必须少于30个字！").stop(true, true).show()
				.fadeOut(4000);
	}
	
	jQuery.ajax({
		url : "/act/addRawAct",
		type : "post",
		cache : false,
		data : {
			"name" : name,
			"detail" : detail,
			"filePath" : filePath,
			"categoryId" : categoryId,
			"address" : address,
			"startTime" : startTime,
			"endTime" : endTime,
			"town":town,
			"city" : city,
			"province" : province
		},
		dataType : "json",
		success : function(result) {
			if (result && result.success) {
				var url = window.location.href;
				if(url.indexOf("?success=true") < 0){
					url = url + "?success=true";
				}
				window.location.href = url;
			} else {
				alert(result.errorInfo);
			}
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login";
			}
		}
	});
}
