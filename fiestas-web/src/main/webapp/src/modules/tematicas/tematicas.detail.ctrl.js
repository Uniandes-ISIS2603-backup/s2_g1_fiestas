(function (ng) {
    var mod = ng.module("tematicasModule");
    mod.constant("tematicasContext", "api/tematicas");
    mod.controller('tematicasDetailCtrl', ['$scope', '$http', 'tematicasContext', '$state', '$filter',
        function ($scope, $http, tematicasContext, $state, $filter) {

            if (($state.params.tematicaID !== undefined) && ($state.params.tematicaID !== null)) {
                $http.get(tematicasContext).then(function (response) {
                    $scope.tematicasRecords = response.data;
                    $scope.currentTematica = $filter('filter')($scope.tematicasRecords, {ID: $state.params.tematicaID}, true)[0];
                });
            }
        }
    ]);
}
)(window.angular);
