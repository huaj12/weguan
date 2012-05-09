function feed(id) {
	jQuery.get('/weiboFeed', {
		id : id,
		random : Math.random()
	}, function(data) {
		clearfeed();
		$("#feed_content").val(data.message);
		$("#feed_picurl").attr("src", data.picurl);
		$("#feed_actId").val(data.actId);
		$.dialog({
			lock : true,
			content :$("#feedDiv")[0],
			title : "微博分享",
			top : "50%"
		});
	});

}

function recommendFeed(id){
	feed(id);
}

function request(id) {
	jQuery.get('/weiboRequest', {
		random : Math.random()
	}, function(data) {
		clearRequest();
		$("#request_content").val(data.message);
		$.dialog({
			lock : true,
			content : $("#requestDiv")[0],
			title : "微博分享",
			top : "50%"
		});

	});
}
function clearRequest() {
	$("#request_content").val("");
	show_btn('request');
}

function clearfeed() {
	$("#feed_content").val("");
	$("#feed_picurl").attr("src", "");
	$("#feed_actId").val("");
	$("#isFollow").attr('checked', false);
	show_btn('feed');
}
function sendRequest() {
	hide_btn('request');
	var content = $("#request_content").val();
	if (trimStr(content).length == 0) {
		alert("给你的粉丝分享点内容吧！");
		show_btn('request');
		return;
	}
	$.post('/sendRequest', {
		content : content,
		random : Math.random()
	}, function(result) {
		if (result && result.success) {
			closeAllDiv();
			$.dialog({
				lock : true,
				content : '<div>分享成功</div>',
				top : "50%",
				width : 305,
				time : 2,
				title : "微博分享"
			});
		} else {
			closeAllDiv();
			// 未知错误请刷新页面后重试
			$.dialog({
				lock : true,
				content : '刷新页面后重试',
				icon : 'error',
				time : 3,
				top : "50%"

			});
		}
	});
}

function sendfeed() {
	hide_btn('feed');
	var content = $("#feed_content").val();
	var actId = $("#feed_actId").val();
	var isFollow = "";
	if ($("#isFollow").attr('checked') != undefined) {
		isFollow = $("#isFollow").val();
	}
	if (trimStr(content).length == 0) {
		alert("给你的粉丝分享点内容吧！");
		show_btn('feed');
		return;
	}
	$.post('/sendFeed', {
		content : content,
		actId : actId,
		isFollow : isFollow,
		random : Math.random()
	}, function(result) {
		if (result && result.success) {
			closeAllDiv();
			$.dialog({
				lock : true,
				content : '<div>分享成功</div>',
				top : "50%",
				width : 305,
				time : 2,
				title : "微博分享"
			});

		} else {
			closeAllDiv();
			// 未知错误请刷新页面后重试
			$.dialog({
				lock : true,
				content : '刷新页面后重试',
				icon : 'error',
				time : 3,
				top : "50%"

			});
		}
	});
}