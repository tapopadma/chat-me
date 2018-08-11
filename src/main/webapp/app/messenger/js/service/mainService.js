angular.module('mainApp')
.factory('mainService', ['$http', function ($http){
	return {
		logout : function (){
			return $http({
                method: 'POST',
                url: '/chat-me/perform_logout'
            });
		} 
	};
}]);