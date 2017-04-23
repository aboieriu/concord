'use strict';

/* Controllers */
// signin controller
app.controller('LogoutController', ['$scope', '$http', '$state', '$rootScope', function($scope, $http, $state, $rootScope) {
    $rootScope.master = null;
    $state.go('login');
}]);