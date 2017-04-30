var app = angular.module('app');
app.config(['$stateProvider', function ($stateProvider) {
            $stateProvider.state('indexer', {
                url: '/indexer',
                templateUrl: 'js/app/indexer/template/indexer.html',
                resolve: app.load( ['js/app/indexer/controller/indexer-controller.js'])
            });

            $stateProvider.state('indexerAuto', {
                url: '/indexer-auto',
                templateUrl: 'js/app/indexer/template/indexer-auto.html',
                resolve: app.load( ['js/app/indexer/controller/indexer-auto-controller.js'])
            });
        }
    ]
);
