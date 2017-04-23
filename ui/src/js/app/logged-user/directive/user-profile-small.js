angular.module('app')
.controller('LoggedUserUserProfileSmallController', ['$scope','$rootScope', function($scope, $rootScope) {
    $scope.loggedUser = $rootScope.master;
    $scope.showUserActions = false;
}]);
angular.module('app')
.directive('loggedUserProfile', ['$rootScope', function($document) {
    return {
        link: function($scope, element){
            element.addClass('user-profile-small-container');
        }
        , templateUrl:'js/app/logged-user/template/user-profile-small.html'
    };
}]);