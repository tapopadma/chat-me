var __commonService = function() {
	var map = {};
	
	return {
		
		get : function (key){
			return map[key];
		},
		
		set : function(key, val){
			map[key] = val;
		}
	
	}
	
};