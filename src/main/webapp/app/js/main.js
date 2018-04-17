angular.module('mainApp', [])
.controller('mainController',  
	function mainController($scope, mainService, userService, commonService, messengerService){
		commonService.set('mainController', $scope);
		$scope.logout = function() {
			mainService.logout();
		};
		$scope.init = function (){
			userService.getUsername();
			userService.getAllUsers();
		};
		$scope.selectUser = function (selectedUsername){
			angular.forEach(this.userList, function(user){
				if(user.username == selectedUsername){
					$scope.selectedUser = user;
				}
			});
			$scope.messageHistory = '';
		};
		$scope.sendMessage = function () {
			messengerService.saveMessage({
				'fromUsername' : $scope.username,
				'toUsername' : $scope.selectedUser.username,
				'message' : $scope.message
			});
			this.addSenderMessageTemplate($scope.message);
			$scope.message = '';
		};
		$scope.addSenderMessageTemplate = function (text) {
			var htmlContent = $('#message-history');
			htmlContent.load('<div style="color:green;">'+text+'</div>');
			$compile(htmlContent.contents())($scope);
		};
		$scope.addReceipientMessageTemplate = function (text) {
			
		};
})
.factory('messengerService', ['$http', 'commonService', function($http, commonService) {
	return {
		saveMessage: function (data) {
			$http(
				{
					method: 'POST',
					url: '/chat-me/messenger/saveMessage',
					data: data
				}
			)
			.then(function (response){
				if(response.status == 200){
					console.log('message sent successfully!!!');
				}
				else{
					console.log('something went wrong!!!');
				}
			});
		}
	};
}])
.factory('userService', ['$http', 'commonService', function ($http, commonService){
	return {
		getAllUsers : function () {
			$http(
				{
					method: 'GET',
					url: '/chat-me/user/getAllUsers'
				}
			)
			.then(function (response){
				if(response.status == 200){
					commonService.get('mainController').userList 
						=  response.data;
				}
			});
		},
		getUsername : function () {
			$http(
				{
					method: 'GET',
					url: '/chat-me/user/getLoggedInUser'
				}
			)
			.then(function (response){
				if(response.status == 200){
					commonService.get('mainController').username 
						=  response.data.username;
				}
			});
		}
	};
		
}])
.factory('mainService', ['$http', function ($http){
	return {
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

.factory('commonService', function() {
	var map = {};
	
	return {
		
		get : function (key){
			return map[key];
		},
		
		set : function(key, val){
			map[key] = val;
		}
	
	}
	
})

;