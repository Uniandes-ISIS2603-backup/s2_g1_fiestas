(function (ng) {
    var mod = ng.module("proveedorModule");
    mod.constant("proveedorContext", "api/proveedores");
    mod.controller('proveedorCtrl', ['$scope', '$http', 'proveedorContext', '$rootScope', '$state', 
        function ($scope, $http, proveedorContext, $rootScope, $state) {
            $rootScope.proveedorId = $state.params.proveedorId;
            $http.get(proveedorContext).then(function (response) {
                $rootScope.proveedoresRecords = response.data;
            });
        }
    ]);
}
)(window.angular);