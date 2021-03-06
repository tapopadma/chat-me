angular.module('mainApp').factory('userService', ['$http', function ($http){
	var service = {};
    service.getAllLoggedInUsers = function () {
		$http(
			{
				method: 'GET',
				url: '/messenger/getAllLoggedInUsers'
			}
		)
		.then(function (response){
			if(response.status == 200){
				alert(response.data);
			}
		});
	};
	service.getFriendListByUser = function (user) {
		return $http(
			{
				method: 'GET',
				url: '/user/getAllUsers'
			}
		);
	};
	service.getUser = function () {
		return $http(
			{
				method: 'GET',
				url: '/user/getLoggedInUser'
			}
		);
	};
	return service;
}]);