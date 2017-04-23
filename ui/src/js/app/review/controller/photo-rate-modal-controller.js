app.controller('PhotoRateModalController', ['$scope', '$http', '$state', '$window', '$modal', 'photo', '$modalInstance', function($scope, $http, $state, $window, $modal, $photo, $modalInstance) {
   $scope.photo = $photo;

    $scope.ratePhoto = function(rating){
        $http.get(app.apiBaseUrl+ 'photos/rate/'+$photo.photoId+'?rating='+rating).then(function(){
            $photo.humanRated=true;
            $modalInstance.close();
        });
    };
}]);