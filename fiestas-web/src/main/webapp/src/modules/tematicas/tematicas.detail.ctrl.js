(function (ng) {
    var mod = ng.module("tematicaModule");
    mod.constant("tematicaContext", "api/tematicas");
    mod.controller('tematicaDetailCtrl', ['$scope', '$http', 'tematicaContext', '$state', '$filter',
        function ($scope, $http, tematicaContext, $state, $filter) {

            if (($state.params.tematicaId !== undefined) && ($state.params.tematicaId !== null)) {
                $http.get(tematicaContext).then(function (response) {
                    $scope.tematicasRecords = response.data;
                    $scope.currentProducto = $filter('filter')($scope.tematicasRecords, {id: $state.params.tematicaId}, true)[0];
                });
            }
        }
    ]);
}
)(window.angular);
