function doLogin(loginForm) {
	var request = new HttpRequest(WeGaunConstants.httpService+"/login.html", "POST", {
		onRequestSuccess : function(responseText) {
			var resultArray = JSON.parse(responseText);
			if (resultArray[0]) {
				if (resultArray[2] && (typeof(resultArray[2]) == "string")) {
					//var date = new Date();
					//date.setMilliseconds(date.getMilliseconds() + 1000 * 60
					//		* 60 * 24 * 30);
					//Cookie.set('loginCookie', resultArray[2], {
					//	path : '/',
					//	duration : 30
					//});
				} else {
					//Cookie.remove("loginCookie");
				}
				window.location.href = resultArray[1];
			} else {
				if ($("message")) {
					$("message").innerHTML = "用户名或密码错误错误！";
				} else {
					window.location.href = resultArray[1];
				}
			}

		},
		onRequestError : function(errorCode) {
		}
	});
	request.fillWithForm(loginForm);
	request.sendRequest();
	return false;
}

function doGetPassword(loginForm) {

	var request = new HttpRequest(WeGaunConstants.httpService+"/user/sendemail.html", "POST", {
		onRequestSuccess : function(responseText) {
			var resultArray = JSON.parse(responseText);
			if (resultArray[0]) {

			} else {
				switch (resultArray[1]) {
					case "file_not_exist" :
						$("message").innerHTML = "用户名错误！";
						break;
					case "emailAddress_invalid" :
						$("message").innerHTML = "email地址错误！";
						break;
					//case "emailAddress_invalid" :
					//	$("message").innerHTML = "email地址错误！";
					//	break;
					case "success_send" :
						$("message").innerHTML = "密码已发到您的邮箱，请注意查收！";
						break;
				}
			}

		},
		onRequestError : function(errorCode) {
		}
	});
	request.fillWithForm(loginForm);
	request.sendRequest();
	return false;
}