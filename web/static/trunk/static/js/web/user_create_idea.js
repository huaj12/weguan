$(document).ready(function() {
	$(window).bind('beforeunload', function() {
		return '关闭页面后，当前好主意内容将不被保存，确定要放弃编辑吗？';
	});
	$("#charge-select").bind("change", function() {
		if ("0" == $("#charge-select").val()) {
			$("#charge-input").hide();
		} else {
			$("#charge-input").show();
		}
		$("input[name='charge']").val("");
		return false;
	});

	$("div.send_btn>a").bind("click", function() {
		userCreateIdea();
		return false;
	});
	
	new LocationWidget();
	registerInitMsg($("input[name='place']"));
	registerInitMsg($("input[name='link']"));
	registerInitMsg($("input[name='startDay']"));
	registerInitMsg($("input[name='endDay']"));
});

function uploadImage() {
	$("div.pub_x > div.load_pic_btn").hide();
	$("div.pub_x > div.loading_pic_btn").show();
	$("#pic-tip").hide();
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
				$("#pic-tip").text(result.errorInfo);
				$("#pic-tip").show();
				$("div.load_pic_btn> form>a").text("重新上传图片");
				$("div.load_pic_btn").show();
			}
		},
		error : function(data) {
			$("div.pub_x > div.loading_pic_btn").hide();
			$("#pic-tip").text("上传失败");
			$("#pic-tip").show();
			$("div.load_pic_btn> form>a").text("重新上传图片");
			$("div.load_pic_btn").show();
		}
	};
	$("#uploadImgForm").ajaxSubmit(options);
	return false;
}

function userCreateIdea() {
	var categoryId = $("input[name='categoryId']").val();
	var ideaId = $("input[name='ideaId']").val();
	var createUid = $("input[name='createUid']").val();
	var content = $("input[name='content']").val();
	var pic = $("input[name='pic']").val();
	var startDay = $("input[name='startDay']").val();
	var startHour = $("select[name='startHour']").val();
	var startMinute = $("select[name='startMinute']").val();
	var endDay = $("input[name='endDay']").val();
	var endHour = $("select[name='endHour']").val();
	var endMinute = $("select[name='endMinute']").val();
	var province = $("select[name='province']").val();
	var city = $("select[name='city']").val();
	var town = $("select[name='town']").val();
	var place = $("input[name='place']").val();
	var detail = $("textarea[name='detail']").val();
	var charge = $("input[name='charge']").val();
	var link = $("input[name='link']").val();
	var placeDes = $("input[name='place']").attr("init-tip");
	var linkDes = $("input[name='link']").attr("init-tip");
	var endDayDes = $("input[name='endDay']").attr("init-tip");
	var startDayDes = $("input[name='startDay']").attr("init-tip");
	if (trimStr(place) == trimStr(placeDes)) {
		place = "";
	}
	if (trimStr(link) == trimStr(linkDes)) {
		link = "";
	}
	if (trimStr(startDay) == trimStr(startDayDes)) {
		startDay = "";
	}
	if (trimStr(endDay) == trimStr(endDayDes)) {
		endDay = "";
	}

	if (!checkValLength(content, 4, 160)) {
		$("input[name='content']")[0].focus();
		$("#content-tip").show().text("标题请控制在2-80字以内");
		$("input[name='content']").parent().parent().addClass("wrong");
		return;
	} else {
		$("#content-tip").hide();
		$("input[name='content']").parent().parent().removeClass("wrong");
	}

	if (pic == null || pic == "") {
		// 定位不到pic定位content
		$("input[name='content']")[0].focus();
		$("#pic-tip").show().text("请上传封面");
		return;
	} else {
		$("#pic-tip").hide();
	}

	if (categoryId == "3" || categoryId == "6") {
		if (trimStr(startDay) == "") {
			$("input[name='startDay']")[0].focus();
			$("input[name='startDay']").parent().parent().addClass("wrong");
			$("#start-date-tip").show().text("请输入活动开始日期");
			return;
		} else {
			$("input[name='startDay']").parent().parent().removeClass("wrong");
			$("#start-date-tip").hide();
		}

		if (trimStr(endDay) == "") {
			$("input[name='endDay']")[0].focus();
			$("input[name='endDay']").parent().parent().addClass("wrong");
			$("#end-date-tip").show().text("请输入活动结束日期");
			return;
		} else {
			$("input[name='endDay']").parent().parent().removeClass("wrong");
			$("#end-date-tip").hide();
		}
		if (province == 0) {
			$("select[name='province']")[0].focus();
			$("select[name='province']").parent().parent().addClass("wrong");
			$("#place-tip").show().text("请输入省份");
			return;
		} else {
			$("select[name='province']").parent().parent().removeClass("wrong");
			$("#place-tip").hide();
		}

		if (city == 0) {
			$("select[name='city']")[0].focus();
			$("select[name='city']").parent().parent().addClass("wrong");
			$("#place-tip").show().text("请输入城市");
			return;
		} else {
			$("select[name='city']").parent().parent().removeClass("wrong");
			$("#place-tip").hide();
		}

		if (town == -1) {
			$("select[name='town']")[0].focus();
			$("select[name='town']").parent().parent().addClass("wrong");
			$("#place-tip").show().text("请输入区");
			return;
		} else {
			$("select[name='town']").parent().parent().removeClass("wrong");
			$("#place-tip").hide();
		}

		if (trimStr(place) == "") {
			$("input[name='place']")[0].focus();
			$("input[name='place']").parent().parent().addClass("wrong");
			$("#place-tip").show().text("详细地址");
			return;
		} else {
			$("input[name='place']").parent().parent().removeClass("wrong");
			$("#place-tip").hide();
		}

		if (trimStr(detail) == "") {
			$("textarea[name='detail']").parent().parent().addClass("wrong");
			$("#detail-tip").show().text("简介不能为空");
			return;
		} else {
			$("textarea[name='detail']").parent().parent().removeClass("wrong");
			$("#detail-tip").hide();
		}
	}

	if (trimStr(charge) != "") {
		if (!isNum(charge)) {
			$("input[name='charge']")[0].focus();
			$("input[name='charge']").parent().parent().addClass("wrong");
			$("#charge-tip").show().text("费用必须填写数字！");
			return;
		} else {
			$("input[name='charge']").parent().parent().removeClass("wrong");
			$("#charge-tip").hide();
		}
	}

	if (!checkValLength(place, 0, 100)) {
		$("input[name='place']")[0].focus();
		$("#place-tip").show().text("详细地址请在50字以内");
		$("input[name='place']").parent().parent().addClass("wrong");
		return;
	} else {
		$("#place-tip").hide();
		$("input[name='place']").parent().parent().removeClass("wrong");
	}

	if (!checkValLength(detail, 0, 2000)) {
		$("#detail-tip").show().text("简介内容请不要超过1000字");
		$("textarea[name='detail']").parent().parent().addClass("wrong");
		return;
	} else {
		$("#detail-tip").hide();
		$("textarea[name='detail']").parent().parent().removeClass("wrong");
	}

	if (!checkValLength(link, 0, 300)) {
		$("input[name='link']")[0].focus();
		$("#link-tip").show().text("相关连接太长！");
		$("input[name='link']").parent().parent().addClass("wrong");
		return;
	} else {
		$("#link-tip").hide();
		$("input[name='link']").parent().parent().removeClass("wrong");
	}
	if (getByteLen(startHour) == 1) {
		startHour = "0" + startHour;
	}
	if (getByteLen(startMinute) == 1) {
		startMinute = "0" + startMinute;
	}
	if (getByteLen(endHour) == 1) {
		endHour = "0" + endHour;
	}
	if (getByteLen(endMinute) == 1) {
		endMinute = "0" + endMinute;
	}
	var startDateString = "";
	if (startDay != "") {
		startDateString = startDay + " " + startHour + ":" + startMinute
				+ ":00";
	}
	var endDateString = "";
	if (endDay != "") {
		endDateString = endDay + " " + endHour + ":" + endMinute + ":00";
	}
	jQuery
			.ajax({
				url : "/idea/save",
				type : "post",
				data : {
					"createUid" : createUid,
					"ideaId" : ideaId,
					"categoryId" : categoryId,
					"content" : content,
					"pic" : pic,
					"startDateString" : startDateString,
					"endDateString" : endDateString,
					"province" : province,
					"city" : city,
					"town" : town,
					"place" : place,
					"detail" : detail,
					"charge" : charge,
					"link" : link
				},
				dataType : "json",
				success : function(result) {
					if (result && result.success) {
						$(window).unbind("beforeunload");
						if (ideaId == null || ideaId == "") {
							window.location.href = "/showideas";
						} else {
							window.location.href = "/idea/" + ideaId;
						}
					} else {
						if (result.errorCode == '180001'
								|| result.errorCode == '180010') {
							$("#content-tip").show().text(result.errorInfo);
							$("input[name='content']").parent().parent()
									.addClass("wrong");
						} else if (result.errorCode == '180003') {
							$("input[name='pic']")[0].focus();
							$("#pic-tip").show().text(result.errorInfo);
						} else if (result.errorCode == '180011') {
							$("select[name='city']")[0].focus();
							$("select[name='city']").parent().parent()
									.addClass("wrong");
							$("#place-tip").show().text(result.errorInfo);
						} else if (result.errorCode == '180012') {
							$("select[name='town']")[0].focus();
							$("select[name='town']").parent().parent()
									.addClass("wrong");
							$("#place-tip").show().text(result.errorInfo);
						} else if (result.errorCode == '180005'
								|| result.errorCode == '180007') {
							$("input[name='place']")[0].focus();
							$("#place-tip").show().text(result.errorInfo);
							$("input[name='place']").parent().parent()
									.addClass("wrong");
						} else if (result.errorCode == '180006'
								|| result.errorCode == '180008') {
							$("#detail-tip").show().text(result.errorInfo);
							$("textarea[name='detail']").parent().parent()
									.addClass("wrong");
						} else if (result.errorCode == '180009') {
							$("input[name='link']")[0].focus();
							$("#link-tip").show().text(result.errorInfo);
							$("input[name='link']").parent().parent().addClass(
									"wrong");
						} else if (result.errorCode == '180013') {
							$("input[name='startDay']")[0].focus();
							$("#start-date-tip").show().text(result.errorInfo);
							$("input[name='startDay']").parent().parent()
									.addClass("wrong");
						} else {
							alert(result.errorInfo);
						}
					}
				},
				statusCode : {
					401 : function() {
						window.location.href = "/login?turnTo="
								+ window.location.href;
					}
				}
			});
}