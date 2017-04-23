var app = angular.module('app');
app.run(
    [          '$rootScope', '$state', '$stateParams','$urlRouter', '$http',
        function ($rootScope,   $state,   $stateParams, $urlRouter, $http) {
            $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams, status) {
                $rootScope.master = 'alex';
                if (!$rootScope.master && toState.name != 'login' && toState.name != 'signup'){
                    event.preventDefault();
                    getLoggedUser().then(function(response){
                        saveLoggedUser(response);
                        $state.go(toState.name);
                    }, function error(){
                        $state.go('login');
                    });
                }
            });


           $rootScope.$on('$locationChangeSuccess', function(event, targetRoute, referrerRoute) {
                var loginStateIsTarget = targetRoute.indexOf("#/login") > 0;
                var signupStateIsTarget = targetRoute.indexOf("#/signup") > 0;
                var loginStateIsFirst = loginStateIsTarget && targetRoute === referrerRoute;

                handleRouteOrLocationChange(event, loginStateIsTarget, signupStateIsTarget, loginStateIsFirst);
            });

            var handleRouteOrLocationChange = function(event, loginStateIsTarget, signupStateIsTarget, loginStateIsFirst) {
                // If we already have a master user defined, just carry on
                if (loginStateIsTarget && $rootScope.master) {
                    $state.go('dashboard');
                }
                // Signup page is allowd without a logged user
                if (signupStateIsTarget || $rootScope.master) {
                    return;
                }
                if (!loginStateIsTarget || loginStateIsFirst) {
                    event.preventDefault();
                    saveLoggedUser('alex');
                    if (loginStateIsTarget) {
                        $state.go('dashboard');
                    } else {
                        $urlRouter.sync();
                    }
                } else {
                    $urlRouter.sync();
                }
            };

            var getLoggedUser = function() {
                return $http.get(app.apiBaseUrl+'authenticated');
            };

            var saveLoggedUser = function(response) {
                $rootScope.master = response.data;
            };
            // Configures $urlRouter's listener *after* your custom listener
            $urlRouter.listen();
        }
    ]
)
.config(
    ['$urlRouterProvider', function ($urlRouterProvider) {
        $urlRouterProvider.otherwise('dashboard');
        $urlRouterProvider.deferIntercept();
    }]
);
