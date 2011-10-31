function sendUI(params,url){
  	var uiOpts = {
		  url : url,
		  display : 'iframe',
		  method :'get',
		  style : {
			  top:100,
			  left:75,
			  width:650,
			  height:520					  
		  },
		  params : params,
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
function feed(name){
	jQuery.get('/renrenFeed', {
		name : name,
		random : Math.random()
	}, function(data) {
		sendUI(data,'feed');
	});
	
}