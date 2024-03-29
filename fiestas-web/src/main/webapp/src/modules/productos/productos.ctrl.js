(function (ng) {
    var mod = ng.module("productosModule");
    mod.constant("productosContext", "productos");
    mod.constant("proveedoresContext", "api/proveedores")
    mod.controller('productosCtrl', ['$scope', '$http', 'productosContext','proveedoresContext', '$state', '$rootScope',
        function ($scope, $http, productosContext, proveedoresContext, $state, $rootScope) {
            $rootScope.proveedorId = $state.params.proveedorId;
            $scope.proveedorId = $state.params.proveedorId;
            $rootScope.productoId = $state.params.productoId;
            if($state.params.proveedorId !== null && $state.params.proveedorId !== undefined )
            {
            $http.get(proveedoresContext + '/' + $state.params.proveedorId + '/' + productosContext).then(function (response) {
                $rootScope.productosRecords = response.data;
            });
        }
        }
    ]);
}
)(window.angular);  