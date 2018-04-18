angular.module('mainApp', [])
.controller('mainController',  
	function mainController($scope, $compile, $interval, mainService, userService, 
			commonService, messengerService){
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
			//saves to $scope.messageHistoryList
			messengerService.fetchAllMessage({
				'fromUsername' : $scope.username,
				'toUsername' : $scope.selectedUser.username
			});
		};
		$scope.displayMessageHistoryOnChatBox = function (){
			angular.forEach(this.messageHistoryList, function (message) {
				if(message.fromUsername == $scope.username){// current user is the sender
					$scope.addSenderMessageTemplateToChatBox(message.message);
				}
				else{// current user is the receiver
					$scope.addReceipientMessageTemplateToChatBox(message.message);
				}
			});
			$scope.scrollToEnd(document.getElementById('message-history'));
		};
		$scope.scrollToEnd = function (objDiv){
			objDiv.scrollTop = objDiv.scrollHeight;
		};
		$scope.clearChatBox = function () {
			angular.element(document.getElementById('message-history')).empty();
		};
		$scope.sendMessage = function () {
			messengerService.saveMessage({
				'fromUsername' : $scope.username,
				'toUsername' : $scope.selectedUser.username,
				'message' : $scope.message
			});
		};
		$scope.addSenderMessageTemplateToChatBox = function (text) {
			angular.element(document.getElementById('message-history'))
			.append($compile('<div style="color:green"><b>'+$scope.username+':</b> '+text+'</div>')($scope));
		};
		$scope.addReceipientMessageTemplateToChatBox = function (text) {
			angular.element(document.getElementById('message-history'))
				.append($compile(
						'<div style="color:red"><b>'+$scope.selectedUser.username
						+':</b>'+text+'</div>')($scope));
		};
})
.factory('messengerService', ['$http', 'commonService', function($http, commonService) {
	return {
		fetchAllMessage: function (data) {
			$http(
				{
					method : 'POST',
					url: '/chat-me/messenger/fetchAllMessage',
					data: data
				}
			)
			.then(function (response) {
				if(response.status == 200){
					var scope = commonService.get('mainController');
					if(scope.messageHistoryList != null && response.data.length == scope.messageHistoryList.length){						
						console.log('no update needed!!!');
						scope.selectUser(scope.selectedUser.username);
						return;
					}
					scope.messageHistoryList = response.data;
					scope.clearChatBox();
					scope.displayMessageHistoryOnChatBox();
					scope.selectUser(scope.selectedUser.username);
					console.log('fetched message history data successfully!!!');
				}
				else{
					console.log('unable to fetch the data!!!');
				}
			});
		},
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
					var scope = commonService.get('mainController'); 
					scope.selectUser(scope.selectedUser.username);
					scope.message = '';
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