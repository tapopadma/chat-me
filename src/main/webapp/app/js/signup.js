angular.module('signupApp', [])
.controller('signupController', function signupController($scope, signupService) {
	$scope.signup = function() {
		signupService.signup(
			{
				"name" : $scope.name,
				"email" : $scope.email,
				"phone" : $scope.phone,
				"username" : $scope.username,
				"password" : $scope.password
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