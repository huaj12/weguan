$(document).ready(function(){
	$(".mouseHover").each(function(){
		$(this).bind("mouseover", function(){
			mouseHover(this, true);
		});
		$(this).bind("mouseout", function(){
			mouseHover(this, false);
		});
	});
});

function mouseHover(li, isOver){
	if(isOver){
		$(li).addClass("hover");
	}else {
		$(li).removeClass("hover");
	}
}