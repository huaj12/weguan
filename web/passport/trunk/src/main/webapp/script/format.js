var aa = "/reader/link-frame";
var ba = ba || {}, h = this;
function ca(a, b) {
	var c = a.split("."), d = h, e;
	!(c[0] in d) && d.execScript && d.execScript("var " + c[0]);
	while (c.length && (e = c.shift()))
		if (!c.length && i(b))
			d[e] = b;
		else
			d = d[e] ? d[e] : (d[e] = {})
}
var aa = "/reader/link-frame";
var ba = ba || {}, h = this;
function ca(a, b) {
	var c = a.split("."), d = h, e;
	!(c[0] in d) && d.execScript && d.execScript("var " + c[0]);
	while (c.length && (e = c.shift()))
		if (!c.length && i(b))
			d[e] = b;
		else
			d = d[e] ? d[e] : (d[e] = {})
}
function k(a) {
	var b = typeof a;
	if (b == "object")
		if (a) {
			if (typeof a.length == "number" && typeof a.splice != "undefined"
					&& !l(a, "length"))
				return "array";
			if (typeof a.call != "undefined")
				return "function"
		} else
			return "null";
	else if (b == "function" && typeof a.call == "undefined")
		return "object";
	return b
}
if (Object.prototype.propertyIsEnumerable) {
	var l = function(a, b) {
		return Object.prototype.propertyIsEnumerable.call(a, b)
	};
} else {
	l = function(a, b) {
		if (b in a)
			for (var c in a)
				if (c == b && Object.prototype.hasOwnProperty.call(a, b))
					return true;
		return false
	};
}
function i(a) {
	return typeof a != "undefined"
}
function n(a) {
	return k(a) == "array"
}
function p(a) {
	var b = k(a);
	return b == "array" || b == "object" && typeof a.length == "number"
}
function q(a) {
	return typeof a == "string"
}
function s(a, b) {
	var c = a.ha;
	if (arguments.length > 2) {
		var d = Array.prototype.slice.call(arguments, 2);
		c && d.unshift.apply(d, c);
		c = d
	}
	b = a.ja || b;
	a = a.ia || a;
	var e, g = b || h;
	e = c ? function() {
		var f = Array.prototype.slice.call(arguments);
		f.unshift.apply(f, c);
		return a.apply(g, f)
	} : function() {
		return a.apply(g, arguments)
	};
	e.ha = c;
	e.ja = b;
	e.ia = a;
	return e
}

function da(a, b) {
	ca(a, b)
}
function t(a, b) {
	function c() {
	}
	c.prototype = b.prototype;
	a.Oa = b.prototype;
	a.prototype = new c
}
function ea(a, b, c) {
	if (a.forEach)
		a.forEach(b, c);
	else if (Array.forEach)
		Array.forEach(a, b, c);
	else {
		var d = a.length, e = q(a) ? a.split("") : a;
		for (var g = 0;g < d; g++)
			g in e && b.call(c, e[g], g, a)
	}
}
function fa(a) {
	for (var b = 1;b < arguments.length; b++) {
		var c = arguments[b];
		n(c) ? a.push.apply(a, c) : a.push(c)
	}
}
var w;
function ga(a, b) {
	var c = a.length - b.length;
	return c >= 0 && a.lastIndexOf(b, c) == c
}
function y(a) {
	return a.replace(/^[\s\xa0]+|[\s\xa0]+$/g, "")
}
var ha = /^[a-zA-Z0-9\-_.!~*'()]*$/;
function z(a) {
	a = String(a);
	if (!ha.test(a))
		return encodeURIComponent(a);
	return a
}
function A(a) {
	return decodeURIComponent(a.replace(/\+/g, " "))
}
function B(a, b) {
	if (b)
		return a.replace(C, "&amp;").replace(D, "&lt;").replace(ia, "&gt;")
				.replace(ja, "&quot;");
	else {
		if (!ka.test(a))
			return a;
		if (a.indexOf("&") != -1)
			a = a.replace(C, "&amp;");
		if (a.indexOf("<") != -1)
			a = a.replace(D, "&lt;");
		if (a.indexOf(">") != -1)
			a = a.replace(ia, "&gt;");
		if (a.indexOf('"') != -1)
			a = a.replace(ja, "&quot;");
		return a
	}
}

var C = /&/g, D = /</g, ia = />/g, ja = /\"/g, ka = /[&<>\"]/;
function la(a) {
	if (a.indexOf("&") != -1)
		return "document" in h && !(a.indexOf("<") != -1) ? ma(a) : na(a);
	return a
}
function ma(a) {
	var b = h.document.createElement("a");
	b.innerHTML = a;
	b.normalize && b.normalize();
	a = b.firstChild.nodeValue;
	b.innerHTML = "";
	return a
}

function na(a) {
	return a.replace(/&([^;]+);/g, function(b, c) {
		switch (c) {
			case "amp" :
				return "&";
			case "lt" :
				return "<";
			case "gt" :
				return ">";
			case "quot" :
				return '"';
			default :
				if (c.charAt(0) == "#") {
					var d = Number("0" + c.substr(1));
					if (!isNaN(d))
						return String.fromCharCode(d)
				}
				return b
		}
	})
}
function oa(a, b, c) {
	if (c)
		a = la(a);
	if (a.length > b)
		a = a.substring(0, b - 3) + "...";
	if (c)
		a = B(a);
	return a
}
function pa(a, b) {
	var c = 0, d = y(String(a)).split("."), e = y(String(b)).split("."), g = Math
			.max(d.length, e.length);
	for (var f = 0;c == 0 && f < g; f++) {
		var j = d[f] || "", m = e[f] || "", u = new RegExp("(\\d*)(\\D*)", "g"), x = new RegExp(
				"(\\d*)(\\D*)", "g");
		do {
			var o = u.exec(j) || ["", "", ""], r = x.exec(m) || ["", "", ""];
			if (o[0].length == 0 && r[0].length == 0)
				break;
			var v = o[1].length == 0 ? 0 : parseInt(o[1], 10), Ca = r[1].length == 0
					? 0
					: parseInt(r[1], 10);
			c = E(v, Ca) || E(o[2].length == 0, r[2].length == 0)
					|| E(o[2], r[2])
		} while (c == 0)
	}
	return c
}

function E(a, b) {
	if (a < b)
		return -1;
	else if (a > b)
		return 1;
	return 0
}
(Date.now||function(){return(new Date).getTime()})();
if("StopIteration"in h)var F=h.StopIteration;
else F=Error("StopIteration");
function G(){}
G.prototype.next=function(){throw F;};
G.prototype.fa=function(){return this};
function qa(a){var b=[],c=0;for(var d in a)b[c++]=a[d];return b}
function ra(a){var b=[],c=0;for(var d in a)b[c++]=d;return b}
function sa(a){if(typeof a.p=="function")return a.p();if(q(a))return a.split("");if(p(a)){var b=[],c=a.length;for(var d=0;d<c;d++)b.push(a[d]);return b}return qa(a)}
function ta(a){if(typeof a.r=="function")return a.r();if(typeof a.p=="function")return undefined;
if(p(a)||q(a)){var b=[],c=a.length;for(var d=0;d<c;d++)b.push(d);return b}return ra(a)}
function ua(a,b,c){if(typeof a.forEach=="function")a.forEach(b,c);else if(p(a)||q(a))ea(a,b,c);else{var d=ta(a),e=sa(a),g=e.length;for(var f=0;f<g;f++)b.call(c,e[f],d&&d[f],a)}}
function H(a){this.e={};this.b=[];var b=arguments.length;if(b>1){if(b%2)throw Error("Uneven number of arguments");for(var c=0;c<b;c+=2)this.set(arguments[c],arguments[c+1])}else a&&this.ga(a)}
H.prototype.d=0;
H.prototype.C=0;
H.prototype.p=function(){this.D();var a=[];for(var b=0;b<this.b.length;b++)a.push(this.e[this.b[b]]);return a};
H.prototype.r=function(){this.D();return this.b.concat()};
H.prototype.i=function(a){return I(this.e,a)};
H.prototype.clear=function(){this.e={};this.b.length=0;this.d=0;this.C=0};
H.prototype.remove=function(a){if(I(this.e,a)){delete this.e[a];this.d--;this.C++;this.b.length>2*this.d&&this.D();return true}return false};
H.prototype.D=function(){if(this.d!=this.b.length){var a=0,b=0;while(a<this.b.length){var c=
this.b[a];if(I(this.e,c))this.b[b++]=c;a++}this.b.length=b}if(this.d!=this.b.length){var d={},a=0,b=0;while(a<this.b.length){var c=this.b[a];if(!I(d,c)){this.b[b++]=c;d[c]=1}a++}this.b.length=b}};

H.prototype.l=function(a,b){if(I(this.e,a))return this.e[a];return b};
H.prototype.set=function(a,b){if(!I(this.e,a)){this.d++;this.b.push(a);this.C++}this.e[a]=b};
H.prototype.ga=function(a){var b,c;if(a instanceof H){b=a.r();c=a.p()}else{b=ra(a);c=qa(a)}for(var d=0;d<b.length;d++)this.set(b[d],c[d])};
H.prototype.w=function(){return new H(this)};
H.prototype.fa=function(a){this.D();var b=0,c=this.b,d=this.e,e=this.C,g=this,f=new G;f.next=function(){while(true){if(e!=g.C)throw Error("The map has changed since the iterator was created");if(b>=c.length)throw F;var j=c[b++];return a?j:d[j]}};return f};

if(Object.prototype.hasOwnProperty)
var I=function(a,b){return Object.prototype.hasOwnProperty.call(a,b)};
else I=function(a,b){return b in a&&a[b]!==Object.prototype[b]};
function J(a,b){var c;if(a instanceof J){this.v(b==
null?a.f:b);this.Q(a.h);this.R(a.B);this.I(a.q);this.N(a.t);this.L(a.s);this.O(a.g.w());this.J(a.z)}else if(a&&(c=String(a).match(va()))){this.v(!!b);this.Q(c[1],true);this.R(c[2],true);this.I(c[3],true);this.N(c[4]);this.L(c[5],true);this.O(c[6]);this.J(c[7],true)}else{this.v(!!b);this.g=new K(null,this,this.f)}}
J.prototype.h="";
J.prototype.B="";
J.prototype.q="";
J.prototype.t=null;
J.prototype.s="";
J.prototype.z="";
J.prototype.va=false;
J.prototype.f=false;
J.prototype.toString=function(){if(this.c)return this.c;
var a=[];this.h&&a.push(L(this.h,wa),":");if(this.q){a.push("//");this.B&&a.push(L(this.B,wa),"@");a.push(xa(this.q));this.t!=null&&a.push(":",String(this.t))}this.s&&a.push(L(this.s,ya));var b=String(this.g);b&&a.push("?",b);this.z&&a.push("#",L(this.z,za));return this.c=a.join("")};
J.prototype.w=function(){return Aa(this.h,this.B,this.q,this.t,this.s,this.g.w(),this.z,this.f)};
J.prototype.Q=function(a,b){this.j();delete this.c;this.h=b?a?decodeURIComponent(a):"":a;if(this.h)this.h=this.h.replace(/:$/,
"");return this};
J.prototype.R=function(a,b){this.j();delete this.c;this.B=b?a?decodeURIComponent(a):"":a;return this};
J.prototype.I=function(a,b){this.j();delete this.c;this.q=b?a?decodeURIComponent(a):"":a;return this};
J.prototype.N=function(a){this.j();delete this.c;if(a){a=Number(a);if(isNaN(a)||a<0)throw Error("Bad port number "+a);this.t=a}else this.t=null;return this};
J.prototype.L=function(a,b){this.j();delete this.c;this.s=b?a?decodeURIComponent(a):"":a;return this};
J.prototype.O=function(a){this.j();
delete this.c;if(a instanceof K){this.g=a;this.g.S=this;this.g.v(this.f)}else this.g=new K(a,this,this.f);return this};
J.prototype.J=function(a,b){this.j();delete this.c;this.z=b?a?decodeURIComponent(a):"":a;return this};
J.prototype.j=function(){if(this.va)throw Error("Tried to modify a read-only Uri");};
J.prototype.v=function(a){this.f=a;this.g&&this.g.v(a)};
function Aa(a,b,c,d,e,g,f,j){var m=new J(null,j);a&&m.Q(a);b&&m.R(b);c&&m.I(c);d&&m.N(d);e&&m.L(e);g&&m.O(g);f&&m.J(f);return m}
function xa(a){if(q(a))return encodeURIComponent(a);
return null}

var Ba=/^[a-zA-Z0-9\-_.!~*'():\/;?]*$/;
function L(a,b){var c=null;if(q(a)){c=a;Ba.test(c)||(c=encodeURI(a));if(c.search(b)>=0)c=c.replace(b,Da)}return c}
function Da(a){var b=a.charCodeAt(0);return"%"+(b>>4&15).toString(16)+(b&15).toString(16)}
var M=null;
function va(){M||(M=/^(?:([^:\/?#]+):)?(?:\/\/(?:([^\/?#]*)@)?([^\/?#:@]*)(?::([0-9]+))?)?([^?#]+)?(?:\?([^#]*))?(?:#(.*))?$/);return M}
var wa=/[#\/\?@]/g,ya=/[\#\?]/g,za=/#/g;
function K(a,b,c){this.a=new H;this.S=b||null;this.f=!!c;if(a){var d=
a.split("&");for(var e=0;e<d.length;e++){var g=d[e].indexOf("="),f=null,j=null;if(g>=0){f=d[e].substring(0,g);j=d[e].substring(g+1)}else f=d[e];f=A(f);f=this.o(f);this.add(f,j?A(j):"")}}}
K.prototype.d=0;
K.prototype.add=function(a,b){this.A();a=this.o(a);if(this.i(a)){var c=this.a.l(a);n(c)?c.push(b):this.a.set(a,[c,b])}else this.a.set(a,b);this.d++;return this};
K.prototype.remove=function(a){a=this.o(a);if(this.a.i(a)){this.A();var b=this.a.l(a);if(n(b))this.d-=b.length;else this.d--;return this.a.remove(a)}return false};
K.prototype.clear=function(){this.A();this.a.clear();this.d=0};
K.prototype.i=function(a){a=this.o(a);return this.a.i(a)};
K.prototype.r=function(){var a=this.a.p(),b=this.a.r(),c=[];for(var d=0;d<b.length;d++){var e=a[d];if(n(e))for(var g=0;g<e.length;g++)c.push(b[d]);else c.push(b[d])}return c};
K.prototype.p=function(a){var b;if(a){var c=this.o(a);if(this.i(c)){var d=this.a.l(c);if(n(d))return d;else{b=[];b.push(d)}}else b=[]}else{var e=this.a.p();b=[];for(var g=0;g<e.length;g++){var f=e[g];n(f)?
fa(b,f):b.push(f)}}return b};
K.prototype.set=function(a,b){this.A();a=this.o(a);if(this.i(a)){var c=this.a.l(a);if(n(c))this.d-=c.length;else this.d--}this.a.set(a,b);this.d++;return this};
K.prototype.l=function(a,b){a=this.o(a);if(this.i(a)){var c=this.a.l(a);return n(c)?c[0]:c}else return b};
K.prototype.toString=function(){if(this.c)return this.c;var a=[],b=0,c=this.a.r();for(var d=0;d<c.length;d++){var e=c[d],g=z(e),f=this.a.l(e);if(n(f))for(var j=0;j<f.length;j++){b>0&&a.push("&");a.push(g,"=",
z(f[j]));b++}else{b>0&&a.push("&");a.push(g,"=",z(f));b++}}return this.c=a.join("")};
K.prototype.A=function(){delete this.c;this.S&&delete this.S.c};
K.prototype.w=function(){var a=new K;a.a=this.a.w();return a};
K.prototype.o=function(a){var b=String(a);if(this.f)b=b.toLowerCase();return b};
K.prototype.v=function(a){if(a&&!this.f){this.A();ua(this.a,function(b,c){var d=c.toLowerCase();if(c!=d){this.remove(c);this.add(d,b)}},this)}this.f=a};
var Ea,Fa,Ga,Ha,Ia,Ja,N,Ka,La,Ma,Na;

(function(){var a=false,
b=false,c=false,d=false,e=false,g=false,f=false,j=false,m=false,u="";if(h.navigator){var x=h.navigator,o=x.userAgent;a=o.indexOf("Opera")==0;b=!a&&o.indexOf("MSIE")!=-1;c=!a&&o.indexOf("WebKit")!=-1;m=c&&o.indexOf("Mobile")!=-1;d=!a&&!c&&x.product=="Gecko";e=d&&x.vendor=="Camino";var r,v;if(a)r=h.opera.version();else{if(d)v=/rv\:([^\);]+)(\)|;)/;else if(b)v=/MSIE\s+([^\);]+)(\)|;)/;else if(c)v=/WebKit\/(\S+)/;if(v){v.test(o);r=RegExp.$1}}u=x.platform||"";g=u.indexOf("Mac")!=-1;f=u.indexOf("Win")!=
-1;j=u.indexOf("Linux")!=-1}Ea=a;Fa=b;Ga=d;Ha=e;Ia=c;Ja=m;N=r;Ka=u;La=g;Ma=f;Na=j})();

var Oa=Ea,Pa=Fa,O=Ia;
function Qa(){w||(w=new P);return w}
function Q(a){return q(a)?document.getElementById(a):a}
function R(a){return Qa().createElement(a)}
function Ra(a,b){a.appendChild(b)}
var Sa=O&&pa(N,"521")<=0;
function Ta(a,b){if(typeof a.contains!="undefined"&&!Sa&&b.nodeType==1)return a==b||a.contains(b);if(typeof a.compareDocumentPosition!="undefined")return a==b||Boolean(a.compareDocumentPosition(b)&16);
while(b&&a!=b)b=b.parentNode;return b==a}
function P(a){this.la=a||h.document||document}
P.prototype.createElement=function(a){return this.la.createElement(a)};
P.prototype.appendChild=Ra;
P.prototype.contains=Ta;
function Ua(a,b,c){if(a.length!=b.length)return null;this.ka=c;this.Ba(a);this.Pa=b}
Ua.prototype.Ba=function(a){this.Na=a;for(var b=1;b<a.length;b++)if(a[b]==null)a[b]=a[b-1]+1;else if(this.ka)a[b]+=a[b-1]};
new Ua([0,10,1,2,1,18,95,33,13,1,594,112,275,7,263,45,1,1,1,2,1,2,1,1,56,4,12,11,48,20,
17,1,101,7,1,7,2,2,1,4,33,1,1,1,30,27,91,11,58,9,269,2,1,56,1,1,3,8,4,1,3,4,13,2,29,1,2,56,1,1,1,2,6,6,1,9,1,10,2,29,2,1,56,2,3,17,30,2,3,14,1,56,1,1,3,8,4,1,20,2,29,1,2,56,1,1,2,1,6,6,11,10,2,30,1,59,1,1,1,12,1,9,1,41,3,58,3,5,17,11,2,30,2,56,1,1,1,1,2,1,3,1,5,11,11,2,30,2,58,1,2,5,7,11,10,2,30,2,70,6,2,6,7,19,2,60,11,5,5,1,1,8,97,13,3,5,3,6,74,2,27,1,1,1,1,1,4,2,49,14,1,5,1,2,8,45,9,1,100,2,4,1,6,1,2,2,2,23,2,2,4,3,1,3,2,7,3,4,13,1,2,2,6,1,1,1,112,96,72,82,357,1,946,3,29,3,29,2,30,2,64,2,1,7,8,
1,2,11,9,1,45,3,155,1,118,3,4,2,9,1,6,3,116,17,7,2,77,2,3,228,4,1,47,1,1,5,1,1,5,1,2,38,9,12,2,1,30,1,4,2,2,1,121,8,8,2,2,392,64,523,1,2,2,24,7,49,16,96,33,3311,32,554,6,105,2,30164,4,9,2,388,1,3,1,4,1,23,2,2,1,88,2,50,16,1,97,8,25,11,2,213,6,2,2,2,2,12,1,8,1,1,434,11172,9082,1,737,16,16,7,216,1,158,2,89,3,513,1,2051,15,40,8,50981,1,1,3,3,1,5,8,8,2,7,30,4,148,3,798140,255],[1,11,1,10,1,0,1,0,1,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,1,0,2,0,2,0,2,0,2,1,2,0,2,0,2,0,1,0,2,0,2,0,2,0,2,0,2,4,0,2,0,4,2,4,2,0,2,
0,2,0,2,4,0,2,0,2,4,2,4,2,0,2,0,2,0,2,4,0,2,4,2,0,2,0,2,4,0,2,0,4,2,4,2,0,2,0,2,4,0,2,0,2,4,2,4,2,0,2,0,2,0,2,4,2,4,2,0,2,0,4,0,2,4,2,0,2,0,4,0,2,0,4,2,4,2,4,2,4,2,0,2,0,4,0,2,4,2,4,2,0,2,0,4,0,2,4,2,4,2,4,0,2,0,3,2,0,2,0,2,0,3,0,2,0,2,0,2,0,2,0,2,0,4,0,2,4,2,0,2,0,2,0,2,0,4,2,4,2,4,2,4,2,0,4,2,0,2,0,4,0,4,0,2,0,2,4,2,4,2,0,4,0,5,6,7,0,2,0,2,0,2,0,2,0,2,0,1,4,2,4,2,4,2,0,2,0,2,0,2,0,2,4,2,4,2,4,2,0,4,0,4,0,2,4,0,2,4,0,2,4,2,4,2,4,2,4,0,2,0,2,4,0,4,2,4,2,4,0,4,2,4,2,0,2,0,1,2,1,0,1,0,1,0,2,0,2,0,2,
0,2,0,2,0,2,0,2,0,2,0,2,0,4,2,4,0,4,0,4,2,0,2,0,2,4,0,2,4,2,4,2,0,2,0,2,4,0,9,0,2,0,2,0,2,0,1,0,2,0,1,0,2,0,2,0,2,0,2,4,2,0,4,2,1,2,0,2,0,2,0,2,0,1,2],true);

({"":1,n:Math.pow(1024,-3),u:Math.pow(1024,-2),m:9.765625E-4,k:1024,K:1024,M:Math.pow(1024,2),G:Math.pow(1024,3),T:Math.pow(1024,4),P:Math.pow(1024,5)});

var Va;
(function(){var a=false;if("ScriptEngine"in h){a=h.ScriptEngine()=="JScript";a&&h.ScriptEngineMajorVersion()+"."+h.ScriptEngineMinorVersion()+"."+h.ScriptEngineBuildVersion()}Va=a})();
function Wa(){this.Ea=document.all?true:false;this.Ja="pop"in Array.prototype;this.Ga="contains"in document;this.Ha="implementation"in document&&"createDocument"in document.implementation;this.Fa="compatMode"in document;this.Ia="XMLHttpRequest"in window;var a=navigator.userAgent,b=/\(.*\) AppleWebKit\/(.*) \((.*)/.exec(a);if(b){this.X=true;this.Qa=parseInt(b[1],10)}else this.X=false;this.La=window.opera;this.Ma=a.indexOf("Wii")!=-1;this.Ka=this.X&&a.indexOf("Chrome")!=-1}
new Wa;
function Xa(a,b,c){a+=
a.indexOf("?")==-1?"?":"&";return a+z(b)+"="+z(c)}
function Ya(a){if(!a)return"";if(a.indexOf("x-")==0)a=a.substring(2);var b=a.split("/");return y(b[0]).toLowerCase()}
function Za(){var a={};if(!window||!window.location||!window.location.href)return a;var b=window.location.href.split("#")[1];if(!b)return a;var c=b.split("&");for(var d=0,e;e=c[d];d++){var g=e.indexOf("=");a[e.substring(0,g)]=e.substring(g+1)}return a}
function $a(a,b,c){var d=b.split("#")[0]+c;ab(a,d)}
function ab(a,b){try{a.location.replace(b)}catch(c){a.location=
b}}
function bb(a){try{return i(a._USER_ID)&&i(a._USER_EMAIL)}catch(b){return false}}
function _finishSignIn(){$a(top,_OPENER_URL,"#refresh=1")}
function S(){}
S.prototype.F=function(){return null};
function T(){S.call(this)}
t(T,S);
T.prototype.ra=function(){if(document.selection&&document.selection.createRange)return document.selection.createRange().text?document.selection.createRange().htmlText:"";else if(window.getSelection){var a=window.getSelection();if(a.rangeCount>0){var b=R("div");b.appendChild(a.getRangeAt(0).cloneContents());
return b.innerHTML}}return""};
T.prototype.F=function(){return this.ra().replace(/[\r|\n]/g,"")};
function U(){S.call(this)}
t(U,S);
U.prototype.F=function(){var a=document.getElementsByTagName("meta");for(var b=0,c;c=a[b];b++){var d=c.getAttribute("name");if(d&&d.toUpperCase()=="DESCRIPTION")return c.getAttribute("content")}return null};
function V(){S.call(this)}
t(V,S);
V.prototype.F=function(){var a=new J(window.location.href);if(!ga(a.q,"youtube.com"))return null;if("/watch"!=a.s)return null;var b=
document.getElementById("embed_code");if(b)return b.value;return null};
var cb=[new V,new T,new U,new S];
function db(){var a=null;for(var b=0,c;c=cb[b];b++){a=c.F();if(a)return a}return""}
var W="GR________link_bookmarklet_node",X="GR________link_bookmarklet_frame";
function eb(a,b,c){if(a[b]){$a(window,window.location.href,"#");c()}}
function fb(){return(window.GR________bookmarklet_domain||window.location.protocol+"//"+window.location.host)+aa}
function gb(){var a=new Y;a.aa(document.title);a.ba(window.location.href);
a.Y(db());a.Z(window.location.host);a.$(window.location.protocol+"//"+window.location.host+"/");return a}
function hb(){if(!document)return false;var a=document.contentType;if(a&&Ya(a)=="image")return true;if(document.body&&document.body.childNodes.length==2&&document.body.firstChild.tagName&&document.body.firstChild.tagName.toLowerCase()=="img")return true;return false}
function ib(){var a=new Y,b=document.location.pathname.split("/");a.aa(b[b.length-1]);var c=document.location.href;a.ba(c);a.Y('<img src="'+
c+'">');a.Z(window.location.host);a.$(window.location.protocol+"//"+window.location.host+"/");return a}
var Z=null;
function $(a,b){this.H=a;this.ea=b}
$.prototype.clear=function(a){window.clearInterval(this.ua);if(this.H)return;var b=Q(W);b.innerHTML="";a&&b.parentNode.removeChild(b);Z=null};
$.prototype.qa=function(){if(O)return window.frames[X];var a=Q(X);return a?a.contentWindow:null};
$.prototype.V=function(){return this.qa()?true:false};
$.prototype.ma=function(){if(!this.H&&!this.V())return;var a=
Za(),b=this;eb(a,"refresh",function(){b.clear();b.W(gb())});eb(a,"close",s(b.clear,b,true))};
$.prototype.W=function(a){this.H?this.ta(a):this.sa(a);this.ua=window.setInterval(s(this.ma,this),50)};
$.prototype.ta=function(a){if(!window.open(this.ea?this.pa(a):"",X,"height=378,width=520")){alert("Grrr! A popup blocker may be preventing Google Reader from opening the page. If you have a popup blocker, try disabling it to open the window.");return}this.ea||this.da(a)};
$.prototype.sa=function(a){if(this.V())return;
Q(W).innerHTML='<iframe frameborder="0" id="'+X+'" name="'+X+'" style="width:100%;height:100%;border:0px;padding:0px;margin:0px"></iframe>';this.da(a)};
$.prototype.da=function(a){var b=a.na(fb(),X);document.body.appendChild(b);b.submit()};
$.prototype.pa=function(a){return a.oa(fb())};
function Y(){}
Y.prototype.aa=function(a){this.Ca=a};
Y.prototype.ba=function(a){this.Da=a};
Y.prototype.Y=function(a){this.xa=a};
Y.prototype.Z=function(a){this.za=a};
Y.prototype.$=function(a){this.Aa=a};
Y.prototype.U=function(a){if(this.ya)a("srcItemId",
this.ya);else{a("title",this.Ca);a("url",this.Da);a("srcTitle",this.za);a("srcUrl",this.Aa);a("snippet",oa(this.xa,100000))}};
Y.prototype.na=function(a,b){var c=R("form");c.method="POST";c.target=b;c.action=a;c.acceptCharset="utf-8";var d=[];function e(g,f){if(!f)return;d.push('<input type="hidden" name="'+B(g)+'" value="'+B(f)+'">')}this.U(e);c.innerHTML=d.join("");return c};
Y.prototype.oa=function(a){var b=a;this.U(function(c,d){if(d)b=Xa(b,c,d)});return b};
Y.prototype.ca=function(a,b){var c=a||
Pa||Oa;c||this.wa();Z=new $(c,b);Z.W(this)};
Y.prototype.wa=function(){document.body.scrollTop=document.documentElement.scrollTop=0;var a=Q(W);if(!a){a=R("div");a.id=W;a.style.position=Pa&&pa(N,6)==0?"absolute":"fixed";a.style.background="#fff";a.style.border="4px solid #c3d9ff";a.style.top="8px";a.style.right="8px";a.style.width="520px";a.style.height="378px";a.style.zIndex=100000;document.body.appendChild(a)}};
da("removeLinkFrame",function(){Z.clear(true)});
bb(window)||(hb()?ib().ca(O?false:true,
O?false:true):gb().ca());