var __userService =	function ($http, commonService, channelService, messengerService, socketService){
	var service = {};
	service.getScope = function (){
		return commonService.get('mainController');
	};
    service.getAllLoggedInUsers = function () {
		$http(
			{
				method: 'GET',
				url: '/chat-me/messenger/getAllLoggedInUsers'
			}
		)
		.then(function (response){
			if(response.status == 200){
				alert(response.data);
			}
		});
	};
	service.getFriendListByUser = function (user) {
		$http(
			{
				method: 'GET',
				url: '/chat-me/user/getAllUsers'
			}
		)
		.then(function (response){
			if(response.status == 200){
				var scope = service.getScope();
				var responseData = response.data;
				var userList = [];
				angular.forEach(responseData, function(data){
					var obj = data.userMstDto;
					if(obj.userId != user.userId){
						obj.isloggedIn = data.loggedIn;
						obj.status = obj.isloggedIn ? 'online' : 'offline';
						obj.USER_STATUS_COLOR = 
							obj.isloggedIn ? scope.ONLINE_COLOR : scope.OFFLINE_COLOR;
						obj.unReadMessages = [];
						userList.push(obj);
					}
				});
				scope.userList = userList;
				channelService.getAllChannels(scope.user.userId);
			}
		});
	};
	service.getUser = function () {
		$http(
			{
				method: 'GET',
				url: '/chat-me/user/getLoggedInUser'
			}
		)
		.then(function (response){
			if(response.status == 200){
				var scope = service.getScope();
				scope.user = response.data;
				scope.memberList = [];
				var member = scope.user;
				member.userType = 'admin';
				scope.memberList.push(member);
				service.getFriendListByUser(scope.user);
			}
		});
	};
	return service;
};