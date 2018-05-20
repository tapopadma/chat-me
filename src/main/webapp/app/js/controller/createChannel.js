angular.module('createChannelApp', [])
.controller('createChannelController',
	function createChannelController($scope, userService, commonService, channelService){
		$scope.init = function () {
			commonService.set('createChannelController', $scope);
			$scope.memberList = [];
			$scope.userList = [];	
			userService.getUsername();
			userService.getAllUsers();
		};
		$scope.addUser = function (){
			var validSelection = false;
			angular.forEach($scope.userList, function(username){
				if(username == $scope.selectedMember){
					validSelection = true;
				}
			});
			if(!validSelection)
				return;
			$scope.memberList.push($scope.selectedMember);
			var userListCopy = $scope.userList;
			$scope.userList = [];
			angular.forEach(userListCopy, function(username){
				if(username != $scope.selectedMember){
					$scope.userList.push(username);
				}
			});
			$scope.selectedMember = '';
		};
		$scope.removeUser = function(member){
			var memberListCopy = $scope.memberList;
			$scope.memberList = [];
			angular.forEach(memberListCopy, function(mem){
				if(mem != member){
					$scope.memberList.push(mem);
				}
			});
			$scope.userList.push(member);
		};
		$scope.createNewChannel = function (){
			channelService.createChannel({
				channelName: $scope.channelName, 
				userList: $scope.memberList
			});
		};
	}
)
.factory('channelService', ['$http', function($http){
	return {
		createChannel : function (data){
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
		}		
	};
}])
.factory('userService', ['$http', 'commonService', function ($http, commonService){
	return {
		getAllUsers : function (){
			$http(
				{
					url: "/chat-me/user/getAllUsers",
					method: 'GET'
				}
			).then(function(response){
				if(response.status == 200){
					var scope = commonService.get('createChannelController');
					scope.userList = [];
					angular.forEach(response.data, function(data){
						if(data.accountMstDto.username != scope.username)
							scope.userList.push(data.accountMstDto.username);
					});
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
					var scope = commonService.get('createChannelController');
					scope.username =  response.data.username;
					scope.memberList.push(scope.username);
				}
			});
		}
	}
}])
.factory('commonService', __commonService);
