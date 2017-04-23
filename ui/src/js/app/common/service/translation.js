angular.module('app')
.factory('translateService', ['$http', '$state', '$translate', function($http, $state, $translate) {
    var DEFAULT_ERROR_MESSAGE_KEY = "common.error.default";
    var ERROR_KEY_PREFIX = "common.error.";

    var forKey = function(key) {
        var translated = $translate.instant(key);
        if (translated === key) {
            return false;
        }
        return translated;
    };

    var forErrorResponse = function(response) {
        if (response.data && response.data.code) {
            var translated = this.forKey(ERROR_KEY_PREFIX + response.data.code);
            if (translated){
                return translated;
            }
        }
        return getErrorMessageFromBackendOrDefault(response);
    };

    var getErrorMessageFromBackendOrDefault = function(response) {
        if(response.data && response.data.message) {
            return response.data.message;
        }
        return forKey(DEFAULT_ERROR_MESSAGE_KEY);
    };

    return {
        forKey:forKey
        , forErrorResponse:forErrorResponse
    }
}]);