(function (ng) {
    var mod = ng.module("productoModule");
    mod.constant("productoContext", "api/productos");
    mod.controller('productoDetailCtrl', ['$scope', '$http', 'productoContext', '$state', '$filter',
        function ($scope, $http, productoContext, $state, $filter) {

            if (($state.params.productoId !== undefined) && ($state.params.productoId !== null)) {
                $http.get(productoContext).then(function (response) {
                    $scope.productosRecords = response.data;
                    $scope.currentProducto = $filter('filter')($scope.productosRecords, {id: $state.params.productoId}, true)[0];
                });
            }
        }
    ]);
}
)(window.angular);
