angular.module('mainApp')
.factory('messengerService', ['$http', 'commonService', function($http, commonService) {
	return {
		fetchAllChannelMessage: function (data) {
			$http(
				{
					method : 'POST',
					url: '/chat-me/messenger/fetchAllChannelMessage',
					data: data
				}
			)
			.then(function (response) {
				if(response.status == 200){
					var scope = commonService.get('mainController');
					scope.messageHistoryList = response.data;
					scope.clearChatBox();
					scope.displayChannelMessageHistoryOnChatBox();
					console.log('fetched message history data successfully!!!');
				}
				else{
					console.log('unable to fetch the data!!!');
				}
			});
		},
		fetchAllMessage: function (data) {
			$http(
				{
					method : 'POST',
					url: '/chat-me/messenger/fetchAllMessage',
					data: data
				}
			)
			.then(function (response) {
				if(response.status == 200){
					var scope = commonService.get('mainController');
					scope.messageHistoryList = response.data;
					scope.clearChatBox();
					scope.displayMessageHistoryOnChatBox();
					console.log('fetched message history data successfully!!!');
				}
				else{
					console.log('unable to fetch the data!!!');
				}
			});
		},
		saveMessage: function (data) {
			return;//polling no more useful
			$http(
				{
					method: 'POST',
					url: '/chat-me/messenger/saveMessage',
					data: data
				}
			)
			.then(function (response){
				if(response.status == 200){
					var scope = commonService.get('mainController');
					scope.selectUser(scope.selectedUser.username);
					scope.message = '';
					console.log('message sent successfully!!!');
				}
				else{
					console.log('something went wrong!!!');
				}
			});
		}
	};
}]);