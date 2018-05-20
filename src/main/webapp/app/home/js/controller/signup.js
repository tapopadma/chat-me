angular.module('signupApp', [])
.controller('signupController', function signupController($scope, signupService) {
	$scope.signup = function(name, email, phone, username, password) {
		signupService.signup(
			{
				"name" : name,
				"email" : email,
				"phone" : phone,
				"username" : username,
				"password" : password
			}
		);
	}
})
.factory('signupService', ['$http', '$window', function($http, $window){
	return {
		signup : function (data){
			$http(
				{
					method : 'POST',
					url : '/chat-me/signup',
					data : data
				}
			).then(
				function (response) {
					if(response.status == 200) {
						$window.location.href = '/chat-me/app/login';
					}
					else {
						console.log('error in signup!!!');
					}
				}
			);
		}
	}
}]);