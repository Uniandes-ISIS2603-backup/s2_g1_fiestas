(function (ng) {
    var mod = ng.module("proveedorModule");
    mod.constant("proveedorContext", "api/proveedores");
    mod.controller('proveedorDetailCtrl', ['$scope', '$http', 'proveedorContext', '$state', '$filter', '$rootScope',
        function ($scope, $http, proveedorContext, $state, $filter, $rootScope) {

            if (($state.params.proveedorId !== undefined) && ($state.params.proveedorId !== null)) {
                $http.get(proveedorContext).then(function (response) {
                    $rootScope.proveedoresRecords = response.data;
                    $rootScope.currentProveedor = $filter('filter')($rootScope.proveedoresRecords, {id: $state.params.proveedorId}, true)[0];
                });
            }
        }
    ]);
}
)(window.angular);