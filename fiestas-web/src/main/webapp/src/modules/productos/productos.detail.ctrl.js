(function (ng) {
    var mod = ng.module("productosModule");
    mod.constant("productosContext", "api/productos");
    mod.controller('productosDetailCtrl', ['$scope', '$http', 'productosContext', '$state', '$filter',
        function ($scope, $http, productosContext, $state, $filter) {

            if (($state.params.productoId !== undefined) && ($state.params.productoId !== null)) {
                $http.get(productosContext).then(function (response) {
                    $scope.productosRecords = response.data;
                    $scope.currentProducto = $filter('filter')($scope.productosRecords, {ID: $state.params.productoId}, true)[0];
                });
            }
        }
    ]);
}
)(window.angular);