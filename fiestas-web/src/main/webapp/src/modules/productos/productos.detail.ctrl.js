/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function (ng) {
    var mod = ng.module("productoModule");
    mod.constant("productoContext", "api/productos");
    mod.controller('productoDetailCtrl', ['$scope', '$http', 'productoContext', '$state', '$filter',
        function ($scope, $http, productoContext, $state, $filter) {

            if (($state.params.productoId !== undefined) && ($state.params.productoId !== null)) {
                $http.get(productoContext).then(function (response) {
                    $scope.productosRecords = response.data;
                    $scope.currentPago = $filter('filter')($scope.productosRecords, {id: $state.params.productoId}, true)[0];
                });
            }
        }
    ]);
}
)(window.angular);
