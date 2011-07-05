/*--修改个人资料--------------------------------------------------------------------------*/
var profileBoxVisible = false;
var profileBoxInit = false;
var profileBoxEffect = null;
function showModifyProfileDialog(divId) {
	var div = $(divId);
	if (!profileBoxInit) {
		div.style.display = 'block';
		div.setOpacity(0);
		profileBoxEffect = new Fx.Styles(div, {
			duration : 300,
			transition : Fx.Transitions.linear,
			onComplete : function() {
				profileBoxVisible = !profileBoxVisible;
			}
		});
		profileBoxInit = true;
	}
	profileBoxEffect.start( {
		opacity : profileBoxVisible ? 0 : 1
	});
}

function doSelect(selectForm) {
	var value = selectForm.value;
	//$('cssPublic').href = '/style/user/template' + value + '/public.css';
	$('cssskin').href = WeGaunConstants.staticService+'/style/user/skin' + value + '.css';
}

function checkData(profileForm) {
	var password = profileForm.password.value;
	var pwdConf = profileForm.pwdConf.value;
	if (password != pwdConf) {
		$("error").style.display = 'block';
		return false;
	}

	return true;
}
function cancelModify(divId, styleTemplate) {
	var div = $(divId);
	showModifyProfileDialog(divId);
	//$('cssPublic').href = '/style/user/template' + styleTemplate
	//		+ '/public.css';
	$('cssskin').href = WeGaunConstants.staticService+'/style/user/skin' + styleTemplate + '.css';
}

function returnProfileInfo(iframObj) {
	var responseText = iframObj.contentWindow.document.body.innerHTML;
	if (responseText == "") {
		return;
	}
	var result = JSON.parse(responseText);
	if (result[0]) {
		window.location = result[1];
	} else {
		switch (result[1]) {
			case "invalid_type" :
				$("avatarTypeError").style.display = "inline";
				break;
			case "invalid_size" :
				$("avatarSizeError").style.display = "inline";
				break;
			case "email_existed" :
				$("duplicateEmailError").style.display = "inline";
				break;
			case "email_invalid" :
				$("invalidEmailError").style.display = "inline";
				break;
			case "password_invalid" :
				$("invalidPasswordError").style.display = "inline";
				break;
		}
	}

	iframObj.contentWindow.document.body.innerHTML = "";
}