function sendUI(params,url){
  	var uiOpts = {
		  url : url,
		  display : 'iframe',
		  method :'get',
		  style : {
			  width:450,
			  height:420					  
		  },
		  params : JSON.decode(params),
		  onComplete : function(response){
			  if(window.console) 
				  console.log("complete: "+response);
		  },
		  onSuccess : function(response){
			  if(window.console) 
				  console.log("success: "+response);
			  if(response.access_token){
				  accessToken = response.access_token;
			  }
		  },
		  onFailure : function(response){
			  if(window.console) 
				  console.log("failure: " + response.error + ',' + response.error_description);
	 	  } 
	  };
	   Renren.ui(uiOpts);
  }
function feed(id){
	jQuery.get('/renrenFeed', {
		id: id,
		random : Math.random()
	}, function(data) {
		sendUI(data,'feed');
	});
	
}

function request(id) {
	jQuery.get('/renrenRequest', {
		id : id,
		random : Math.random()
	}, function(data) {
		sendUI(data,'request');
	});
}
