function setHeight(){
	var dHeight = document.documentElement.offsetHeight;
	if("\v"=="v")
	{
		dHeight = document.body.scrollHeight;
	}
	var t = document.createElement("div");
	t.innerHTML = '<iframe id="kxiframeagent" src="http://rest.kaixin001.com/api/agent.html#'+dHeight+'" scrolling="yes" height="0px" width="0px" frameborder="0"></iframe>';
	document.documentElement.appendChild(t.firstChild);
}

$(document).ready(function(){
	setHeight();
});
