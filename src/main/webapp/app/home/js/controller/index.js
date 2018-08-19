angular.module('homeApp', [])
.controller('homeController', function homeController($scope, $http, homeService){
	$scope.authenticationFailed = false;
	$scope.show = function() {
		homeService.show();
	};
	$scope.login = function(username, password) {
		homeService.login(username, password);
	};
	$scope.signup = function() {
		homeService.signup();
	};
	$scope.init = function(){
		$scope.checkIfLoginAttempted();
		$http(
			{
				method: 'GET',
				url: '/chat-me/user/isUserLoggedInAlready'
			}
		).then(function(response){
			if(response.status == 200 && response.data == true){				
				console.log('already logged in!!!');
				window.location.href = '/chat-me/app/messenger/';
			}
		});
		$http(
			{
				method: 'GET',
				url: '/chat-me/user/getUniqueVistors'
			}
		).then(function(response){
			if(response.status == 200){
				$scope.uniqueVisitor = response.data.length;
			}
		});
	};
	$scope.checkIfLoginAttempted = function(){
		var url = window.location.href;
		$scope.authenticationFailed = url.endsWith('?error');
	};
})
.factory('homeService', ['$http', '$window', function ($http, $window){
	return {
		login : function (username, password){
			$http(
				{
					method: 'POST',
					url: '/chat-me/perform_login',
					data:{
						'username': username,
						'password': password,
						'submit': 'Login'
					}
				}		
			).then(function (response){
				console.log('logged in successfully!!!');
			});
		},
		signup : function (){
			$window.location.href = '/chat-me/app/signup.html';
		},
		show : function (){
			$http(
				{
					method: 'GET',
					url: "/chat-me/user"
			})
			.then(function (response) {
	            if (response.status == 200) {
	                window.location.reload();
                }
                else {
                    console.log("show failed!");
                }
			});
		},
		logout : function (){
			$http({
                method: 'POST',
                url: '/chat-me/perform_logout'
            })
            .then(function (response) {
                if (response.status == 200) {
                	window.location.reload();
                }
                else {
                    console.log("Logout failed!");
                }
            });
		} 
	};
}])
;