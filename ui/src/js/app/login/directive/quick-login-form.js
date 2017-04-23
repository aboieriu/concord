angular.module('app')
.controller('LoggedUserHistory', ['$scope','$rootScope', 'loginService', function($scope, $rootScope, loginService) {
    $scope.showForm = false;

    var initialize = function() {
        $scope.showForm = false;
        $scope.lastLoggedUser = loginService.getLastLoggedUser();
        if ($scope.lastLoggedUser) {
            var parentScopeEval = $scope.showQuickLoginForm ? $scope.showQuickLoginForm.call() : true;
            if (parentScopeEval) {
                $scope.showForm = true;
            }
        }
    };

    $scope.$on('quick-login-refresh', initialize);

    $scope.loginRecent = function() {
        $scope.authError = null;
        var user = {email: $scope.lastLoggedUser.data.email, password: $scope.quickLoginUser.password};
        loginService.authenticate(user, $scope, $rootScope);
    };

    $scope.dispose = function() {
        $scope.showForm = false;
    };

    initialize();
}]);
angular.module('app')
.directive('loginResultContainerReactive', ['$rootScope', function($document) {
    var settings = {
        successClass:'form-success'
        , errorClass: 'form-error'
        , shakeClass: 'shake-block'
    };
    var markSuccess= function(element) {
        element.addClass(settings.successClass);
    };
    var unmarkSuccess = function(element) {
        element.removeClass(settings.successClass);
    };

    var markFailed = function(element) {
        element.addClass(settings.shakeClass).addClass(settings.errorClass);
    };

    var unmarkFailed = function(element) {
        element.removeClass(settings.shakeClass).removeClass(settings.errorClass);
    };

    var clean = function(element) {
        unmarkSuccess(element);
        unmarkFailed(element);
    };

    return {
        link: function($scope, element, attr) {
            $scope.$on('login-failed', function() {
                markFailed(element);
            });

            $scope.$on('login-success', function() {
                markSuccess(element);
            });

            $scope.$on('login-init', function() {
                clean(element);
            });
        }
    };
}]);
angular.module('app')
.directive('quickLoginForm', ['$rootScope', function($document) {
    return {
        templateUrl:'js/app/login/template/logged-user-history.html'
    };
}]);