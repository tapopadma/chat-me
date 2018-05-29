var __socketService = function($q, $timeout, commonService){
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
    
    var updateMessageTrnInfo = function(messageTrnInfoEntity, scope){
    	if(messageTrnInfoEntity == null)
    		return;
        
    	var messageTrnDtoList = messageTrnInfoEntity.messageTrnDtoList;
    	
    	angular.forEach(messageTrnDtoList, function(messageTrnDto){
    		if(scope.messageMode == scope.DIRECT_MODE){
    			if(messageTrnDto.sourceId == scope.user.userId){
        			//i sent this message
        			if(scope.hasOwnProperty('selectedUser') && 
        				messageTrnDto.destinationId == scope.selectedUser.userId){
        				scope.addSenderMessageTemplateToChatBox(messageTrnDto);
        			}
        		}
        		else if(messageTrnDto.destinationId == scope.user.userId){
        			//the message sent to me
        			if(scope.hasOwnProperty('selectedUser') &&
        					messageTrnDto.sourceId == scope.selectedUser.userId){
        				scope.addReceipientMessageTemplateToChatBox(messageTrnDto);
        			}
        			else{
        				var userListCopy = scope.userList;
        				scope.userList = [];
        				angular.forEach(userListCopy, function(user){
        					if(user.userId == messageTrnDto.sourceId){
        						user.unReadMessageCounter += 1;
        					}
        					scope.userList.push(user);
        				});
        			}
        		}
    		}
    		else{
    			if(messageTrnDto.destinationId == scope.selectedUser.userId){//message for the currently selected Channel
    				if(messageTrnDto.sourceId == scope.user.userId){//mine 
    					scope.addSenderMessageTemplateToChatBox(messageTrnDto);
    				}
    				else{//other people
    					scope.addReceipientMessageTemplateToChatBox(messageTrnDto, 
    							scope.getUserNameById(messageTrnDto.sourceId));
    				}
    			}
    			else{//message to other channel
    				var channelListCopy = scope.channelList;
    				scope.channelList = [];
    				angular.forEach(channelListCopy, function(channel){
    					if(channel.channelId == messageTrnDto.destinationId){
    						channel.unReadMessageCounter += 1;
    					}
    					scope.channelList.push(channel);
    				});
    			}
    		}
    	});
    	scope.$apply();
    };
    
    var updateMessageMiscellaneousInfo = function (messageMiscellaneousInfoEntity, scope){
    	if(messageMiscellaneousInfoEntity == null)
    		return;
    	
    	var messageTrnDto = messageMiscellaneousInfoEntity.messageTrnDto;
    	var isUserTyping = messageMiscellaneousInfoEntity.userTyping;
    	
    	if(isUserTyping){
    	  if(scope.messageMode == scope.DIRECT_MODE){
			  if(scope.hasOwnProperty('selectedUser') && 
	    			  messageTrnDto.sourceId == scope.selectedUser.userId){
	    		if(messageTrnDto.destinationId == scope.user.userId){
	    		  scope.setUserActiveStatus(scope.selectedUser.userId, scope.TYPING_CAPTION);
	    		  $timeout(function () {
	    			  scope.setUserActiveStatus(scope.selectedUser.userId, scope.ONLINE_CAPTION);
	    		  },1000);
	    		}
	    	  }
    	  }
    	  else{
    		  if(scope.hasOwnProperty('selectedUser') && 
	    			  messageTrnDto.destinationId == scope.selectedUser.userId){
	    		  scope.setUserActiveStatus(scope.selectedUser.userId, 
	    				  scope.getUserNameById(messageTrnDto.sourceId) + ' ' + scope.TYPING_CAPTION);
	    		  $timeout(function () {
	    			  scope.setUserActiveStatus(scope.selectedUser.userId, '');
	    		  },1000);
	    	  }
    	  }
    	}
    };
    
    var updateUserSessionInfo = function (userSessionInfoEntity, scope){
		if(userSessionInfoEntity == null)
			return;
  	  	if(scope.hasOwnProperty('selectedUser') && 
  	  			userSessionInfoEntity.userId == scope.selectedUser.userId){
  		  if(userSessionInfoEntity.loginRequest){
  			  scope.setUserActiveStatus(userSessionInfoEntity.userId, scope.ONLINE_CAPTION);
  		  }
  		  else if(userSessionInfoEntity.logoutRequest){
  			  scope.setUserActiveStatus(userSessionInfoEntity.userId, scope.OFFLINE_CAPTION);
  		  }
  	  }
    };
    
    var getMessage = function(data) {
      var socketMessageEntity = JSON.parse(data);
      
      var messageTrnInfoEntity = socketMessageEntity.messageTrnInfoEntity;
      var messageMiscellaneousInfoEntity = socketMessageEntity.messageMiscellaneousInfoEntity;
      var userSessionInfoEntity = socketMessageEntity.userSessionInfoEntity;
      var scope = commonService.get('mainController');
      
      updateMessageTrnInfo(messageTrnInfoEntity, scope);
      updateMessageMiscellaneousInfo(messageMiscellaneousInfoEntity, scope);
      updateUserSessionInfo(userSessionInfoEntity, scope);
      
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
};