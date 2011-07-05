var Hi = new Class( {
	initialize : function() {
		this.hiArray = [["G'day", "澳大利亚语"], ["Kia ora", "毛利语"],
				["Hola", "西班牙语"], ["Mbote", "林加拉语"], ["Moyo", "西鲁巴语"],
				["Ahoy", "英语"], ["Ciao", "意大利语"], ["Olá", "葡萄牙语"],
				["Sawubona", "祖鲁语"], ["Merhaba", "土耳其语"],
				["Góðan daginn", "冰岛语"], ["Mingalaba", "缅甸语"],
				["Kumusta", "塔加拉族语"], ["Guten", "德语"], ["Guten Tag", "德语"],
				["Bonjour", "法语"], ["Szia", "匈牙利语"], ["Jambo", "斯华西里语"],
				["Shalom", "希伯来语"], ["Salaam", "阿拉伯语"], ["Hala", "阿拉伯语"],
				["Hoi", "荷兰语"], ["Bangawoyo", "韩语"], ["Yasou", "希腊语"],
				["Hej", "瑞典语"], ["Konnichiwa", "日语"], ["Namaste", "北印度语"],
				["Përshëndetje", "阿尔巴尼亚语"], ["Hello", "英语"], ["Oi", "英语"],
				["Hi", "英语"], ["'Allo", "英语"], ["Yo", "英语"]]
	},

	getHi : function() {
		return this.hiArray.getRandom();
	}
});