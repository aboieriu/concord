'use strict';

/* Controllers */
app.controller('SocialEngagementController', ['$scope', '$http', '$state', '$window', function($scope, $http, $state, $window) {
    var graph = "[ { data: [###data###], points: { show: true, radius: 6}, splines: { show: true, tension: 0.45, lineWidth: 5, fill: 0 } } ], { colors: ['#23b7e5'], series: { shadowSize: 3 }, xaxis:{ font: { color: '#ccc' }, position: 'bottom', ticks: [ ###labels### ] }, yaxis:{ font: { color: '#ccc' } }, grid: { hoverable: true, clickable: true, borderWidth: 0, color: '#ccc' }, tooltip: true, tooltipOpts: { content: '%y.0 photos engaged', defaultTheme: false, shifts: { x: 0, y: 20 } } }";

    var initialGraph = graph.replace("###data###", "[0, 1]");
    $scope.graphData = initialGraph.replace("###labels###", "[1, '-']");
    $scope.refresh= new Date();

    $http.get(app.apiBaseUrl+ 'social/all').then(function(response){
        $scope.classififedPhotos = response.data.content;
    });

    $http.get(app.apiBaseUrl+ 'social/successful-summary').then(function(response){
        var count = 0;
        var labels="";
        var data = "";
        for (var key in response.data) {
            data += "["+count+","+response.data[key]+"],";
            labels += "["+count+",'"+key+"'],";
            count++;
        }

        graph = graph.replace("###data###", data.substr(0, data.length-1));
        graph = graph.replace("###labels###", labels.substr(0, labels.length-1));
        $scope.refresh= new Date();
        $scope.graphData = graph;
    });

}]);