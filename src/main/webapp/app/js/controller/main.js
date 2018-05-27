angular.module('mainApp', [])
.controller('mainController',  
	function mainController($scope, $compile, $interval, $document, 
			mainService, userService, commonService, messengerService, 
			socketService, channelService){
	
		commonService.set('mainController', $scope);
		$scope.NONE_SELECTED = 'none';
		$scope.USER_SELECTED = 'user';
		$scope.CHANNEL_SELECTED = 'channel';
		$scope.chatType = $scope.NONE_SELECTED;
		$scope.ONLINE_COLOR = 'blue';
		$scope.ONLINE_CAPTION = 'online';
		$scope.OFFLINE_COLOR = 'grey';
		$scope.OFFLINE_CAPTION = 'offline';
		$scope.TYPING_COLOR = 'black';
		$scope.TYPING_CAPTION = 'typing...';
		$scope.DIRECT_MODE = 'DIRECT';
		$scope.CHANNEL_MODE = 'CHANNEL';
		
		$scope.logout = function() {
			socketService.send({
				'userSessionInfoEntity':{
					'userId' : $scope.user.userId,
					'logoutRequest' : true
				 }
			});
			mainService.logout();
		};
		$scope.init = function (){
			userService.getUser();
		};
		$scope.selectChannel = function (channel){
			$scope.messageIdtoEntityMap = {};
			$scope.messageHistoryList = [];
			$scope.messageMode = $scope.CHANNEL_MODE;
			$scope.chatType = $scope.CHANNEL_SELECTED;
			$scope.selectedUser = {'username':channel};
			messengerService.fetchAllChannelMessage({
				'username' : $scope.username,
				'channelName' : channel
			});
		};
		$scope.selectUser = function (selectedUser){
			$scope.messageIdtoEntityMap = {};
			$scope.messageHistoryList = [];
			$scope.messageMode = $scope.DIRECT_MODE;
			$scope.chatType = $scope.USER_SELECTED;
			angular.forEach(this.userList, function(user){
				if(user.userId == selectedUser.userId){
					$scope.selectedUser = user;
				}
			});
			
			socketService.send({
				'userSessionInfoEntity':{
					'userId' : $scope.user.userId,
					'loginRequest' : true
				 }
			});
			
			//saves to $scope.messageHistoryList
			messengerService.fetchAllMessageBySourceAndDest({
				'sourceId' : $scope.user.userId,
				'destinationId' : $scope.selectedUser.userId
			});
		};
		$scope.displayMessageHistoryOnChatBox = function (){
			angular.forEach(this.messageHistoryList, function (messageTrnDto) {
				if(messageTrnDto.sourceId == $scope.user.userId){
					// current user is the sender
					if(messageTrnDto.destinationId == $scope.selectedUser.userId){
						$scope.messageIdtoEntityMap[messageTrnDto.messageId] = messageTrnDto;
						$scope.pushSenderMessageToChatBox(messageTrnDto);
					}
				}
				else if(messageTrnDto.destinationId == $scope.user.userId){
					// current user is the receiver
					if(messageTrnDto.sourceId == $scope.selectedUser.userId){
						$scope.messageIdtoEntityMap[messageTrnDto.messageId] = messageTrnDto;
						$scope.pushRecieverMessageToChatBox(messageTrnDto);
					}
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
			$scope.sendMessageOverSocket();
		};
		$scope.sendMessageOverSocket = function () {
			var data = {};
			data.messageTrnInfoEntity = {
				'messageTrnDtoList':[{
					'message' : $scope.message,
					'sourceId' : $scope.user.userId,
					'destinationId' : $scope.selectedUser.userId,
					'messageMode' : $scope.messageMode
				}]
			};
			socketService.send(data);																																																						
		};
		$scope.pushSenderMessageToChatBox = function(messageTrnDto){
			angular.element(document.getElementById('message-history'))
			.append($compile('<div id="'+ messageTrnDto.messageId+'" style="color:green"><b>'+
					$scope.user.userName+':</b> '+messageTrnDto.message+
					'-{{messageIdtoEntityMap["'+messageTrnDto.messageId+
					'"].messageDeliveryStatus}}</div>')($scope));
		}
		$scope.addSenderMessageTemplateToChatBox = function (messageTrnDto) {
			var exists = $scope.messageIdtoEntityMap.hasOwnProperty(messageTrnDto.messageId);
			$scope.messageIdtoEntityMap[messageTrnDto.messageId] = messageTrnDto;
			if(!exists){
				$scope.pushSenderMessageToChatBox(messageTrnDto);
			}
			$scope.scrollToEnd(document.getElementById('message-history'));
	    	$scope.message = '';
	    	$scope.$apply();
		};
		$scope.pushRecieverMessageToChatBox = function (messageTrnDto){
			angular.element(document.getElementById('message-history'))
			.append($compile(
					'<div id="' +messageTrnDto.messageId+'" style="color:red"><b>'
					+$scope.selectedUser.userName
					+':</b>'+messageTrnDto.message+'</div>')($scope));
		};
		$scope.addReceipientMessageTemplateToChatBox = function (messageTrnDto) {
			var exists = $scope.messageIdtoEntityMap.hasOwnProperty(messageTrnDto.messageId);
			$scope.messageIdtoEntityMap[messageTrnDto.messageId] = messageTrnDto;
			if(!exists){
				$scope.pushRecieverMessageToChatBox(messageTrnDto);
			}
			$scope.scrollToEnd(document.getElementById('message-history'));
	    	$scope.message = '';
	    	$scope.$apply();
			if(messageTrnDto.messageDeliveryStatus != 'READ'){
				$scope.resetUnReadMessageCounterByUserId($scope.selectedUser.userId);
				$scope.notifyMessageAsRead([messageTrnDto]);
			}
		};
		$scope.resetUnReadMessageCounterByUserId = function(userId){
			var userListCopy = $scope.userList;
			$scope.userList = [];
			angular.forEach(userListCopy, function(user){
				if(user.userId == userId){
					user.unReadMessageCounter = 0;
				}
				$scope.userList.push(user);
			});
			$scope.$apply();
		};
		$scope.getAllNotReadMessageTrns = function(){
			var messageTrnDtoList = [];
			angular.forEach($scope.messageHistoryList, function(messageTrnDto){
				if(messageTrnDto.sourceId == $scope.selectedUser.userId &&
					messageTrnDto.messageDeliveryStatus != 'READ'){
					messageTrnDtoList.push(messageTrnDto);
				}
			});
			return messageTrnDtoList;
		};
		$scope.notifyMessageAsRead = function (messageTrnDtoList){
			var data = {};
			data.messageTrnInfoEntity = {
				'markAllMessageAsRead' : true,
				'messageTrnDtoList'	: messageTrnDtoList
			};
			socketService.send(data);
		};
		$scope.sendUserTypingStatus = function (){
			var data = {};
			data.userTyping = true;
			data.messageTrnDto = {
				'sourceId' : $scope.user.userId,
				'destinationId' : $scope.selectedUser.userId
			};
			socketService.send({
				'messageMiscellaneousInfoEntity' : data
			});
		};
		$document.bind("keypress", function (event){
			if(event.target != null && event.target.getAttribute('id') != null 
					&& event.target.getAttribute('id') == 'chat-text-area'
					&& event.code != null && event.code == "Enter"){
				event.preventDefault();
				if($scope.message != null && $scope.message.length > 0){
					$scope.sendMessageOverSocket();
				}
			}
		});
		$scope.setUserActiveStatus = function (userId, statusValue) {
			//update in userList
			angular.forEach($scope.userList, function(user){
				if(user.userId == userId){
					user.status = statusValue;
				}
			});
			
			//update in selectedUser
			var captionValue = statusValue;
			if(captionValue.search($scope.TYPING_CAPTION) >= 0){
				captionValue = $scope.TYPING_CAPTION;
			}
			switch (captionValue){
			case $scope.ONLINE_CAPTION:
				$scope.selectedUser.USER_STATUS_COLOR = $scope.ONLINE_COLOR;
				break;
			case $scope.OFFLINE_CAPTION:
				$scope.selectedUser.USER_STATUS_COLOR = $scope.OFFLINE_COLOR;
				break;
			case $scope.TYPING_CAPTION:
				$scope.selectedUser.USER_STATUS_COLOR = $scope.TYPING_COLOR;
				break;
			default:
				break;
			};
			$scope.selectedUser.status = statusValue;
			$scope.$apply();
		};
		
		$scope.createNewChannel = function (){
			window.location.href = '/chat-me/app/create-channel.html';
		};
		
})
.factory('commonService', __commonService)
.factory('messengerService', ['$http', 'commonService', __messengerService])
.factory('channelService', ['$http', 'commonService', __channelService])
.factory('socketService', ['$q', '$timeout', 'commonService', __socketService])
.factory('userService', ['$http', 'commonService', 'channelService', 
						'messengerService', 'socketService', __userService]);


