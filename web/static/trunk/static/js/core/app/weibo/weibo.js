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
			content : document.getElementById('feedDiv'),
			title : "微博分享",
			top : "50%"
		});
	});

}
function request(id) {
	jQuery.get('/weiboRequest', {
		random : Math.random()
	}, function(data) {
		clearRequest();
		$("#request_content").val(data.message);
		$("#request_picurl").attr("src", data.picurl);
		$.dialog({
			lock : true,
			content : document.getElementById('requestDiv'),
			title : "微博分享",
			top : "50%"
		});

	});
}
function clearRequest() {
	$("#request_content").val("");
}

function clearfeed() {
	$("#feed_content").val("");
	$("#feed_picurl").attr("src", "");
	$("#feed_actId").val("");
	$("#isFollow").attr('checked', false);

}
