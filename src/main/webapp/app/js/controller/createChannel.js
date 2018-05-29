angular.module('createChannelApp', [])
.controller('createChannelController',
	function createChannelController($scope, userService, commonService, channelService){
		$scope.init = function () {
			commonService.set('createChannelController', $scope);
			userService.getUser();
		};
		$scope.addUser = function (){
			var validSelection = false;
			var newMember = null;
			angular.forEach($scope.userList, function(user){
				if(user.userName == $scope.selectedMember){
					validSelection = true;
					newMember = user;
					newMember.userType = 'user';
				}
			});
			if(!validSelection)
				return;
			$scope.memberList.push(newMember);
			var userListCopy = $scope.userList;
			$scope.userList = [];
			angular.forEach(userListCopy, function(user){
				if(user.userId != newMember.userId){
					$scope.userList.push(user);
				}
			});
			$scope.selectedMember = '';
		};
		$scope.removeUser = function(member){
			var memberListCopy = $scope.memberList;
			$scope.memberList = [];
			angular.forEach(memberListCopy, function(mem){
				if(mem.userId != member.userId){
					$scope.memberList.push(mem);
				}
			});
			$scope.userList.push(member);
		};
		$scope.createNewChannel = function (){
			var channelInfoEntity = {
				'channelMstDto':{
					'channelName' : $scope.channelName
				}
			};
			var channelUserMstDtoList = [];
			angular.forEach($scope.memberList, function(member){
				channelUserMstDtoList.push({
					'userId' : member.userId,
					'userType' : member.userType
				});
			});
			channelInfoEntity.channelUserMstDtoList = channelUserMstDtoList;
			channelService.createChannel(channelInfoEntity);
		};
	}
)
.factory('commonService', __commonService)
.factory('channelService', ['$http', 'commonService', __channelService])
.factory('userService', ['$http', 'commonService', 'channelService', __userService]);
