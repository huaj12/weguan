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
		var imgObj = $("div.upload > div.load_done > p > img");
		imgObj.attr("src", imgObj.attr("loading-src"));
		$("div.upload > div.sc_btn").show();
		return false;
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
		iframe : "true",
		success : function(result) {
			$(".loading").hide();
			if (result.success) {
				$("div.upload > div.load_done > p > img").attr("src", result.result[0]);
				$("div.upload > div.loading").hide();
				$("div.upload > div.load_done").attr("filePath", result.result[1]).show();
			} else if (result.errorCode == "00003") {
				window.location.href = "/login?turnTo=" + window.location.href;
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
	if(town==''||town==null){
		town=-1;
	}
	if($("#towns").css("display")=="none"){
		town="-1";
	}
	if (address == "详细地址") {
		address = "";
	}
	if (!checkValLength(name, 2, 20)) {
		$("#name")[0].focus();
		$("#name_tip").html("请不要超过10个字").stop(true, true).show().fadeOut(
				4000);
		return;
	}
	var detailCount = editor.text();
	var detail_length = getByteLen(detailCount);
	if (detail_length > 8000) {
		$("#detail_tip").html("内容过多，请精简一些!")
				.stop(true, true).show().fadeOut(4000);
		return;
	}
	 if (filePath == null || filePath == "") {
		 $("div.upload > div.error").text("请上传！").stop(true, true).show().fadeOut(4000);
		return;
	}
	if (!checkValLength(address, 0, 60)) {
		$("#address_tip").html("请不要超过30个字！").stop(true, true).show()
				.fadeOut(4000);
		return;
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
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}
