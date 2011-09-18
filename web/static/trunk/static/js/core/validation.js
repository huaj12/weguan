function checkValLength(val, min, max){
	var length = getByteLen(val);
	if(length<min||length>max){
		return false;
	}
	return true;
}

function checkEmail(email){
	var emailRegExp = new RegExp("^[_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*$");
	if (!email||email==""||email.indexOf('.')==-1||!emailRegExp.test(email)){
		return false;
	}else {
		return true;
	}
}