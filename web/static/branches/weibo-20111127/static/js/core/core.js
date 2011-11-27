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

function show_btn(str){
	$("#"+str+"_btn").show();
	$("#"+str+"_btn_sending").hide();
}

function hide_btn(str){
	$("#"+str+"_btn").hide();
	$("#"+str+"_btn_sending").show();
}

