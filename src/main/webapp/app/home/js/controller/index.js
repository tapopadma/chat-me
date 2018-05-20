angular.module('homeApp', [])
.controller('homeController', function homeController($scope, homeService){
	$scope.show = function() {
		homeService.show();
	};
	$scope.login = function(username, password) {
		homeService.login(username, password);
	};
	$scope.signup = function() {
		homeService.signup();
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