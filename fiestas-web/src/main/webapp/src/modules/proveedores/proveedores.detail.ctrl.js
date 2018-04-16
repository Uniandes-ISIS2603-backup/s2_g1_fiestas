(function (ng) {
    var mod = ng.module("proveedorModule");
    mod.constant("proveedorContext", "api/proveedores");
    mod.controller('proveedorDetailCtrl', ['$scope', '$http', 'proveedorContext', '$state', '$filter',
        function ($scope, $http, proveedorContext, $state, $filter) {

            if (($state.params.proveedorId !== undefined) && ($state.params.proveedorId !== null)) {
                $http.get('data/proveedores.json').then(function (response) {
                    $scope.proveedoresRecords = response.data;
                    $scope.currentSport = $filter('filter')($scope.proveedoresRecords, {id: $state.params.proveedorId}, true)[0];
                });
            }
        }
    ]);
}
)(window.angular);