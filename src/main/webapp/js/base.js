var jmsapp = angular.module("jmsapp", [ 'ngRoute' ]);
jmsapp.controller('MessageController', function($scope, $http) {
	$scope.sendMessageFormData = {
		message : ""
	};
	$scope.sendMessage = function() {
		$http({
			method : 'POST',
			url : '/jms-queue/sendMessage',
			dataType : 'json',
			headers : {
				'Content-Type' : 'application/json'
			},
			data : $scope.sendMessageFormData,
		}).success(function(data) {
			$scope.showResult = true;
			$scope.result = data.result;
		});
	};
});