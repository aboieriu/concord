angular.module('app')
    .directive('customCheckbox', function() {
        return {
            link: function(scope, element) {

                var input = element.find('input');
                element.on('click', function(ev) {
                    console.log(input.val());
                });
            }
        };
    });