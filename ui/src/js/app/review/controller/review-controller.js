'use strict';

/* Controllers */
app.controller('ReviewController', ['$scope', '$http', '$state', '$window', function($scope, $http, $state, $window) {

    $http.get(app.apiBaseUrl + 'index/rate').then(function(response){
        $scope.indexBatches=prepareBatchData(response.data.content);
    });

    // Utility methods
    var prepareBatchData = function(data) {
        for (var key in data) {
            var item = data[key];
            item.date = formatDate(new Date(item.date));
            if (!item.photoIndexRequest) {
                item.photoIndexRequest = {};
            }
        }
        return data;
    };

    var formatDate = function(d) {
        return moment(d).fromNow();
    }
}]);