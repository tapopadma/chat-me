var __channelService =	function channelService($http, commonService){
	var service = {};
	service.getScope = function (){
		var currentUrl = window.location.href;
		if(currentUrl.search('channel') >= 0){
			return commonService.get('createChannelController');
		}
		if(currentUrl.search('main') >= 0){
			return commonService.get('mainController');
		}
		return null;
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
					channel.unReadMessageCounter = 0;
					scope.channelList.push(channel);
				});
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
				window.location.href = '/chat-me/app/main.html';
			}
		});
	};
	
	return service;
	
};

