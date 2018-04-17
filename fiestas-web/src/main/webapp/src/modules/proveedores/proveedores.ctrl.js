(function (ng) {
    var mod = ng.module("proveedorModule");
    mod.constant("proveedorContext", "api/proveedores");
    mod.controller('proveedorCtrl', ['$scope', '$http', 'proveedorContext',
        function ($scope, $http, proveedorContext) {
            $http.get('proveedorContext').then(function (response) {
                $scope.proveedoresRecords = response.data;
            });
        }
    ]);
}
)(window.angular);