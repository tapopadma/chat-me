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
		$scope.initializeScopeVariables = function (){
			$scope.messageIdtoEntityMap = {};
			$scope.messageHistoryList = [];
		};
		$scope.selectChannel = function (channel){
			$scope.initializeScopeVariables();
			$scope.loadChatBoxInfo($scope.CHANNEL_MODE, 
					{'userId':channel.channelId, 
					'userName':channel.channelName});
		};
		$scope.selectUser = function (selectedUser){
			$scope.initializeScopeVariables();
			$scope.loadChatBoxInfo($scope.DIRECT_MODE, selectedUser);
		};
		$scope.getUserNameById = function(userId){//assuming we load all registered users to our main page
			var result = null;
			angular.forEach($scope.userList, function(user){
				if(user.userId == userId){
					result = user.userName;
				}
			});
			return result;
		};	
		$scope.loadChatBoxInfo = function(messageMode, selectedUser){
			$scope.messageMode = messageMode;
			$scope.selectedUser = selectedUser;
			
			if($scope.user.status != $scope.ONLINE_CAPTION){
				socketService.send({
					'userSessionInfoEntity':{
						'userId' : $scope.user.userId,
						'loginRequest' : true
					 }
				});
			}
			
			//saves to $scope.messageHistoryList
			messengerService.fetchAllMessageBySourceAndDest({
				'sourceId' : $scope.user.userId,
				'destinationId' : $scope.selectedUser.userId,
				'messageMode' : messageMode
			});
		};
		$scope.displayMessageHistoryOnChatBox = function (){
			angular.forEach(this.messageHistoryList, function (messageTrnDto) {
				if($scope.messageMode == $scope.DIRECT_MODE){//DM
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
				}
				else{//CHANNEL
					if(messageTrnDto.sourceId == $scope.user.userId){//mine
						$scope.messageIdtoEntityMap[messageTrnDto.messageId] = messageTrnDto;
						$scope.pushSenderMessageToChatBox(messageTrnDto);
					}
					else{//other people
						$scope.messageIdtoEntityMap[messageTrnDto.messageId] = messageTrnDto;
						$scope.pushRecieverMessageToChatBox(messageTrnDto, $scope.getUserNameById(messageTrnDto.sourceId));
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
		$scope.pushRecieverMessageToChatBox = function (messageTrnDto, memberName=null){
			angular.element(document.getElementById('message-history'))
			.append($compile(
					'<div id="' +messageTrnDto.messageId+'" style="color:red"><b>'
					+(memberName == null ? $scope.selectedUser.userName : memberName)
					+':</b>'+messageTrnDto.message+'</div>')($scope));
		};
		$scope.addReceipientMessageTemplateToChatBox = function (messageTrnDto, memberName=null) {
			var exists = $scope.messageIdtoEntityMap.hasOwnProperty(messageTrnDto.messageId);
			$scope.messageIdtoEntityMap[messageTrnDto.messageId] = messageTrnDto;
			if(!exists){
				$scope.pushRecieverMessageToChatBox(messageTrnDto, memberName);
			}
			$scope.scrollToEnd(document.getElementById('message-history'));
	    	$scope.message = '';
	    	$scope.resetUnReadMessageCounterByUserId($scope.selectedUser.userId);
	    	$scope.$apply();
			if(messageTrnDto.messageDeliveryStatus != 'READ'){
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
			var userListCopy = $scope.userList;
			$scope.userList = [];
			angular.forEach(userListCopy, function(user){
				if(user.userId == userId){
					user.status = statusValue;
				}
				$scope.userList.push(user);
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
.factory('channelService', ['$http', 'commonService', 'messengerService', __channelService])
.factory('socketService', ['$q', '$timeout', 'commonService', __socketService])
.factory('userService', ['$http', 'commonService', 'channelService', 
						'messengerService', 'socketService', __userService]);


