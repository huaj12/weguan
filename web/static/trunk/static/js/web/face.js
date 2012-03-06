$(document).ready(function() {
	$("#logoCutForm").submit(function() {
		var x = $('#face_x').val();
		var y = $('#face_y').val();
		var w = $('#face_w').val();
		var h = $('#face_h').val();
		var filePath = $('#filePath').val();
		if ("" == x || "" == y || "" == w || "" == h || "" == filePath) {
			return false;
		} else {
			return true;
		}
	});
	$(".save").bind("click", function() {
		$("#logoCutForm").submit();
		return false;
	});
	$("div.upload_area > div.uploading_ok > a").bind("click", function(){
		$(this).parent().hide();
		$("div.upload_area > div.btns").show();
		$("div.upload_area > div.upload_ts").show();
		return false;
	});
});

var jcrop_api;
function prepareJcrop() {
	var boundx = null;
	var boundy = null;
	$('#target').Jcrop({
		onChange : function(c) {
			updatePreview(c, boundx, boundy);
		},
		onSelect : function(c) {
			updatePreview(c, boundx, boundy);
		},
		aspectRatio : 1
	}, function() {
		// Use the API to get the real image size
		var bounds = this.getBounds();
		boundx = bounds[0];
		boundy = bounds[1];
		jcrop_api = this;
		jcrop_api.animateTo([ 135, 135, 315, 315 ]);
		jcrop_api.setOptions(this.checked ? {
			minSize : [ 90, 90 ]
		} : {
			minSize : [ 90, 90 ]
		});
	});
}

function updatePreview(c, boundx, boundy) {
	if (parseInt(c.w) > 0) {
		var rx = 180 / c.w;
		var ry = 180 / c.h;
		$('#preview_180').css({
			width : Math.round(rx * boundx) + 'px',
			height : Math.round(ry * boundy) + 'px',
			marginLeft : '-' + Math.round(rx * c.x) + 'px',
			marginTop : '-' + Math.round(ry * c.y) + 'px'
		});
		rx = 120 / c.w;
		ry = 120 / c.h;
		$('#preview_120').css({
			width : Math.round(rx * boundx) + 'px',
			height : Math.round(ry * boundy) + 'px',
			marginLeft : '-' + Math.round(rx * c.x) + 'px',
			marginTop : '-' + Math.round(ry * c.y) + 'px'
		});
		rx = 50 / c.w;
		ry = 50 / c.h;
		$('#preview_50').css({
			width : Math.round(rx * boundx) + 'px',
			height : Math.round(ry * boundy) + 'px',
			marginLeft : '-' + Math.round(rx * c.x) + 'px',
			marginTop : '-' + Math.round(ry * c.y) + 'px'
		});
		$('#face_x').val(c.x);
		$('#face_y').val(c.y);
		$('#face_w').val(c.w);
		$('#face_h').val(c.h);
	}
};

function releaseLogo() {
	jcrop_api.release();
	var logo = $("#target").attr("src");
	$("#preview_50").attr("src", logo);
	$("#preview_120").attr("src", logo);
	$("#preview_180").attr("src", logo);
}

function uploadImage() {
	var fileName = $(".btn_file_molding").val();
	$("div.upload_area > div.btns").hide();
	$("div.upload_area > div.error").hide();
	$("div.upload_area > div.upload_ts").hide();
	$("div.upload_area > div.uploading").show();
	var options = {
		url : "/profile/logo/upload",
		type : "POST",
		dataType : "json",
		iframe : "true",
		success : function(result) {
			if (result.success) {
				$("#target").attr("src", result.result[0]).show();
				$("#preview_180").attr("src", result.result[0]);
				$('#filePath').val(result.result[1]);
				// $("#preview_50").attr("src",result.result);
				// $("#preview_120").attr("src",result.result);
				if (jcrop_api != null) {
					jcrop_api.destroy();
				}
				prepareJcrop();
				$("div.upload_area > div.uploading").hide();
				$("div.upload_area > div.uploading_ok > font").text("已成功上传 " + fileName);
				$("div.upload_area > div.uploading_ok").show();
			} else if (result.errorCode == "00003") {
				window.location.href = "/login?turnTo=" + window.location.href;
			} else {
				$("div.upload_area > div.uploading").hide();
				$("div.upload_area > div.error").text(result.errorInfo).show();
				$("div.upload_area > div.btns").show();
			}
		},
		error : function(data) {
			$("div.upload_area > div.uploading").hide();
			$("div.upload_area > div.error").text("上传失败").show();
			$("div.upload_area > div.btns").show();
		}
	};
	$("#uploadImgForm").ajaxSubmit(options);
	return false;
}
