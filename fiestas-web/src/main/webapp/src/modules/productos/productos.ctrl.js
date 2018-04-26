(function (ng) {
    var mod = ng.module("productoModule");
    mod.constant("productoContext", "api/productos");
    mod.controller('productoCtrl', ['$scope', '$http', 'productoContext',
        function ($scope, $http, productoContext) {
            $http.get(productoContext).then(function (response) {
                $scope.productosRecords = response.data;
            });
        }
    ]);
}
)(window.angular);

