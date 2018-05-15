(function (ng) {
    var mod = ng.module("productosModule");
    mod.constant("productosContext", "productos");
    mod.constant("proveedoresContext", "api/proveedores")
    mod.controller('productosCtrl', ['$scope', '$http', 'productosContext','proveedoresContext', '$state',
        function ($scope, $http, productosContext, proveedoresContext, $state) {
            $http.get(productosContext).then(function (response) {
                $scope.productosRecords = response.data;
            });
        }
    ]);
}
)(window.angular);