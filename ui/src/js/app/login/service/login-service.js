angular.module('app').factory('loginService', ['$http', '$state', 'quickLoginDataService','translateService', function($http, $state, quickLoginDataService, translateService) {

    var authenticate = function(user, $scope, $rootScope) {
        $scope.$broadcast('login-init');
        $scope.$emit('sync-init');
        // Try to login
        return $http.post(app.apiBaseUrl+'authenticate', user)
            .then(function(response) {
                $scope.$broadcast('login-success');
                $scope.$emit('sync-success');
                quickLoginDataService.set(response.data);
                $state.go('dashboard');
            }, function(response) {
                $scope.$broadcast('login-failed');
                $scope.$emit('sync-fail');
                $scope.authError = translateService.forErrorResponse(response);
            });
    };

    // Expose public methods
    return {
        authenticate: authenticate
        , getLastLoggedUser : quickLoginDataService.get
    };
}]);