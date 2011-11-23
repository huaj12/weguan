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
function feed(actId) {
	jQuery.get('/kaixinFeed', {
		actId : actId,
		random : Math.random()
	}, function(data) {
		showKxDialog(data);
	});
}
// TODO 封装不同第三方app
function request(name) {
	jQuery.get('/kaixinRequest', {
		name : name,
		random : Math.random()
	}, function(data) {
		showSysnewsDialog(data);
	});
}
// TODO
function requestByName(actId) {
	jQuery.get('/dialogSysnews', {
		actId : actId,
		random : Math.random()
	}, function(data) {
		showSysnewsDialog(data);
	});
}