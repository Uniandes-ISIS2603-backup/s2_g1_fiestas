(function (ng) {
    var mod = ng.module("tematicaModule");
    mod.constant("tematicaContext", "api/tematicas");
    mod.controller('tematicaCtrl', ['$scope', '$http', 'tematicaContext',
        function ($scope, $http, tematicaContext) {
            $http.get(tematicaContext).then(function (response) {
                $scope.tematicasRecords = response.data;
            });
        }
    ]);
}
)(window.angular);

