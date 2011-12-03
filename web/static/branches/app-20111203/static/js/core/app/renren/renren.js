function sendUI(params,url,rwidth,rheight){
  	var uiOpts = {
		  url : url,
		  display : 'iframe',
		  method :'get',
		  style : {
			  width:rwidth,
			  height:rheight					  
		  },
		  params :params,
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
		sendUI(data,'feed',500.350);
	});
	
}

function recommendFeed(id){
	feed(id);
}

function request(id) {
	jQuery.get('/renrenRequest', {
		id : id,
		random : Math.random()
	}, function(data) {
		sendUI(data,'request',600,520);
		setHeight(520);
	});
}
