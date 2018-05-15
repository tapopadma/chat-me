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
		$scope.channelList = [];
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
		};
		$scope.selectChannel = function (channel){
			$scope.chatType = $scope.CHANNEL_SELECTED;
			$scope.selectedUser = {'username':channel};
			messengerService.fetchAllChannelMessage({
				'username' : $scope.username,
				'channelName' : channel
			});
		};
		$scope.selectUser = function (selectedUsername){
			$scope.chatType = $scope.USER_SELECTED;
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
		$scope.displayChannelMessageHistoryOnChatBox = function (){
			angular.forEach(this.messageHistoryList, function (message) {
				if(message.username == $scope.username){// current user is the sender
					$scope.addSenderMessageTemplateToChatBox(message.message);
				}
				else{// current user is the receiver
					$scope.addReceipientMessageTemplateToChatBox(message.message, message.username);
				}
			});
			$scope.scrollToEnd(document.getElementById('message-history'));
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
			$scope.sendMessageOverSocket();
		};
		$scope.sendMessageOverSocket = function () {
			var data = {};
			data.isUsertyping = false;
			if($scope.chatType == $scope.USER_SELECTED){
				data.messageinfoEntity = {
						'fromUsername' : $scope.username,
						'toUsername' : $scope.selectedUser.username,
						'message' : $scope.message,
						'isUsertyping': false
				};
			}
			else{
				data.channelmessageinfoEntity = {
						'username' : $scope.username,
						'channelName' : $scope.selectedUser.username,
						'message' : $scope.message
				};
			}
			socketService.send(data);
		};
		$scope.addSenderMessageTemplateToChatBox = function (text) {
			angular.element(document.getElementById('message-history'))
			.append($compile('<div style="color:green"><b>'+$scope.username+':</b> '+text+'</div>')($scope));
		};
		$scope.addReceipientMessageTemplateToChatBox = function (text, channelMemberName=null) {
			angular.element(document.getElementById('message-history'))
				.append($compile(
						'<div style="color:red"><b>'
						+(channelMemberName == null ? $scope.selectedUser.username :channelMemberName) 
						+':</b>'+text+'</div>')($scope));
		};
		$scope.sendUserTypingStatus = function (){
			var data = {};
			data.isUsertyping = true;
			if($scope.chatType == $scope.USER_SELECTED){
				data.messageinfoEntity = {
					'fromUsername' : $scope.username,
					'toUsername' : $scope.selectedUser.username
				};
			}
			else{
				data.channelmessageinfoEntity = {
					'username' : $scope.username,
					'channelName' : $scope.selectedUser.username,
				};
			}
			socketService.send(data);
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
		$scope.setUserActiveStatus = function (value) {
			angular.forEach($scope.userList, function(user){
				if(user.username == $scope.selectedUser.username){
					user.status = value;
				}
			});
			var captionValue = value;
			if(value.search($scope.TYPING_CAPTION)){
				value = $scope.TYPING_CAPTION;
			}
			switch (value){
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
			$scope.selectedUser.status = captionValue;
			$scope.$apply();
		};
		
		$scope.createNewChannel = function (){
			window.location.href = '/chat-me/app/create-channel.html';
		};
		
})
.factory('commonService', __commonService);




