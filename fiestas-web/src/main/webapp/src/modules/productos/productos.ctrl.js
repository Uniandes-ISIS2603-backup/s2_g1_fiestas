/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
(function (ng) {
    var mod = ng.module("productoModule");
    mod.constant("productoContext", "api/productos");
    mod.controller('pagoCtrl', ['$scope', '$http', 'productosContext',
        function ($scope, $http, productosContext) {
            $http.get(productosContext).then(function (response) {
                $scope.productosRecords = response.data;
            });
        }
    ]);
}
)(window.angular);

