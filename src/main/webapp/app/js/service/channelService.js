angular.module('mainApp')
.factory('channelService', ['$http', 'commonService', 
	function channelService($http, commonService){
	return {
		getAllChannels: function(username){
			$http(
				{
					url : '/chat-me/channel/getAll',
					method: 'POST',
					data: username
				}
			).then(function(response){
				var scope = commonService.get('mainController');
				if(response.status == 200){
					angular.forEach(response.data, function(channel){
						scope.channelList.push(channel.channelName);
					});
				}
			});
		}
	};
}]);