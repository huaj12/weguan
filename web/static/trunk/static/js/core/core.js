// Inspired by base2 and Prototype
(function(){
	var initializing = false, fnTest = /xyz/.test(function(){xyz;}) ? /\b_super\b/ : /.*/;

	// The base Class implementation (does nothing)
	this.Class = function(){};

	// Create a new Class that inherits from this class
	Class.extend = function(prop) {
		var _super = this.prototype;
	
		// Instantiate a base class (but only create the instance,
		// don't run the init constructor)
		initializing = true;
		var prototype = new this();
		initializing = false;

		// Copy the properties over onto the new prototype
		for (var name in prop) {
			// Check if we're overwriting an existing function
			prototype[name] = typeof prop[name] == "function" &&
			typeof _super[name] == "function" && fnTest.test(prop[name]) ?
			(function(name, fn){
				return function() {
					var tmp = this._super;
					
					// Add a new ._super() method that is the same method
					// but on the super-class
					this._super = _super[name];
				
					// The method only need to be bound temporarily, so we
					// remove it when we're done executing
					var ret = fn.apply(this, arguments);
					this._super = tmp;
				
					return ret;
				};
			})(name, prop[name]) : prop[name];
		}

		// The dummy class constructor
		function Class() {
			// All construction is actually done in the init method
			if ( !initializing && this.init )
			this.init.apply(this, arguments);
		}
	
		// Populate our constructed prototype object
		Class.prototype = prototype;
	
		// Enforce the constructor to be what we expect
		Class.constructor = Class;
	
		// And make this class extendable
		Class.extend = arguments.callee;
	
		return Class;
	};
})();

function getByteLen(val) {
	if(!val){
		return 0;
	}
	var len = 0;
	for (var i = 0; i < val.length; i++) { 
		if (val.charAt(i).match(/[^\x00-\xff]/ig) != null) //全角 
			len += 2; 
		else 
			len += 1; 
	} 
	return len; 
}

function trimStr(str)  
{   
    if ((typeof(str) != "string") || !str)  
    {  
        return "";   
    }  
    return str.replace(/(^\s*)|(\s*$)/g, "");   
}

function closeAllDiv(){
	var list = $.dialog.list;
	for (var i in list) {
	    list[i].close();
	};
}

jQuery.cookie = function(name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        if (value === null) {
            value = '';
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
        }
        var path = options.path ? '; path=' + options.path : '';
        var domain = options.domain ? '; domain=' + options.domain : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
};

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
