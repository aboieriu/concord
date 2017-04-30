'use strict';

/* Controllers */
app.controller('ClassificationModelsController', ['$scope', '$http', '$state', '$window', function($scope, $http, $state, $window) {

    var loadData = function(){
        $http.get(app.apiBaseUrl+ 'classification').then(function(response){
            $scope.models = response.data;
        });
    };
    loadData();

    $http.get(app.apiBaseUrl+ 'index/rate').then(function(response){
        $scope.indexBatches=response.data.content;
    });

    $http.get(app.apiBaseUrl+ 'photos/categories').then(function(response){
        $scope.categories=response.data;
    });

    $scope.submitModelRequest = function(indexRequest) {
        indexRequest.indexBatches = indexBatchesRequest();
        if (!indexRequest || !indexRequest.category || indexRequest.indexBatches.length ==0) {
            alert("Fill in data!");
            return;
        }

        $http.post(app.apiBaseUrl+ 'classification?photoCategory='+indexRequest.category, indexRequest.indexBatches).then(function(response){
            window.location.hash = "#/classification-model/" + response.data.id;
        });
    };

    var indexBatchesRequest = function(){
        var indexBatches = [];
        $("input[name='requestIndexBatches']").each(function(key, checkboxInput) {
            var value = getCheckboxeValueIfChecked($(checkboxInput));
            if (value) {
                indexBatches.push(value);
            }
        });
        return indexBatches;
    };

    var getCheckboxeValueIfChecked = function(checkboxInput) {
        if (checkboxInput.is(":checked")) {
            return checkboxInput.val();
        }
    };

    $scope.deleteModel = function(modelId) {
        $http.delete(app.apiBaseUrl+ 'classification/model/'+modelId).then(function(){
            loadData();
        })
    }
}]);