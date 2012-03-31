$(document).ready(function(){
	$("div.btn > a").zclip({
		path : 'http://static.51juzhai.com/swf/ZeroClipboard.swf',
		copy : $('div.text_area > textarea').val()
	});
});