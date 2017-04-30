var app = angular.module('app');
app.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('socialEngagement', {
            url: '/social-engagement',
            templateUrl: 'js/app/social/template/social-engagement.html',
            resolve: app.load( ['js/app/social/controller/social-engagement-controller.js'])
        });
        }
    ]
);
