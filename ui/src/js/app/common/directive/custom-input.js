angular.module('app')
    .directive('customInput', ['$rootScope','$compile', function($document, $compile) {
        var defaults = {
            id: _.uniqueId()
            , inputType: 'text'
            , required: false
            , inputFilledClass: 'input--filled'
            , autofocus:false
        };

        var gatherData = function(attr) {
            var input = {};
            if (attr.type){
                input.type = attr.type;
            }
            if (attr.model) {
                input.model = attr.model;
            }
            if (attr.label) {
                input.label = attr.label;
            }
            if (attr.required && attr.required == 'true') {
                input.required = true;
            }
            if (attr.autofocus && attr.autofocus == 'true') {
                input.autofocus = true;
            }

            return _.extend({}, defaults, input);
        };

        var addClassOnInputIfHasValue = function(input, element, inputFilledClass) {
            if (input.val().trim().length > 0) {
                element.addClass(inputFilledClass);
            } else {
                element.removeClass(inputFilledClass);
            }
        };

        return {
            link: function(scope, element, attr) {

                var inputData = gatherData(attr);

                var container = element.find('span.input');
                var input = element.find('.input__field ');
                var label = element.find('.input__label');
                var pseudoLabel = element.find('.input__label-content');

                // fill in data
                input.attr('id', inputData.id);
                input.attr('ng-model', inputData.model);
                input.attr('type', inputData.type);
                if (inputData.required) {
                    input.attr('required', 'true');
                }

                if (inputData.autofocus) {
                    input.attr('autofocus', 'autofocus');
                }

                label.attr('for', inputData.id);
                pseudoLabel.html(inputData.label);
                pseudoLabel.attr('data-content', inputData.label);

                $compile(input)(scope);

                // Inital check on input
                addClassOnInputIfHasValue(input, container, inputData.inputFilledClass);
                // repeat check for each blur on input
                input.on('change', function() {
                    addClassOnInputIfHasValue(input, container, inputData.inputFilledClass);
                });

            }
            , templateUrl: 'js/app/common/template/custom-input.html'
        };
    }]);