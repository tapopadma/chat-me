var __channelService =	function channelService($http, commonService, messengerService){
	var service = {};
	service.getScope = function (){
		return commonService.get('mainController');
	};
	service.getAllChannels =  function(userId){
		$http(
			{
				url : '/chat-me/channel/getAll',
				method: 'POST',
				data: userId
			}
		).then(function(response){
			var scope = service.getScope();
			if(response.status == 200){
				scope.channelList = [];
				angular.forEach(response.data, function(channel){
					channel.unReadMessages = [];
					scope.channelList.push(channel.channelMstDto);
				});
				messengerService.fetchAllUnreadMessage(scope.user.userId);
			}
		});
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
	
};

