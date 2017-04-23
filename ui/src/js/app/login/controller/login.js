'use strict';

/* Controllers */
// signin controller
angular.module('app').controller('LoginFormController', ['$scope', '$rootScope', 'loginService', function($scope, $rootScope, loginService) {

    $scope.login = function() {
        alert('login');
    };
}]);