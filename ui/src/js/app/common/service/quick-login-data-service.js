angular.module('app')
.factory('quickLoginDataService', ['$http', '$state', 'localDataService', function($http, $state, localDataService) {

    var getLoginData = function() {
        var localStorageData = localDataService.get();
        if (localStorageData) {
            var loggedUserHistory = localStorageData.loggedUserHistory
            if (loggedUserHistory) {
                return _parseAndReturnLastUser(loggedUserHistory);
            }
        }
    };

    var saveLoginData = function(loginData) {
        var userLocalData = localDataService.get();
        // for now, always keep just one user
        userLocalData.loggedUserHistory = {};
        userLocalData.loggedUserHistory[loginData.email] = {
            data: {
                email:loginData.email
                , fname:loginData.firstName
                , lname: loginData.lastName
                , avatar: _.isObject(loginData.avatar) ? loginData.avatar.url : loginData.avatar
            }
            , timestamp: new Date().getTime()
        };
        localDataService.set(userLocalData);
    };

    var _parseAndReturnLastUser = function(loggedUserHistory) {
        if(_.keys(loggedUserHistory).length > 0) {
            return _formatTimestamp(_.values(loggedUserHistory)[0]);
        }
    };

    var _formatTimestamp = function(user) {
        if (user) {
            user.timestamp = moment(new Date(user.timestamp)).fromNow();
        }
        return user;
    };

    return {
        get:getLoginData
        , set: saveLoginData
    }
}]);