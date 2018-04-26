(function (ng) {
    var mod = ng.module("tematicasModule");
    mod.constant("tematicasContext", "api/tematicas");
    mod.controller('tematicasDetailCtrl', ['$scope', '$http', 'tematicasContext', '$state', '$filter',
        function ($scope, $http, tematicasContext, $state, $filter) {

            if (($state.params.tematicaId !== undefined) && ($state.params.tematicaId !== null)) {
                $http.get(tematicasContext).then(function (response) {
                    $scope.tematicasRecords = response.data;
                    $scope.currentTematica = $filter('filter')($scope.tematicasRecords, {id: $state.params.tematicaId}, true)[0];
                });
            }
        }
    ]);
}
)(window.angular);
