angular.module('mainApp', ['ngAnimate', 'ngSanitize', 'ui.bootstrap']);
angular.module('mainApp').controller('mainController',  
	function mainController($uibModal, $log, $scope, $compile, $interval, $document, $timeout,
			mainService, userService, messengerService, 
			socketService, channelService){
	
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
			mainService.logout()
            .then(function (response) {
                if (response.status == 200) {
                	window.location.reload();
                }
                else {
                    console.log("Logout failed!");
                }
            });
		};
		$scope.init = function (){
			userService.getUser().then((response) => {
				if(response.status == 200){
					$scope.user = response.data;
					socketService.setUserInfo($scope);
					$scope.memberList = [];
					var member = $scope.user;
					member.userType = 'admin';
					$scope.memberList.push(member);
					userService.getFriendListByUser(
						$scope.user).then((response) => {
						if(response.status == 200){
							var responseData = response.data;
							var userList = [];
							angular.forEach(responseData, (data) => {
								var obj = data.userMstDto;
								if(obj.userId != $scope.user.userId){
									obj.isloggedIn = data.loggedIn;
									obj.status = obj.isloggedIn ? 'online' : 'offline';
									obj.USER_STATUS_COLOR = 
										obj.isloggedIn ? $scope.ONLINE_COLOR : $scope.OFFLINE_COLOR;
									obj.unReadMessages = [];
									userList.push(obj);
								}
							});
							$scope.userList = userList;
							$scope.suggestedUserList = userList;
							channelService.getAllChannels(
								$scope.user.userId).then((response) => {
								if(response.status == 200){
									$scope.channelList = [];
									angular.forEach(response.data, (channel) => {
										channel.unReadMessages = [];
										$scope.channelList.push(channel.channelMstDto);
									});
									messengerService.fetchAllUnreadMessage(
										$scope.user.userId).then((response) => {
										if(response.status == 200){
											var messageTrnDtoList = response.data;
											$scope.calculateUnReadMessageCounter(
													messageTrnDtoList);
										}
									});
								}
							});
						}
					});
				}
			});
			socketService.receive().then(null, null, function(response){
				$scope.processSocketMessageReceived(response);
			});
		};
		
		$scope.processSocketMessageReceived = (message) => {
			
		  var socketMessageEntity = JSON.parse(message);
		      
	      var messageTrnInfoEntity = socketMessageEntity.messageTrnInfoEntity;
	      var messageTypingInfoEntity = socketMessageEntity.messageTypingInfoEntity;
	      var userSessionInfoEntity = socketMessageEntity.userSessionInfoEntity;
	      var messageMarkAsReadInfoEntity = socketMessageEntity.messageMarkAsReadInfoEntity;
	      var messageOperationEntity = socketMessageEntity.messageOperationEntity;
	      
	      $scope.updateMessageTrnInfo(messageTrnInfoEntity);
	      $scope.updateMessageTypingInfo(messageTypingInfoEntity);
	      $scope.updateUserSessionInfo(userSessionInfoEntity);
	      $scope.updateMessageMarkAsReadInfo(messageMarkAsReadInfoEntity);
	      $scope.updateMessageOperationInfo(messageOperationEntity);
		};
		
	    
	    $scope.updateMessageTrnInfo = function(messageTrnInfoEntity){
	    	if(messageTrnInfoEntity == null)
	    		return;
	        
	    	var messageTrnDtoList = messageTrnInfoEntity.messageTrnDtoList;
	    	
	    	angular.forEach(messageTrnDtoList, function(messageTrnDto){
	    		// if message belongs to currently selected chat box
	    		if($scope.messageMode == $scope.DIRECT_MODE){
	    			if(messageTrnDto.sourceId == $scope.user.userId){
	        			//i sent this message
	        			if($scope.hasOwnProperty('selectedUser') && 
	        				messageTrnDto.destinationId == $scope.selectedUser.userId){
	        				$scope.addSenderMessageTemplateToChatBox(messageTrnDto);
	        			}
	        		}
	        		else if(messageTrnDto.destinationId == $scope.user.userId){
	        			//the message sent to me
	        			if($scope.hasOwnProperty('selectedUser') &&
	        					messageTrnDto.sourceId == $scope.selectedUser.userId){
	        				$scope.addReceipientMessageTemplateToChatBox(messageTrnDto);
	        			}
	        		}
	    		}
	    		else{
	    			if($scope.hasOwnProperty('selectedUser') && 
	    					messageTrnDto.destinationId == $scope.selectedUser.userId){
	    				//message for the currently selected Channel
	    				if(messageTrnDto.sourceId == $scope.user.userId){//mine 
	    					$scope.addSenderMessageTemplateToChatBox(messageTrnDto);
	    				}
	    				else{//other people
	    					$scope.addReceipientMessageTemplateToChatBox(messageTrnDto, 
	    							$scope.getUserNameById(messageTrnDto.sourceId));
	    				}
	    			}
	    		}
	    		// if message is beyond currently selected chat box
	    		if(!$scope.hasOwnProperty('selectedUser') ||
	    				(messageTrnDto.messageMode == $scope.DIRECT_MODE
	    				&& messageTrnDto.destinationId == $scope.user.userId 
	    				&& messageTrnDto.sourceId != $scope.selectedUser.userId)
	    			||(messageTrnDto.messageMode == $scope.CHANNEL_MODE
	        				&& messageTrnDto.sourceId != $scope.user.userId 
	        				&& messageTrnDto.destinationId != $scope.selectedUser.userId)){
	    			if(messageTrnDto.messageMode == $scope.DIRECT_MODE){
	        			var userListCopy = $scope.userList;
	        			$scope.userList = [];
	        			angular.forEach(userListCopy, function(user){
	        				if(user.userId == messageTrnDto.sourceId){
	        					if(user.unReadMessages.indexOf(messageTrnDto.messageId) < 0)
	        						user.unReadMessages.push(messageTrnDto.messageId);
	        				}
	        				$scope.userList.push(user);
	        			});
	        		}
	        		else{
	        			var channelListCopy = $scope.channelList;
	        			$scope.channelList = [];
	        			angular.forEach(channelListCopy, function(channel){
	        				if(channel.channelId == messageTrnDto.destinationId){
	        					if(channel.unReadMessages.indexOf(messageTrnDto.messageId) < 0)
	        						channel.unReadMessages.push(messageTrnDto.messageId);
	        				}
	        				$scope.channelList.push(channel);
	        			});
	        		}
	    		}
	    	});
	    	//$scope.$apply();
	    };
	    
	    $scope.updateMessageTypingInfo = function (messageTypingInfoEntity){
	    	if(messageTypingInfoEntity == null)
	    		return;
	    	
	    	var messageTrnDto = messageTypingInfoEntity.messageTrnDto;
	    	var isUserTyping = messageTypingInfoEntity.userTyping;
	    	
	    	if(isUserTyping){
	    	  if($scope.messageMode == $scope.DIRECT_MODE){
				  if($scope.hasOwnProperty('selectedUser') && 
		    			  messageTrnDto.sourceId == $scope.selectedUser.userId){
		    		if(messageTrnDto.destinationId == $scope.user.userId){
		    		  $scope.setUserActiveStatus($scope.selectedUser.userId, $scope.TYPING_CAPTION);
		    		  $timeout(function () {
		    			  $scope.setUserActiveStatus($scope.selectedUser.userId, $scope.ONLINE_CAPTION);
		    		  },1000);
		    		}
		    	  }
	    	  }
	    	  else{
	    		  if($scope.hasOwnProperty('selectedUser') && 
		    			  messageTrnDto.destinationId == $scope.selectedUser.userId){
	    			  if(messageTrnDto.sourceId != $scope.user.userId){
	    				  $scope.setUserActiveStatus($scope.selectedUser.userId, 
	    	    				  $scope.getUserNameById(messageTrnDto.sourceId) + ' ' + $scope.TYPING_CAPTION);
	    	    		  $timeout(function () {
	    	    			  $scope.setUserActiveStatus($scope.selectedUser.userId, '');
	    	    		  },1000);
	    			  }
		    	  }
	    	  }
	    	}
	    };
	    
	    $scope.updateUserSessionInfo = function (userSessionInfoEntity){
	    	if(userSessionInfoEntity == null)
				return;
	    	if(userSessionInfoEntity.loginRequest){
				$scope.setUserActiveStatus(userSessionInfoEntity.userId, $scope.ONLINE_CAPTION);
			}
			else if(userSessionInfoEntity.logoutRequest){
			    $scope.setUserActiveStatus(userSessionInfoEntity.userId, $scope.OFFLINE_CAPTION);
			}
	    };
	    
	    $scope.updateMessageMarkAsReadInfo = function (messageMarkAsReadInfoEntity){
	    	if(messageMarkAsReadInfoEntity == null)
	    		return;
	    	//nothing useful 
	    };
	    
	    $scope.updateMessageOperationInfo = function(messageOperationEntity){
	    	if(messageOperationEntity == null)
	    		return;
	    	var operation = messageOperationEntity.operation;
	    	var messageId = messageOperationEntity.messageId;
	    	switch(operation){
	    		case 'DELETE':
	    			$scope.deleteMessageElementByMessageId(messageId);
	    			break;
	    		default:
	    			break;
	    	}
	    };
		
		$scope.calculateUnReadMessageCounter = function(messageTrnDtoList){
			for(var i=0;i<$scope.userList.length; ++i){
				$scope.userList[i].unReadMessages = [];
			}
			for(var i=0;i<$scope.channelList.length; ++i){
				$scope.channelList[i].unReadMessages = [];
			}
			angular.forEach(messageTrnDtoList, (messageTrnDto) => {
				if(messageTrnDto.messageMode == $scope.DIRECT_MODE){
					for(var i=0;i<$scope.userList.length; ++i){
						if($scope.userList[i].userId == messageTrnDto.sourceId){
							if($scope.userList[i].unReadMessages.indexOf(messageTrnDto.messageId) < 0)
								$scope.userList[i].unReadMessages.push(messageTrnDto.messageId);
						}
					}
				}
				else{
					for(var i=0;i<$scope.channelList.length; ++i){
						if($scope.channelList[i].channelId == messageTrnDto.destinationId){
							if($scope.channelList[i].unReadMessages.indexOf(messageTrnDto.messageId) < 0)
								$scope.channelList[i].unReadMessages.push(messageTrnDto.messageId);
						}
					}
				}
			});
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
			})
			.then((response) => {
				if(response.status == 200){
					$scope.messageHistoryList = response.data;
					$scope.clearChatBox();
					$scope.displayMessageHistoryOnChatBox();
					console.log('fetched message history data successfully!!!');
				}
				else{
					console.log('unable to fetch the data!!!');
				}
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
					$scope.user.userId, $scope.selectedUser.userId, $scope.messageMode, data);
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
			/*messengerService.deleteMessageByMessageId(messageId)
			.then((response) => {
				if(response.status == 204){
					$scope.selectUser(scope.selectedUser);
				}
				else{
					console.log('something went wrong!!!');
				}
			});*/
			socketService.sendMessageOperationInfo(
				$scope.user.userId, $scope.selectedUser.userId, $scope.messageMode,	
				{
				'messageOperationEntity':{
					'messageId' : messageId,
					'operation' : 'DELETE',
					'messageMode' : $scope.messageMode
				}
			});
		};
		
		$scope.deleteMessageElementByMessageId = function(messageId){
			var messageTemplateElement = angular.element(
					document.getElementById(messageId));
			messageTemplateElement.remove();
			//$scope.$apply();
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
	    	//$scope.$apply();
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
	    	//$scope.$apply();
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
						$scope.user.userId, $scope.selectedUser.userId, $scope.messageMode, data);
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
					$scope.user.userId, $scope.selectedUser.userId, $scope.messageMode, data);
		};
		$scope.sendUserTypingStatus = function (){
			var data = {};
			data.userTyping = true;
			data.messageTrnDto = {
				'sourceId' : $scope.user.userId,
				'destinationId' : $scope.selectedUser.userId
			};
			socketService.sendMessageTypingInfo(
				$scope.user.userId, $scope.selectedUser.userId, $scope.messageMode, {
				'messageTypingInfoEntity' : data
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
			
			//$scope.$apply();
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
.factory('messengerService', ['$http', __messengerService])
.factory('channelService', ['$http', __channelService])
.factory('socketService', ['$q', '$timeout', __socketService])
.factory('userService', ['$http', __userService])
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

