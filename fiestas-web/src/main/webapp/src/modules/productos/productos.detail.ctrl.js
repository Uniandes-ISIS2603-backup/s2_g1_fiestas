(function (ng) {
    var mod = ng.module("productosModule");
    mod.constant("productosContext", "productos");
    mod.constant("proveedorContext", "api/proveedores")
    mod.controller('productosDetailCtrl', ['$scope', '$http', 'productosContext','proveedoresContext', '$state', '$filter','$rootScope',
        function ($scope, $http, productosContext, $state, $filter, proveedorContext, $rootScope) {

            if (($state.params.proveedorId !== undefined) && ($state.params.proveedorId !== null)) {
                $http.get(proveedorContext + '/' + $state.params.proveedorId + '/' + productosContext).then(function (response) {
                    $rootScope.productosRecords = response.data;
                    $rootScope.currentProducto = $filter('filter')($rootScope.productosRecords, {id: $state.params.productoId}, true)[0];
                });
            }
        }
    ]);
}
)(window.angular);