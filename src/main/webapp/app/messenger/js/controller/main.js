angular.module('mainApp', ['ngAnimate', 'ngSanitize', 'ui.bootstrap']);
angular.module('mainApp').controller('mainController',  
	function mainController($uibModal, $log, $scope, $compile, $interval, $document, 
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
		$scope.SCREEN_WIDTH = window.innerWidth;
		$scope.SCREEN_HEIGHT = window.innerHeight;
		$scope.CHATBOX_HEIGHT = $scope.SCREEN_HEIGHT * 0.80;
		$scope.CHATBOX_WIDTH = $scope.SCREEN_WIDTH * 0.75;
		$scope.MESSAGE_TEMPLATE_WIDTH = $scope.CHATBOX_WIDTH * 0.40;
		
		$scope.updateScreenDimension = function (){
			console.log(window.innerWidth + ', ' + window.innerHeight);
			$scope.SCREEN_WIDTH = window.innerWidth;
			$scope.SCREEN_HEIGHT = window.innerHeight;
			$scope.CHATBOX_HEIGHT = $scope.SCREEN_HEIGHT * 0.80;
			$scope.CHATBOX_WIDTH = $scope.SCREEN_WIDTH * 0.75;
			$scope.MESSAGE_TEMPLATE_WIDTH = $scope.CHATBOX_WIDTH * 0.40;
		};
		
		$scope.logout = function() {
			socketService.sendUserSessionInfo({
				'userSessionInfoEntity':{
					'userId' : $scope.user.userId,
					'logoutRequest' : true
				 }
			}, $scope.user.userId);
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
		$scope.getUserNameById = function(userId){// assuming we load all
													// registered users to our
													// main page
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
			
			// saves to $scope.messageHistoryList
			messengerService.fetchAllMessageBySourceAndDest({
				'sourceId' : $scope.user.userId,
				'destinationId' : $scope.selectedUser.userId,
				'messageMode' : messageMode
			});
		};
		$scope.displayMessageHistoryOnChatBox = function (){
			angular.forEach(this.messageHistoryList, function (messageTrnDto) {
				if($scope.messageMode == $scope.DIRECT_MODE){// DM
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
				else{// CHANNEL
					if(messageTrnDto.sourceId == $scope.user.userId){// mine
						$scope.messageIdtoEntityMap[messageTrnDto.messageId] = messageTrnDto;
						$scope.pushSenderMessageToChatBox(messageTrnDto);
					}
					else{// other people
						$scope.messageIdtoEntityMap[messageTrnDto.messageId] = messageTrnDto;
						$scope.pushRecieverMessageToChatBox(messageTrnDto, $scope.getUserNameById(messageTrnDto.sourceId));
					}
				}
			});
			$scope.scrollToEnd(document.getElementById('message-history'));
			$scope.notifyMessageAsReadByUserId($scope.selectedUser.userId, $scope.messageMode);
			$scope.resetUnReadMessageCounterByUserId($scope.selectedUser.userId);
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
			socketService.sendMessageTrnInfo(
					data, $scope.selectedUser.userId, $scope.messageMode);
		};
		
		$scope.getSymbolByMessageDeliveryStatus = function (status){
			var glyphicon = '<span ';
			switch(status){
			case 'SENT':
				glyphicon += 'class="glyphicon glyphicon-ok" style="color:black;"></span>';
				break;
			case 'UNREAD':
				glyphicon += 'class="glyphicon glyphicon-ok" style="color:black;"></span>';
				glyphicon += '<span class="glyphicon glyphicon-ok" style="color:black;"></span>';
				break;
			case 'READ':
				glyphicon += 'class="glyphicon glyphicon-ok" style="color:white;"></span>';
				glyphicon += '<span class="glyphicon glyphicon-ok" style="color:white;"></span>';
				break;
			default:
				glyphicon += 'class="glyphicon glyphicon-time" style="color:black;"></span>';
				break;
			}
			return glyphicon;
		};
		
		$scope.deleteMessageByMessageId = function (messageId){
			//messengerService.deleteMessageByMessageId(messageId);
			socketService.sendMessageOperationInfo({
				'messageOperationEntity':{
					'messageId' : messageId,
					'operation' : 'DELETE',
					'messageMode' : $scope.messageMode
				}
			}, $scope.selectedUser.userId, $scope.messageMode);
		};
		
		$scope.deleteMessageElementByMessageId = function(messageId){
			var messageTemplateElement = angular.element(
					document.getElementById(messageId));
			messageTemplateElement.remove();
			$scope.$apply();
		}
		
		$scope.displayEditMessagePopup = function (messageId){
			var modalInstance = $uibModal.open({
				  animation: true,
			      component: 'editMessagePopupComponent',
			      resolve: {
			    	  items: function(){
			    		  return [];
			    	  }
			      }
			});
			modalInstance.result.then(function (operation) {
				$log.info('operation performed : ' + operation);
				switch(operation){
				case 'DELETE':
					$scope.deleteMessageByMessageId(messageId);
					break;
				default:
					break;
				};
			}, function () {
			      $log.info('Modal dismissed at: ' + new Date());
		    });
		}
		
		$scope.getMessageTemplateForSender = function(messageTrnDto){
			return '<div style="padding-left:{{CHATBOX_WIDTH - MESSAGE_TEMPLATE_WIDTH}}px;" id="' + 
			messageTrnDto.messageId+ '">'+
			'<b>' + $scope.user.userName+':</b><br> '+
			'<a href="#" ng-click="displayEditMessagePopup(\''+messageTrnDto.messageId+'\')">'+
			'<div class="well well-sm" style="color:white;background-color:steelblue;'+
			'display:block;word-wrap:break-word;">'+
			messageTrnDto.message+
			'<p id="' + $scope.getHtmlIdForMessageDeliveryStatusByMessageId(
					messageTrnDto.messageId)+'"></p></div></a></div>';
		};
		
		$scope.getHtmlIdForMessageDeliveryStatusByMessageId = function(messageId){
			return 'message-delivery-status@' + messageId;
		};
		
		$scope.pushSenderMessageToChatBox = function(messageTrnDto){
			angular.element(document.getElementById('message-history'))
			.append($compile($scope.getMessageTemplateForSender(messageTrnDto))($scope));
			$scope.updateMessageDeliveryStatusByMessageTrn(messageTrnDto);
		}
		
		$scope.updateMessageDeliveryStatusByMessageTrn = function(messageTrnDto){
			// update the delivery status for this message
			var deliveryStatusElement = angular.element(
					document.getElementById(
							$scope.getHtmlIdForMessageDeliveryStatusByMessageId(
									messageTrnDto.messageId
							)
					)
			);
			deliveryStatusElement.empty();
			deliveryStatusElement.append(
					$compile(
						$scope.getSymbolByMessageDeliveryStatus(
							$scope.messageIdtoEntityMap[messageTrnDto.messageId].messageDeliveryStatus
						)
					)($scope)
			);
		};
		
		$scope.addSenderMessageTemplateToChatBox = function (messageTrnDto) {
			var exists = $scope.messageIdtoEntityMap.hasOwnProperty(messageTrnDto.messageId);
			$scope.messageIdtoEntityMap[messageTrnDto.messageId] = messageTrnDto;
			if(!exists){
				$scope.pushSenderMessageToChatBox(messageTrnDto);
			}
			$scope.updateMessageDeliveryStatusByMessageTrn(messageTrnDto);
			$scope.scrollToEnd(document.getElementById('message-history'));
	    	$scope.message = '';
	    	$scope.resetUnReadMessageCounterByUserId($scope.selectedUser.userId);
	    	$scope.$apply();
		};
		
		$scope.getMessageTemplateForReciever = function(messageTrnDto, ownerName){
			return '<div style="width:{{MESSAGE_TEMPLATE_WIDTH}}px;" id="' +
			messageTrnDto.messageId+ '">'+
			'<b>' + ownerName+':</b><br> '+
			'<div class="well well-sm" style="color:white;background-color:grey;'+
			'display:block;word-wrap:break-word;">'+
			messageTrnDto.message+
			'</div></div>';
		};
		
		$scope.pushRecieverMessageToChatBox = function (messageTrnDto, memberName=null){
			var ownerName = (memberName == null ? $scope.selectedUser.userName : memberName);
			angular.element(document.getElementById('message-history'))
			.append($compile($scope.getMessageTemplateForReciever(messageTrnDto, ownerName))($scope));
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
					user.unReadMessages = [];
				}
				$scope.userList.push(user);
			});
			var channelListCopy = $scope.channelList;
			$scope.channelList = [];
			angular.forEach(channelListCopy, function(channel){
				if(channel.channelId == userId){
					channel.unReadMessages = [];
				}
				$scope.channelList.push(channel);
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
		$scope.notifyMessageAsReadByUserId = function (userId, messageMode){
			var messageIds = [];
			if(messageMode == $scope.DIRECT_MODE){
				angular.forEach($scope.userList, function(user){
					if(userId == user.userId){
						messageIds = user.unReadMessages;
					}
				});
			}
			else if(messageMode == $scope.CHANNEL_MODE){
				angular.forEach($scope.channelList, function(channel){
					if(userId == channel.channelId){
						messageIds = channel.unReadMessages;
					}
				});
			}
			if(messageIds != null && messageIds.length > 0){
				var data = {};
				data.messageMarkAsReadInfoEntity = {
					'userId' : $scope.user.userId,
					'messageIds'	: messageIds
				};
				socketService.sendMessageMarkAsReadInfo(
						data, $scope.selectedUser.userId, $scope.messageMode);
			}
		};
		$scope.notifyMessageAsRead = function (messageTrnDtoList){
			var messageIds = [];
			angular.forEach(messageTrnDtoList, function(messageTrnDto){
				messageIds.push(messageTrnDto.messageId);
			});
			var data = {};
			data.messageMarkAsReadInfoEntity = {
				'userId' : $scope.user.userId,
				'messageIds'	: messageIds
			};
			socketService.sendMessageMarkAsReadInfo(
					data, $scope.selectedUser.userId, $scope.messageMode);
		};
		$scope.sendUserTypingStatus = function (){
			var data = {};
			data.userTyping = true;
			data.messageTrnDto = {
				'sourceId' : $scope.user.userId,
				'destinationId' : $scope.selectedUser.userId
			};
			socketService.sendMessageTypingInfo({
				'messageTypingInfoEntity' : data
			}, $scope.selectedUser.userId, $scope.messageMode);
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
			// update in userList
			var userListCopy = $scope.userList;
			$scope.userList = [];
			angular.forEach(userListCopy, function(user){
				if(user.userId == userId){
					user.status = statusValue;
				}
				$scope.userList.push(user);
			});
			
			
			// update in user
			if($scope.user != null && $scope.user.userId == userId){
				$scope.user.status = statusValue;
			}
			
			// update in selectedUser
			if($scope.selectedUser != null && $scope.selectedUser.userId == userId){
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
			}
			
			$scope.$apply();
		};
		
		$scope.createNewChannel = function (){
			var channelInfoEntity = {
				'channelMstDto':{
					'channelName' : $scope.channelName
				}
			};
			var channelUserMstDtoList = [];
			angular.forEach($scope.memberList, function(member){
				channelUserMstDtoList.push({
					'userId' : member.userId,
					'userType' : member.userType
				});
			});
			channelInfoEntity.channelUserMstDtoList = channelUserMstDtoList;
			channelService.createChannel(channelInfoEntity);
		};
		
		$scope.addUser = function (){
			var validSelection = false;
			var newMember = null;
			angular.forEach($scope.suggestedUserList, function(user){
				if(user.userName == $scope.selectedMember){
					validSelection = true;
					newMember = user;
					newMember.userType = 'user';
				}
			});
			if(!validSelection)
				return;
			$scope.memberList.push(newMember);
			var suggestedUserListCopy = $scope.suggestedUserList;
			$scope.suggestedUserList = [];
			angular.forEach(suggestedUserListCopy, function(user){
				if(user.userId != newMember.userId){
					$scope.suggestedUserList.push(user);
				}
			});
			$scope.selectedMember = '';
		};
		$scope.removeUser = function(member){
			var memberListCopy = $scope.memberList;
			$scope.memberList = [];
			angular.forEach(memberListCopy, function(mem){
				if(mem.userId != member.userId){
					$scope.memberList.push(mem);
				}
			});
			$scope.suggestedUserList.push(member);
		};
		
})
.factory('commonService', __commonService)
.factory('messengerService', ['$http', 'commonService', __messengerService])
.factory('channelService', ['$http', 'commonService', 'messengerService', __channelService])
.factory('socketService', ['$q', '$timeout', 'commonService', __socketService])
.factory('userService', ['$http', 'commonService', 'channelService', 
						'messengerService', 'socketService', __userService])
.directive('screenDimensionUpdater', ['$window', function ($window){
	return {
        link: link,
        restrict: 'E',
        template: ''
     };
     
     function link(scope, element, attrs){
       
       scope.width = $window.innerWidth;
       
       angular.element($window).bind('resize', function(){
       
    	    scope.SCREEN_WIDTH = window.innerWidth;
		    scope.SCREEN_HEIGHT = window.innerHeight;
			scope.CHATBOX_HEIGHT = scope.SCREEN_HEIGHT * 0.80;
			scope.CHATBOX_WIDTH = scope.SCREEN_WIDTH * 0.75;
			scope.MESSAGE_TEMPLATE_WIDTH = scope.CHATBOX_WIDTH * 0.40;
         
         // manuall $digest required as resize event
         // is outside of angular
         scope.$digest();
       });
       
     }
}])
.component('editMessagePopupComponent', {
	templateUrl: 'editMessagePopupContent.html',
	  bindings: {
	    resolve: '<',
	    close: '&',
	    dismiss: '&'
	  },
	  controller: function () {
	    var $ctrl = this;

	    $ctrl.$onInit = function () {
	      $ctrl.items = $ctrl.resolve.items;	   
	    };

	    $ctrl.delete = function () {
	      $ctrl.close({$value: 'DELETE'});
	    };

	    $ctrl.cancel = function () {
	      $ctrl.dismiss({$value: 'CANCEL'});
	    };
	  }
});					

