angular.module('app').factory('User', function (DS) {
    return DS.defineResource({
        name: 'User'
        , endpoint: '/users'
    });
});