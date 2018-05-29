var __messengerService = function($http, commonService) {
	var service = {};
	service.getScope = function(){
		return commonService.get('mainController');
	};
	service.calculateUnReadMessageCounter = function(messageTrnDtoList, scope){
		for(var i=0;i<scope.userList.length; ++i){
			scope.userList[i].unReadMessageCounter = 0;
		}
		for(var i=0;i<scope.channelList.length; ++i){
			scope.channelList[i].unReadMessageCounter = 0;
		}
		angular.forEach(messageTrnDtoList, function(messageTrnDto){
			for(var i=0;i<scope.userList.length; ++i){
				if(scope.userList[i].userId == messageTrnDto.sourceId){
					scope.userList[i].unReadMessageCounter += 1;
				}
			}
			for(var i=0;i<scope.channelList.length; ++i){
				if(scope.channelList[i].channelId == messageTrnDto.destinationId){
					scope.channelList[i].unReadMessageCounter += 1;
				}
			}
		});
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
				var messageTrnDtoList = response.data;
				service.calculateUnReadMessageCounter(messageTrnDtoList, scope);
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


