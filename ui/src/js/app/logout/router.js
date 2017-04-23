var app = angular.module('app');
app.config(
    ['$stateProvider', function ($stateProvider) {
        $stateProvider.state('logout', {
            url: '/logout',
            templateUrl: 'js/app/logout/template/layout.html',
            resolve: app.load( ['js/app/logout/controller/logout.js'])
        })
    }
    ]
);
