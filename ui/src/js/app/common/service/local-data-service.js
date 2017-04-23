angular.module('app')
.factory('localDataService', ['$http', '$state', function($http, $state) {

    var getUserLocalData = function() {
        return JSON.parse(localStorage.getItem(app.baseKey)) || {};
    };

    var saveUserLocalData = function(userLocalData) {
        localStorage.setItem(app.baseKey, JSON.stringify(userLocalData));
    };

    return {
        get:getUserLocalData
        , set: saveUserLocalData
    }
}]);