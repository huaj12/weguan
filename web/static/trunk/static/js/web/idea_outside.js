$(document).ready(function(){
	var top=$("div.main_iframe_part").css("padding-top").replace("px","");
	var iframe_height=$(window).height()-top ;
	var t = document.createElement("div");
	var link=$("div.main_iframe_part").attr("idea-link");
	t.innerHTML = '<iframe src="'+link+'"  noresize="noresize" frameborder=0   width="100%" height="'+iframe_height+'" ></iframe>';
	$("div.main_iframe_part")[0].appendChild(t.firstChild);
});
