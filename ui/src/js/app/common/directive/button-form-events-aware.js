angular.module('app')
.directive('buttonFormEventsAware', function() {

        var prepareLoadingAnim = function(element, attr) {
            var anim = jQuery('<div></div>');
            anim.addClass('loading-anim circle').append(jQuery("<div />")).append(jQuery("<div />")).append(jQuery("<div />")).append(jQuery("<div />"));
            if (attr.syncColor) {
                anim.addClass(attr.syncColor);
            }
          element.append(anim)
        };
    return {
        link: function($scope, element, attr) {
            // Add loadign anim markup
            prepareLoadingAnim(element, attr);

            $scope.$on('sync-init', function() {
                element.attr('disabled', 'disabled').addClass('sync');
            });

            $scope.$on('sync-fail', function(){
                element.removeAttr('disabled').removeClass('sync');
            });

            $scope.$on('sync-success', function(){
                element.removeAttr('disabled').removeClass('sync');
            });
        }
    };
});