var app = angular.module('app');
app.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('reviewList', {
            url: '/review',
            templateUrl: 'js/app/review/template/review.html',
            resolve: app.load( ['js/app/review/controller/review-controller.js'])
        });

        $stateProvider.state('reviewBatch', {
            url: '/reviewBatch/:batchId',
            templateUrl: 'js/app/review/template/review-batch.html',
            resolve: app.load( ['js/app/review/controller/review-batch-controller.js', 'js/app/review/controller/photo-rate-modal-controller.js'])
        });
        }
    ]
);
