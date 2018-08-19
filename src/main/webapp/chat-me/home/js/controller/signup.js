angular.module('signupApp', [])
.controller('signupController', function signupController($scope, signupService) {
	$scope.signup = function(fullName, email, phone, userName, password) {
		signupService.signup(
			{
				"fullName" : fullName,
				"email" : email,
				"phone" : phone,
				"userName" : userName,
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
					url : '/signup',
					data : data
				}
			).then(
				function (response) {
					if(response.status == 200) {
						$window.location.href = '/chat-me/login';
					}
					else {
						console.log('error in signup!!!');
					}
				}
			);
		}
	}
}]);