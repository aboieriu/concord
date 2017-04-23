var app = angular.module('app');
app.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('classificationModels', {
            url: '/classification-models',
            templateUrl: 'js/app/classification-model/template/classification-models.html',
            resolve: app.load( ['js/app/classification-model/controller/classification-models-controller.js'])
        });

        $stateProvider.state('classificationModel', {
            url: '/classification-model/:modelId',
            templateUrl: 'js/app/classification-model/template/classification-model.html',
            resolve: app.load( ['js/app/classification-model/controller/classification-model-controller.js'])
        });
        }
    ]
);
