angular.module('app')
    .directive('inputFormEventsAware', function() {
        return {
            link: function($scope, element) {
                $scope.$on('sync-init', function() {
                    element.attr('disabled', 'disabled');
                });

                $scope.$on('sync-fail', function(){
                    element.removeAttr('disabled');
                });

                $scope.$on('sync-success', function(){
                    element.removeAttr('disabled');
                });
            }
        };
    });