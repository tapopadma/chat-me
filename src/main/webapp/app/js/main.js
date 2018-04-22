angular.module('mainApp', [])
.controller('mainController',  
	function mainController($scope, $compile, $interval, $document, 
			mainService, userService, commonService, messengerService, socketService){
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
					$scope.selectedUser.status = 'online';
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
			socketService.send({
				'fromUsername' : $scope.username,
				'toUsername' : $scope.selectedUser.username,
				'message' : $scope.message,
				'isUsertyping': false
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
		$scope.sendUserTypingStatus = function (){
			socketService.send({
				'isUsertyping': true,
				'fromUsername' : $scope.username,
				'toUsername' : $scope.selectedUser.username
			});
		};
		$document.bind("keypress", function (event){
			if(event.target != null && event.target.getAttribute('id') != null 
					&& event.target.getAttribute('id') == 'chat-text-area'
					&& event.code != null && event.code == "Enter"){
				event.preventDefault();
				if($scope.message != null && $scope.message.length > 0){
					socketService.send({
						'fromUsername' : $scope.username,
						'toUsername' : $scope.selectedUser.username,
						'message' : $scope.message,
						'isUsertyping': false
					});
				}
			}
		});
})
.factory('socketService', ['$q', '$timeout', 'commonService', function($q, $timeout, commonService){
	var service = {};
	var listener = $q.defer();
	var socket = {
	    client: null,
	    stomp: null
	};
	var messageIds = [];
	    
    service.RECONNECT_TIMEOUT = 30000;
    service.SOCKET_URL = "/chat-me/chat";
    service.CHAT_TOPIC = "/topic/message";
    service.CHAT_BROKER = "/app/chat";
    
    service.receive = function() {
      return listener.promise;
    };
    
    service.send = function(message) {
      socket.stomp.send(
    		  service.CHAT_BROKER, 
    		  {},
    		  JSON.stringify(message)
      );
    };
    
    var reconnect = function() {
      $timeout(function() {
        initialize();
      }, this.RECONNECT_TIMEOUT);
    };
    
    var getMessage = function(data) {
      var messageInfo = JSON.parse(data);
      var scope = commonService.get('mainController');
      if(messageInfo.fromUsername != null && messageInfo.toUsername != null){
    	  if(messageInfo.message != null){
	    	  if(messageInfo.fromUsername == scope.username && 
	    			  messageInfo.toUsername == scope.selectedUser.username){
	    		  scope.addSenderMessageTemplateToChatBox(messageInfo.message);
	    	  }
	    	  else if(messageInfo.fromUsername == scope.selectedUser.username && 
	    			  messageInfo.toUsername == scope.username){
	    		  scope.addReceipientMessageTemplateToChatBox(messageInfo.message);
	    	  }
	    	  scope.scrollToEnd(document.getElementById('message-history'));
	    	  scope.message = '';
	    	  scope.$apply();
    	  }
    	  else if(messageInfo.isUsertyping){
        	  if(messageInfo.fromUsername == scope.selectedUser.username && 
        			  messageInfo.toUsername == scope.username){
        		  scope.selectedUser.status = 'typing...';
    			  scope.$apply();
        		  $timeout(function () {
        			  scope.selectedUser.status = 'online';
        			  scope.$apply();
        		  },1000);
        	  }
          }
    	  if(messageInfo.toUsername == scope.selectedUser.username){
    		  scope.selectedUser.status = messageInfo.isUserloggedin ? 'online' : 'offline';
    		  scope.$apply();
    	  }
      }
      else{
		  console.log('something wrong with message received: ' + message);
      }
    };
    
    var startListener = function() {
      socket.stomp.subscribe(service.CHAT_TOPIC, function(data) {
        listener.notify(getMessage(data.body));
      });
    };
    
    var initialize = function() {
      socket.client = new SockJS(service.SOCKET_URL);
      socket.stomp = Stomp.over(socket.client);
      socket.stomp.connect({}, startListener);
      socket.stomp.onclose = reconnect;
    };
    
    initialize();
    return service;
}])
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
					scope.messageHistoryList = response.data;
					scope.clearChatBox();
					scope.displayMessageHistoryOnChatBox();
					console.log('fetched message history data successfully!!!');
				}
				else{
					console.log('unable to fetch the data!!!');
				}
			});
		},
		saveMessage: function (data) {
			return;//polling no more useful
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