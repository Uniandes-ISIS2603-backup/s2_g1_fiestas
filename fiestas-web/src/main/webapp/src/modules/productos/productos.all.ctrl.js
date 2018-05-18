(function (ng) {
    
    var mod = ng.module("productosModule");
    mod.controller('productosAllCtrl', ['$scope', '$http', '$state', '$rootScope',
        function ($scope, $http, $state, $rootScope) {
            /**
                 * @ngdoc function
                 * @name getProductoID
                 * @methodOf productos.controller:productoDetailCtrl
                 * @description
                 * Esta función utiliza el protocolo HTTP para obtener el recurso 
                 * donde se encuentra el evento por ID en formato JSON.
                 * @param {String} URL Dirección donde se encuentra el recurso
                 * del evento o API donde se puede consultar.
                 */
            console.log($rootScope);
            $http.get("api/productos").then(function (response) {
                $rootScope.productosRecords = response.data;
            });
            
        }
    ]);
}
        )(window.angular);