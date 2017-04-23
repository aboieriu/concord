'use strict';

/* Controllers */
app.controller('ReviewController', ['$scope', '$http', '$state', '$window', '$modal', function($scope, $http, $state, $window, $modal) {

    $http.get(app.apiBaseUrl + 'index/' + $state.params.batchId).then(function(response){
        $scope.data = response.data;
    });

    $scope.open = function (photo) {
        $modal.open({
            templateUrl: 'js/app/review/template/photo-review-modal-content.html',
            controller: 'PhotoRateModalController',
            size: 'lg',
            resolve: {
                photo: function () {
                    return photo;
                }
            }
        });
    };

}]);