var shareBoxVisible = false;
var shareBoxInit = false;
var shareBoxEffect = null;
var shareBoxTimeout = 0;
function startShare(btn) {
	var shareBox = $('ShareBox');
	if (!shareBoxInit) {
		document.addEvent('mouseup', function() {
			if (shareBoxVisible)
				startShare(btn);
		});
		shareBox.style.display = 'block';
		shareBox.setOpacity(0);
		shareBoxInit = true;
		shareBoxEffect = new Fx.Styles($('ShareBox'), {
			duration : 300,
			transition : Fx.Transitions.linear,
			onComplete : function() {
				shareBoxVisible = !shareBoxVisible;
			}
		});
		shareBox.onmouseover = function() {
			clearTimeout(shareBoxTimeout);
			shareBoxTimeout = 0;
		}
		btn.onmouseout = shareBox.onmouseout = function() {
			if (!shareBoxTimeout)
				shareBoxTimeout = setTimeout(function() {
					if (shareBoxVisible)
						startShare(btn);
				}, 2000);
		}
	}
	shareBoxEffect.start( {
		opacity : shareBoxVisible ? 0 : 1
	});
}
function startShareStuff(type) {
	var csi = new ClientSideInclude(WeGaunConstants.httpService+'/user/getshareform.html?type=' + type,
			'StuffFormPanel', 'GET', {
				doAfterRequest : function() {
					$('stuffForm').elements[0].focus();
					if (document.body.scrollIntoView) {
						document.body.scrollIntoView();
					} else {
						document.body.firstChild.scrollIntoView();
					}
				}
			});
	csi.csi();
}
function editStuff(type, poin, pageType) {
	if (type.indexOf('image') < 0)
		$('StuffContent' + poin).hide();
	if (type == 'image')
		type = 'uploadimage';
	$('StuffEdit' + poin).show();
	var csi = new ClientSideInclude(WeGaunConstants.httpService+'/user/geteditform.html?action=edit&type='
			+ type + '&poin=' + poin + '&pageType=' + pageType, 'StuffEdit'
			+ poin, 'GET', {
		doAfterRequest : function() {
			setTimeout(function() {
				$('stuffEditForm' + poin).elements[0].select();
			}, 10);
			return null;
		}
	});
	csi.csi();
}
function cancelEditStuff(poin) {
	$('StuffEdit' + poin).hide();
	$('StuffContent' + poin).show();
}
function refreshStuffs() {
	$("StuffFormPanel").empty();
	stuffPager.refreshCurrentPage();
}
var errorMap = {
	'file_not_exist' : '文件不存在',
	'invalid_type' : '文件类型不支持',
	'invalid_typename' : '无效的请求',
	'not_login' : '你还没有登录，请先登录',
	'invalid_size' : '文件不能大于15兆',
	'invalid_format' : '服务器故障，请稍候再试',
	'image_not_exist' : '原数据不存在',
	'imageAttachment_not_exist' : '原文件不存在',
	'invalid_poin' : '数据不存在',
	'repost_error' : '转贴时发生错误，请稍候再试',
	'delete_failure' : '删除失败，请稍候再试'
}
function returnInfo(iframObj) {
	var responseText = iframObj.contentWindow.document.body.innerHTML;
	if (responseText == "") {
		return;
	}
	var result = JSON.parse(responseText);
	if (result[0]) {
		refreshStuffs();
	} else {
		alert(errorMap[result[1]]);
	}

	iframObj.contentWindow.document.body.innerHTML = "";
}
function shareTextLikeStuff(type, formObj) {
	var request = new HttpRequest(formObj.action, 'POST', {
		onRequestSuccess : function(responseText) {
			var result = Json.evaluate(responseText);
			if (result[0]) {
				refreshStuffs();
			} else {
				alert(errorMap[result[1]]);
			}
		},
		onRequestError : function(code) {
			alert('请稍候再试[' + code + ']');
		}
	});
	request.fillWithForm(formObj);
	request.addParameter('type', type);
	request.sendRequest();
}
function deleteStuff(type, poin, pageType) {
	if (!confirm("确认删除？"))
		return;
	var request = new HttpRequest(WeGaunConstants.httpService+'/user/deletestuff.html', 'POST', {
		onRequestSuccess : function(responseText) {
			var result = Json.evaluate(responseText);
			if (result[0]) {
				if (pageType == "singlePage") {
					window.location.href = "/user/index.do";
				} else if (pageType == 'homepage') {
					$('adminDeleteSpan'+poin).innerHTML = '删除成功';
					$('adminDeleteSpan'+poin).onclick = '';
				} else {
					refreshStuffs();
				}
			} else {
				alert(errorMap[result[1]]);
			}
		},
		onRequestError : function(code) {
			alert('请稍候再试[' + code + ']');
		}
	});
	request.addParameter('type', type);
	request.addParameter('poin', poin);
	request.sendRequest();
}
function repostStuff(poin, a) {
	var oc = a.onclick;
	var oh = a.innerHTML;
	a.innerHTML = '请稍候...';
	a.onclick = '';
	var request = new HttpRequest(WeGaunConstants.httpService+'/user/repoststuff.html', 'POST', {
		onRequestSuccess : function(responseText) {
			var result = Json.evaluate(responseText);
			if (result[0]) {
				a.innerHTML = '转贴成功';
			} else {
				alert(errorMap[result[1]]);
				a.innerHTML = oh;
				a.onclick = oc;
			}
		},
		onRequestError : function(code) {
			alert('请稍候再试[' + code + ']');
			a.innerHTML = oh;
			a.onclick = oc;
		}
	});
	request.addParameter('stuffPoin', poin);
	request.sendRequest();
}
