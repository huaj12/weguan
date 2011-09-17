function checkValLength(val, min, max){
	var length = getByteLen(val);
	if(length<min||length>max){
		return false;
	}
	return true;
}