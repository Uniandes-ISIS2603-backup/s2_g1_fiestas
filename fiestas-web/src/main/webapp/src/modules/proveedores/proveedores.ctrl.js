(function (ng) {
    var mod = ng.module("proveedorModule");
    mod.constant("proveedorContext", "api/proveedores");
    mod.controller('proveedorCtrl', ['$scope', '$http', 'proveedorContext', '$rootScope',
        function ($scope, $http, proveedorContext, $rootScope) {
            $http.get(proveedorContext).then(function (response) {
                $rootScope.proveedoresRecords = response.data;
                $scope.proveedoresRecords = response.data;
            });
        }
    ]);
}
)(window.angular);