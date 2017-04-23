angular.module('app')
.directive('footerBlock', ['$rootScope', function($document) {
    return {
        templateUrl:'js/app/common/template/footer.html'
    };
}]);