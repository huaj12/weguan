$(document).ready(function() {
	$("div.item").each(function(){
		var form = $(this).find("form");
		var commentWidget = new CommentWidget(form);
		commentWidget.bindDelLink(this);
		commentWidget.bindReply();
		$(this).find("a.reply-link").click(function(){
			var area = form.find("div.repy_area_s2");
			if($(area).is(":visible")){
				area.hide();
			}else{
				area.show();
			}
		});
	});
});