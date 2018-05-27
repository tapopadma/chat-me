var __messengerService = function($http, commonService) {
	var service = {};
	service.getScope = function(){
		return commonService.get('mainController');
	};
	service.fetchAllUnreadMessage = function (data) {
		$http(
			{
				method: 'POST',
				url: '/chat-me/messenger/fetchAllUnreadMessage',
				data:data
			}
		)
		.then(function (response){
			if(response.status == 200){
				var scope = service.getScope();
				var messageTrnData = response.data;
				angular.forEach(scope.userList, function(user){
					user.unReadMessageCounter = 0;
				});
				angular.forEach(messageTrnData, function(message){
					angular.forEach(scope.userList, function(user){
						if(user.userId == message.sourceId){
							user.unReadMessageCounter += 1;
						}
					});
				});
			}
		});
	};
	/*
	 * @deprecated
	 * */
	service.markAllMessageAsRead = function (data){
		$http(
			{
				method : 'POST',
				url : '/chat-me/messenger/markAllMessageAsRead',
				data : data
			}		
		).then(function(response){
			if(response.status == 200){
				
			}
		});
	};
	service.fetchAllMessageBySourceAndDest = function (data) {
		$http(
			{
				method : 'POST',
				url: '/chat-me/messenger/fetchAllMessageBySourceAndDest',
				data: data
			}
		)
		.then(function (response) {
			if(response.status == 200){
				var scope = service.getScope();
				scope.messageHistoryList = response.data;
				scope.clearChatBox();
				scope.displayMessageHistoryOnChatBox();
				scope.notifyMessageAsRead(scope.getAllNotReadMessageTrns());
				console.log('fetched message history data successfully!!!');
			}
			else{
				console.log('unable to fetch the data!!!');
			}
		});
	};
	service.saveMessage = function (data) {
		$http(
			{
				method: 'POST',
				url: '/chat-me/messenger/saveMessage',
				data: data
			}
		)
		.then(function (response){
			if(response.status == 200){
				var scope = service.getScope();;
				scope.selectUser(scope.selectedUser.username);
				scope.message = '';
				console.log('message sent successfully!!!');
			}
			else{
				console.log('something went wrong!!!');
			}
		});
	};
	return service;
};


