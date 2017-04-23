angular.module('app')
    .directive('formEventsAware', function() {
        return {
            link: function($scope, element) {
                $scope.$on('sync-init', function() {
                    element.removeClass('error-state shake-block');
                });

                $scope.$on('sync-fail', function(){
                    element.addClass('error-state shake-block');
                });

                $scope.$on('sync-success', function(){
                    element.removeClass('error-state shake-block');
                });
            }
        };
    });