// config
var app = angular.module('app');
app.config([
        '$controllerProvider', '$compileProvider', '$filterProvider', '$provide',
function ($controllerProvider,   $compileProvider,   $filterProvider,   $provide) {

    // lazy controller, directive and service
    app.controller = $controllerProvider.register;
    app.directive  = $compileProvider.directive;
    app.filter     = $filterProvider.register;
    app.factory    = $provide.factory;
    app.service    = $provide.service;
    app.constant   = $provide.constant;
    app.value      = $provide.value;
    app.baseKey = "";
    app.apiBaseUrl = 'http://localhost:8080/';
}
])
.config(['$translateProvider', '$httpProvider', function($translateProvider, $httpProvider){
    // Register a loader for the static files
    // So, the module will search missing translation tables under the specified urls.
    // Those urls are [prefix][langKey][suffix].
    $translateProvider.useStaticFilesLoader({
      prefix: 'l10n/',
      suffix: '.JSON'
    });
    // Tell the module what language to use by default
    $translateProvider.preferredLanguage('en');
    // Tell the module to store the language in the local storage
    $translateProvider.useLocalStorage();

    $httpProvider.defaults.withCredentials = true;

    //Configure calls trough http provider
    $httpProvider.interceptors.push('middleware');

    app.factory('middleware', function() {
        return {
            request: function(config) {
                config.xhrFields= _.extend( config.xhrFields || {} , {
                    withCredentials: true
                });
                return config;
            }
        };
    });
}])
.config(['DSProvider','DSHttpAdapterProvider', function(DSProvider, DSHttpAdapterProvider){
    angular.extend(DSHttpAdapterProvider.defaults, {
        basePath:app.apiBaseUrl
    });
}])
.config(
    [          '$stateProvider', '$urlRouterProvider', 'JQ_CONFIG', 'MODULE_CONFIG',
        function ($stateProvider,   $urlRouterProvider, JQ_CONFIG, MODULE_CONFIG) {
    // define module load function
    app.load = function load(srcs, callback) {
        return {
            deps: ['$ocLazyLoad', '$q',
                function( $ocLazyLoad, $q ){
                    var deferred = $q.defer();
                    var promise  = false;
                    srcs = angular.isArray(srcs) ? srcs : srcs.split(/\s+/);
                    if(!promise){
                        promise = deferred.promise;
                    }
                    angular.forEach(srcs, function(src) {
                        promise = promise.then( function(){
                            if(JQ_CONFIG[src]){
                                return $ocLazyLoad.load(JQ_CONFIG[src]);
                            }
                            angular.forEach(MODULE_CONFIG, function(module) {
                                if( module.name == src){
                                    name = module.name;
                                }else{
                                    name = src;
                                }
                            });
                            return $ocLazyLoad.load(name);
                        } );
                    });
                    deferred.resolve();
                    return callback ? promise.then(function(){ return callback(); }) : promise;
                }]
        }
    };
}]);
