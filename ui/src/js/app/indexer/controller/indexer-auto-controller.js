'use strict';

/* Controllers */
app.controller('IndexerController', ['$scope', '$http', '$state', '$window', function($scope, $http, $state, $window) {
    $scope.indexRequest = {
        page:'1',
        category:'ANIMALS',
        feature:'UPCOMING'
    };

    var getData = function(){
        $http.get(app.apiBaseUrl+ 'index?source=SYSTEM').then(function(response){
            $scope.indexBatches=prepareBatchData(response.data.content);
        });
    };
    getData();

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