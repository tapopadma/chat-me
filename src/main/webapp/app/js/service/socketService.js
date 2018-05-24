angular.module('mainApp')
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
      var isUserTyping = socketMessageInfo.isUsertyping;
      var channelMessageInfo =socketMessageInfo.channelmessageinfoEntity;
      var messageInfo = socketMessageInfo.messageinfoEntity;
      var sessionInfo = socketMessageInfo.sessioninfoEntity;
      var scope = commonService.get('mainController');
      if(channelMessageInfo != null){
    	  if(channelMessageInfo.channelName != null){
    		  if(channelMessageInfo.message != null){
    			  if(channelMessageInfo.channelName == scope.selectedUser.username){
    				  scope.messageHistoryList.push(channelMessageInfo);
    				  if(channelMessageInfo.username == scope.username){
    					  scope.addSenderMessageTemplateToChatBox(
    							  channelMessageInfo);
    				  } 	    			  
    				  else {
    					  scope.addReceipientMessageTemplateToChatBox(
    							  channelMessageInfo, channelMessageInfo.username);
    				  }
    	    	  }
    	    	  scope.scrollToEnd(document.getElementById('message-history'));
    	    	  scope.message = '';
    	    	  scope.$apply();
    		  }
    		  else if(isUserTyping){
            	  if(channelMessageInfo.channelName == scope.selectedUser.username
            			  && channelMessageInfo.username != scope.username){
            		  scope.setUserActiveStatus(
            				  channelMessageInfo.username + ' ' + scope.TYPING_CAPTION);
            		  $timeout(function () {
            			  scope.setUserActiveStatus('');
            		  },1000);
            	  }
              }
    	  }
      }
      else if(messageInfo != null){
    	  if(messageInfo.fromUsername != null && messageInfo.toUsername != null){
        	  if(messageInfo.message != null){
        		  scope.messageHistoryList.push(messageInfo);
    	    	  if(messageInfo.fromUsername == scope.username && 
    	    			  messageInfo.toUsername == scope.selectedUser.username){
    	    		  scope.addSenderMessageTemplateToChatBox(messageInfo);
    	    	  }
    	    	  else if(messageInfo.fromUsername == scope.selectedUser.username && 
    	    			  messageInfo.toUsername == scope.username){
    	    		  scope.addReceipientMessageTemplateToChatBox(messageInfo);
    	    		  messageInfo.deliveryStatus = 'READ';
    	    		  service.send(messageInfo);
    	    	  }
    	    	  scope.scrollToEnd(document.getElementById('message-history'));
    	    	  scope.message = '';
    	    	  scope.$apply();
        	  }
        	  else if(isUserTyping){
            	  if(messageInfo.fromUsername == scope.selectedUser.username && 
            			  messageInfo.toUsername == scope.username){
            		  scope.setUserActiveStatus(scope.TYPING_CAPTION);
            		  $timeout(function () {
            			  scope.setUserActiveStatus(scope.ONLINE_CAPTION);
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
    			  scope.setUserActiveStatus(scope.ONLINE_CAPTION);
    		  }
    		  else if(sessionInfo.islogoutRequest){
    			  scope.setUserActiveStatus(scope.OFFLINE_CAPTION);
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
}]);