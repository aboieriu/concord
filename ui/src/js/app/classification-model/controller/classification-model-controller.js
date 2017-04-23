'use strict';

/* Controllers */
app.controller('ClassificationModelController', ['$scope', '$http', '$state', '$window', function($scope, $http, $state, $window) {

    var loadData = function(){
        $http.get(app.apiBaseUrl+ 'classification/model/' + $state.params.modelId).then(function(response){
            $scope.model = response.data;
        });

        $http.get(app.apiBaseUrl+ 'classification/model/' + $state.params.modelId + '/data/summary').then(function(response){
            $scope.summary = response.data;
        });
    };

    loadData();


    $scope.trainModel = function trainModel(modelId, trainRequest) {
        $http.post(app.apiBaseUrl+ 'classification/model/' + modelId + '/train', {
            steps:1000
        }).then(function(){
            loadData();
        })
    };

    $scope.retrainModel = function trainModel(modelId, trainRequest) {
        $http.post(app.apiBaseUrl+ 'classification/model/' + modelId + '/retrain', {
            steps:1000
        }).then(function(){
            loadData();
        })
    }
}]);