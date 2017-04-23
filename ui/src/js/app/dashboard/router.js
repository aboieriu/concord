var app = angular.module('app');
app.config(['$stateProvider', function ($stateProvider) {
            $stateProvider.state('dashboard', {
                url: '/dashboard',
                templateUrl: 'js/app/dashboard/template/dashboard.html',
                resolve: app.load( ['js/app/dashboard/controller/dashboard-controller.js'])
            });
        }
    ]
);
