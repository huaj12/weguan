	function page_content(url,ajaxId){
		$.get(url, {
		    random : Math.random()
		}, function(data) {
			$("#"+ajaxId).html(data);
			setHeight();
		});
	}
