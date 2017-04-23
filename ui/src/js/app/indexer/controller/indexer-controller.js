'use strict';

/* Controllers */
app.controller('IndexerController', ['$scope', '$http', '$state', '$window', function($scope, $http, $state, $window) {
    $scope.indexRequest = {
        page:'1',
        category:'ANIMALS',
        feature:'UPCOMING'
    };

    var getData = function(){
        $http.get(app.apiBaseUrl+ 'index').then(function(response){
            $scope.indexBatches=prepareBatchData(response.data.content);
        });

        $http.get(app.apiBaseUrl+ 'photos/categories').then(function(response){
            $scope.categories=response.data;
        });

        $http.get(app.apiBaseUrl+ 'photos/features').then(function(response){
            $scope.features=response.data;
        });
    };
    getData();



    //handlers
    $scope.submitIndexRequest = function(){
        $scope.indexRequestInProgress = true;
        $http.post(app.apiBaseUrl+ 'index', $scope.indexRequest).then(function(response){
            $scope.indexRequestInProgress = false;
            getData();
        });
    };

    $scope.deleteBatch = function(batchId) {
        $http.delete(app.apiBaseUrl+ 'index/'+ batchId).then(function(){
            getData();
        })
    };


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