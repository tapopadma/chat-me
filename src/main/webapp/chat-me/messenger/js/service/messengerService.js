angular.module('mainApp').factory('messengerService', ['$http', function($http) {
	var service = {};

	service.fetchAllUnreadMessage = function (data) {
		return $http(
			{
				method: 'POST',
				url: '/messenger/fetchAllUnreadMessage',
				data:data
			}
		);
	};
	/*
	 * @deprecated
	 * */
	service.markAllMessageAsRead = function (data){
		$http(
			{
				method : 'POST',
				url : '/messenger/markAllMessageAsRead',
				data : data
			}		
		).then(function(response){
			if(response.status == 200){
				
			}
		});
	};
	service.fetchAllMessageBySourceAndDest = function (data) {
		return $http(
			{
				method : 'POST',
				url: '/messenger/fetchAllMessageBySourceAndDest',
				data: data
			}
		);
	};
	/*service.saveMessage = function (data) {
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
	};*/
	service.deleteMessageByMessageId = function(messageId){
		return $http({
			url: '/messenger/deleteMessage/'+messageId,
		});
	};
	return service;
}]);


