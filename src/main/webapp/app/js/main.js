angular.module('mainApp', [])
.controller('mainController',  
	function mainController($scope, $compile, $interval, $document, 
			mainService, userService, commonService, messengerService, socketService){
		commonService.set('mainController', $scope);
		$scope.ONLINE_COLOR = 'blue';
		$scope.OFFLINE_COLOR = 'grey';
		$scope.TYPING_COLOR = 'black';
		$scope.logout = function() {
			socketService.send({
				'sessioninfoEntity':{
					'username' : $scope.username,
					'islogoutRequest' : true
				 }
			});
			mainService.logout();
		};
		$scope.init = function (){
			userService.getUsername();
			userService.getAllUsers();
			//userService.getAllLoggedInUsers();
			channelService.getAllChannels();
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
			socketService.send({
				'sessioninfoEntity':{
					'username' : $scope.username,
					'isloginRequest' : true
				 }
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
				'messageinfoEntity':{
					'fromUsername' : $scope.username,
					'toUsername' : $scope.selectedUser.username,
					'message' : $scope.message,
					'isUsertyping': false
				}
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
				'messageinfoEntity':{
					'isUsertyping': true,
					'fromUsername' : $scope.username,
					'toUsername' : $scope.selectedUser.username
				 }
			});
		};
		$document.bind("keypress", function (event){
			if(event.target != null && event.target.getAttribute('id') != null 
					&& event.target.getAttribute('id') == 'chat-text-area'
					&& event.code != null && event.code == "Enter"){
				event.preventDefault();
				if($scope.message != null && $scope.message.length > 0){
					socketService.send({
						'messageinfoEntity':{
							'fromUsername' : $scope.username,
							'toUsername' : $scope.selectedUser.username,
							'message' : $scope.message,
							'isUsertyping': false
						 }
					});
				}
			}
		});
		$scope.setUserActiveStatus = function (value) {
			switch (value){
			case 'online':
				$scope.selectedUser.USER_STATUS_COLOR = $scope.ONLINE_COLOR;
				break;
			case 'offline':
				$scope.selectedUser.USER_STATUS_COLOR = $scope.OFFLINE_COLOR;
				break;
			case 'typing...':
				$scope.selectedUser.USER_STATUS_COLOR = $scope.TYPING_COLOR;
				break;
			default:
				break;
			};
			$scope.selectedUser.status = value;
			$scope.$apply();
		};
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
      var socketMessageInfo = JSON.parse(data);
      var messageInfo = socketMessageInfo.messageinfoEntity;
      var sessionInfo = socketMessageInfo.sessioninfoEntity;
      var scope = commonService.get('mainController');
      if(messageInfo != null){
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
            		  scope.setUserActiveStatus('typing...');
            		  $timeout(function () {
            			  scope.setUserActiveStatus('online');
            		  },1000);
            	  }
              }
          }
          else{
    		  console.log('something wrong with message received: ' + message);
          }
      }
      if(sessionInfo != null){
    	  if(sessionInfo.username == scope.selectedUser.username){
    		  if(sessionInfo.isloginRequest){
    			  scope.setUserActiveStatus('online');
    		  }
    		  else if(sessionInfo.islogoutRequest){
    			  scope.setUserActiveStatus('offline');
    		  }
    	  }
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
		getAllLoggedInUsers : function () {
			$http(
				{
					method: 'GET',
					url: '/chat-me/messenger/getAllLoggedInUsers'
				}
			)
			.then(function (response){
				if(response.status == 200){
					alert(response.data);
				}
			});
		},
		getAllUsers : function () {
			$http(
				{
					method: 'GET',
					url: '/chat-me/user/getAllUsers'
				}
			)
			.then(function (response){
				if(response.status == 200){
					var scope = commonService.get('mainController');
					var responseData = response.data;
					var userList = [];
					angular.forEach(responseData, function(data){
						var obj = data.accountMstDto;
						obj.isloggedIn = data.loggedIn;
						obj.status = obj.isloggedIn ? 'online' : 'offline';
						obj.USER_STATUS_COLOR = 
							obj.isloggedIn ? scope.ONLINE_COLOR : scope.OFFLINE_COLOR;
						userList.push(obj);
					});
					scope.userList = userList;
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
	
});