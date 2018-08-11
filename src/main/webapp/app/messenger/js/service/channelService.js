angular.module('mainApp').factory('channelService', ['$http', function channelService($http){
	var service = {};
	service.getAllChannels =  function(userId){
		return $http(
			{
				url : '/chat-me/channel/getAll',
				method: 'POST',
				data: userId
			}
		);
	};
	service.createChannel = function (data){
		$http(
			{
				url : '/chat-me/channel/create',
				method: 'POST',
				data: data
			}		
		).then(function(response){
			if(response.status == 200){
				window.location.href = '/chat-me/app/messenger/';
			}
		});
	};
	
	return service;
	
}]);

