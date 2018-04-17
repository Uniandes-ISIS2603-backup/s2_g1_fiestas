(function (ng) {
    var mod = ng.module("blogsModule");
    mod.constant("blogsContext", "api/blogs");
    mod.controller('blogsCtrl', ['$scope', '$http', 'blogsContext',
        function ($scope, $http, blogsContext) {
            $http.get(blogsContext).then(function (response) {
                $scope.blogsRecords = response.data;
            });
        }
    ]);
}
)(window.angular);