angular.module('app').config(
    ['$stateProvider', function ($stateProvider) {
            $stateProvider.state('login', {
                url: '/login'
                , templateUrl: 'js/app/login/template/layout.html'
                , resolve: app.load( [
                    'js/app/login/controller/login.js'
                    , 'js/app/login/service/login-service.js'
                    , 'js/app/login/directive/quick-login-form.js'
                    , 'js/app/common/service/quick-login-data-service.js'
                ])
            })
        }
    ]
);
