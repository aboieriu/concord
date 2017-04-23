'use strict';

/* Controllers */
app.controller('ClassificationModelsController', ['$scope', '$http', '$state', '$window', function($scope, $http, $state, $window) {

    var loadData = function(){
        $http.get(app.apiBaseUrl+ 'classification').then(function(response){
            $scope.models = response.data;
        });
    }
    loadData();

    $http.get(app.apiBaseUrl+ 'index/rate').then(function(response){
        $scope.indexBatches=response.data.content;
    });

    $scope.submitModelRequest = function(modelRequest) {
        if (!modelRequest || !modelRequest.category || !modelRequest.indexBatches) {
            alert("Fill in data!");
            return;
        }

        $http.post(app.apiBaseUrl+ 'classification?photoCategory='+modelRequest.category, modelRequest.indexBatches).then(function(response){
            window.location.hash = "#/classification-model/" + response.data.id;
        });
    };

    $scope.deleteModel = function(modelId) {
        $http.delete(app.apiBaseUrl+ 'classification/model/'+modelId).then(function(){
            loadData();
        })
    }
}]);