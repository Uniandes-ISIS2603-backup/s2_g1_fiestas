(function (ng) {
    var mod = ng.module("blogsModule");
    mod.constant("blogsContext", "api/blogs");
    mod.controller('blogsDetailCtrl', ['$scope', '$http', 'blogsContext', '$state', '$filter',
        function ($scope, $http, blogsContext, $state, $filter) {
            if (($state.params.blogsId !== undefined) && ($state.params.blogsId !== null)) {
                    $http.get(blogsContext).then(function (response) {
                    $scope.blogsRecords = response.data;
                    $scope.currentBlogs = $filter('filter')($scope.blogsRecords, {id: $state.params.blogsId}, true)[0];
                });
            }
        }
    ]);
}
        )(window.angular);