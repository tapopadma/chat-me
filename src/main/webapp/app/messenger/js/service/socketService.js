angular.module('mainApp').factory('socketService', ['$q', '$timeout',function($q, $timeout){
	
	var service = {};
	var listener = $q.defer();
	var socket = {
	    client: null,
	    stomp: null
	};
	    
    service.RECONNECT_TIMEOUT = 30000;
    service.SOCKET_URL = "/chat-me/chat";
    service.CHAT_TOPIC = "/topic/message";
    service.CHAT_BROKER = "/app/chat";
    
    var initialize = function() {
      socket.client = new SockJS(service.SOCKET_URL);
      socket.stomp = Stomp.over(socket.client);
      socket.stomp.connect({}, startListener);
      socket.stomp.onclose = service.reconnect;
    };
    
    var reconnect = function() {
        $timeout(function() {
          initialize();
        }, this.RECONNECT_TIMEOUT);
    };
    
    var startListener = function() {
        if(service.user != null){
      	  // subscribe to a single topic '/topic/message/{userId}' for all kinda socket message info
      	  socket.stomp.subscribe(service.CHAT_TOPIC + '/' + service.user.userId, function(data) {
      	        listener.notify(data.body);
      	      });
        }
        if(service.user != null && (!service.user.hasOwnProperty('status') ||
      		  service.user.status != 'online')){
      	  service.sendUserSessionInfo({
          	  'userSessionInfoEntity':{
          		  'userId' : service.user.userId,
          		  'loginRequest' : true
          	  }
            }, service.user.userId);
        }
    };

    service.send = function(message, destination) {
      socket.stomp.send(destination, {}, JSON.stringify(message));
    };
    
    service.receive = function() {
      return listener.promise;
    };

    //send at /app/chat/userSessionInfo/{userId}
    service.sendUserSessionInfo = function(message, userId){
    	service.send(message, service.CHAT_BROKER + '/userSessionInfo/' + userId);
    };
    
    //send at /app/chat/messageTrnInfo/{userId}    
    service.sendMessageTrnInfo = function(sourceId, destinationId, messageMode, message){
    	service.send(message, service.CHAT_BROKER + '/messageTrnInfo/' 
    			+ sourceId + '/' + destinationId + '/' + messageMode);
    };
    
    service.sendMessageOperationInfo = function(sourceId, destinationId, messageMode, message){
    	service.send(message, service.CHAT_BROKER + '/messageOperationInfo/'
    			+ sourceId + '/' + destinationId + '/' + messageMode);
    };
    
    service.sendMessageMarkAsReadInfo = function(sourceId, destinationId, messageMode, message){
    	service.send(message, service.CHAT_BROKER + '/messageMarkAsReadInfo/' 
    			+ sourceId + '/' + destinationId + '/' + messageMode);
    };
    
    service.sendMessageTypingInfo = function(sourceId, destinationId, messageMode, message){
    	service.send(message, service.CHAT_BROKER + '/messageTypingInfo/' 
    			+ sourceId + '/' + destinationId + '/' + messageMode);
    };
    
    service.setUserInfo = (scope) => {
    	service.user = scope.user;
    	console.log('client info updated!!!');
    };
    
    initialize();
    return service;
}]);