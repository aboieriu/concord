angular.module('app').factory('LoggedUser', function (DS) {
    return DS.defineResource({
        name: 'User'
        , endpoint: '/logged-user'
    });
});