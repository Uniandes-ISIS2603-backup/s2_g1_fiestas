(function (ng) {
    var mod = ng.module("productoModule");
    mod.constant("productosContext", "api/productos");
    mod.controller('productosCtrl', ['$scope', '$http', 'productosContext',
        function ($scope, $http, productosContext) {
            $http.get(productosContext).then(function (response) {
                $scope.productosRecords = response.data;
            });
        }
    ]);
}
)(window.angular);

