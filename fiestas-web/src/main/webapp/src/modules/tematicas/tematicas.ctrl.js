(function (ng) {
    var mod = ng.module("tematicasModule");
    mod.constant("tematicasContext", "api/tematicas");
    mod.controller('tematicasCtrl', ['$scope', '$http', 'tematicasContext',
        function ($scope, $http, tematicasContext) {
            $http.get(tematicasContext).then(function (response) {
                $scope.tematicasRecords = response.data;
            });
        }
    ]);
}
)(window.angular);

