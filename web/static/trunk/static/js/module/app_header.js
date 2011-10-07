//TODO 封装不同第三方app
function showKxDialog(para) {
	var t = document.createElement("div");
	t.innerHTML = '<iframe src="http://www.kaixin001.com/rest/rest.php?para='
			+ para
			+ '" scrolling="yes" height="0px"  width="0px" style="display:none"></iframe>';
	document.body.appendChild(t.firstChild);
}
function showSysnewsDialog(url) {
	var t = document.createElement("div");
	t.innerHTML = '<iframe id="iframeinfo" src="'
			+ url
			+ '" scrolling="no" height="344px"  width="547px" style="display:none;"></iframe>';
	document.body.appendChild(t.firstChild);
}
//TODO 封装不同第三方app
function kaixinFeed(name) {
	jQuery.get('/kaixinFeed', {
		name : name,
		random : Math.random()
	}, function(data) {
		showSysnewsDialog(data);
	});
}
// TODO 封装不同第三方app
function kaixinRequest(name) {
	jQuery.get('/kaixinRequest', {
		name : name,
		random : Math.random()
	}, function(data) {
		showKxDialog(data);
	});
}
// TODO
function dialogSysnews(name) {
	jQuery.get('/dialogSysnews', {
		name : name,
		random : Math.random()
	}, function(data) {
		showSysnewsDialog(data);
	});
}

function addAct(successCallback) {
	var value = $("#addAct").attr("value");
	if (!value || value == "") {
		$("#headAddActError").text("请先输入").stop(true, true).show()
				.fadeOut(2000);
		return false;
	}
	if (!checkValLength(value, 2, 20)) {
		$("#headAddActError").text("拒宅兴趣字数控制在1－10个中文内！").stop().show().fadeOut(
				2000);
		return false;
	}
	jQuery.ajax({
		url : "/app/ajax/addAct",
		type : "post",
		cache : false,
		data : {
			"actName" : value
		},
		dataType : "json",
		success : function(result) {
			// result handle plugin
			if (result && result.success) {
				$("#headAddActError").text("保存成功").stop(true, true).show()
						.fadeOut(3000);
				$("#addAct").attr("value", "");
				if ($("div.interesting").length > 0) {
					pageMyAct(1);
				}
				if (successCallback) {
					successCallback();
				}
			} else if (result) {
				if (result && result.errorCode == 10003 && successCallback) {
					successCallback();
				}
				$("#headAddActError").text(result.errorInfo).stop(true, true)
						.show().fadeOut(2000);
			} else {
				alert("系统异常！");
			}
		},
		statusCode : {
			401 : function() {
				alert("未登录");
			}
		}
	});
}

function subEmail() {
	// validation
	var subInput = $("div.dy > span > input");
	var email = subInput.val();
	if (email == subInput.attr("initmsg")) {
		return false;
	}
	if (!checkEmail(email)) {
		$("div.dy > div.error").text("邮箱格式有误！").stop(true, true).show()
				.fadeOut(2000);
		return false;
	}
	jQuery.ajax({
		url : "/app/ajax/subEmail",
		type : "post",
		cache : false,
		data : {
			"email" : email
		},
		dataType : "json",
		success : function(result) {
			if (result && result.success) {
				$("div.dy > div.error").text("订阅成功").stop(true, true).show()
						.fadeOut(3000);
			} else if (result) {
				$("div.dy > div.error").text(result.errorInfo).show().fadeOut(
						2000);
			} else {
				alert("系统异常！");
			}
		},
		statusCode : {
			401 : function() {
				alert("未登录");
			}
		}
	});
}

function showSubEmailDefault(inputObj) {
	var obj = $(inputObj);
	var defaultMsg = obj.attr("initmsg");
	if (obj.val() == "") {
		obj.val(defaultMsg);
	}
}

function hideSubEmailDefault(inputObj) {
	var obj = $(inputObj);
	var defaultMsg = obj.attr("initmsg");
	if (obj.val() == defaultMsg) {
		obj.val("");
	}
}

function showUnreadCnt() {
	jQuery.ajax({
		url : "/msg/getUnMessageCount",
		type : "get",
		cache : false,
		dataType : "json",
		success : function(result) {
			// result handle plugin
			if (result && result.success) {
				var cnt = result.result;
				$(".message_num > span").text(cnt);
				if (cnt > 0) {
					$(".message_num").show();
				} else {
					$(".message_num").hide();
				}
			}
		}
	});
}

$(document).ready(function() {
	var addActInput = new AddActInput($("#addAct"), $("#headAddActError"));
	addActInput.bindKeyUp();
	addActInput.bindFocus();
	addActInput.bindBlur();
	addActInput.bindAutocomplete();

	// 注册添加Act事件
	$("div.zjxq_input > a.add").bind("click", function() {
		addAct();
	});
	// 注册召集令
	$("div.zjxq_input > a.zhao").bind("click", function() {
		var value = $("#addAct").attr("value");
		if (value && value != "") {
			addAct(function() {
				kaixinFeed(value);
			});
		} else {
			kaixinFeed();
		}
	});
	// 注册邀请
	$("div.zjxq_input > a.yao").bind("click", function() {
		var value = $("#addAct").attr("value");
		if (value && value != "") {
			addAct(function() {
				kaixinRequest(value);
			});
		} else {
			kaixinRequest();
		}
	});
	// 注册订阅邮箱事件
	var subInput = $("div.dy > span > input");
	showSubEmailDefault(subInput);
	subInput.bind("focus", function() {
		hideSubEmailDefault(this);
	});
	subInput.bind("blur", function() {
		showSubEmailDefault(this);
	});
	$("div.dy > a").bind("click", function() {
		subEmail();
	});

	// 注册获取新消息数事件
	showUnreadCnt();
	setInterval(function() {
		showUnreadCnt();
	}, 10000);

	var proBoxNum = $("div.pro_box").size();
	if (proBoxNum > 1) {
		var tipId = 1;
		setInterval(function() {
			$("div.tips_" + tipId).fadeOut(500, function() {
				tipId = tipId == proBoxNum ? 1 : (tipId + 1);
				$("div.tips_" + tipId).fadeIn(500);
			});
		}, 5000);
	}
});