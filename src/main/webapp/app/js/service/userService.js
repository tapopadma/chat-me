angular.module('mainApp')
.factory('userService', ['$http', 'commonService', function ($http, commonService){
	return {
		getAllLoggedInUsers : function () {
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
		},
		getAllUsers : function () {
			$http(
				{
					method: 'GET',
					url: '/chat-me/user/getAllUsers'
				}
			)
			.then(function (response){
				if(response.status == 200){
					var scope = commonService.get('mainController');
					var responseData = response.data;
					var userList = [];
					angular.forEach(responseData, function(data){
						var obj = data.accountMstDto;
						obj.isloggedIn = data.loggedIn;
						obj.status = obj.isloggedIn ? 'online' : 'offline';
						obj.USER_STATUS_COLOR = 
							obj.isloggedIn ? scope.ONLINE_COLOR : scope.OFFLINE_COLOR;
						userList.push(obj);
					});
					scope.userList = userList;
				}
			});
		},
		getUsername : function () {
			$http(
				{
					method: 'GET',
					url: '/chat-me/user/getLoggedInUser'
				}
			)
			.then(function (response){
				if(response.status == 200){
					commonService.get('mainController').username 
						=  response.data.username;
				}
			});
		}
	};
		
}]);