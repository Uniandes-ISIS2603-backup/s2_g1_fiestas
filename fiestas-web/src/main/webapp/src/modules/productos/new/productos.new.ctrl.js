(function (ng) {
    var mod = ng.module("productosModule");
    mod.controller('productosNewCtrl', ['$scope', '$http', 'proveedoresContext', 'productosContext', '$state', '$rootScope',
        /**
         * @ngdoc controller
         * @name productos.controller:productosNewCtrl
         * @description
         * Definición del controlador auxiliar para crear Productoes. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} productoContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Productoes en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} $rootScope Referencia injectada al Scope definida para
         * toda la aplicación.
         */
        function ($scope, $http, productosContext, $state, $rootScope) {
            $rootScope.edit = false;
            $scope.data = {};

            /**
             * @ngdoc function
             * @name createProducto
             * @methodOf productos.controller:productoNewCtrl
             * @description0
             * Esta función utiliza el protocolo HTTP para crear la producto.
             * @param {Object} producto Objeto con la nueva de la producto.
             */
            $scope.createProducto = function () {
                     
                $http.post("api/productos" , $scope.data).then(function (response) {
                    console.log("POST!");
                    console.log(response);
                    
                    $http.put("api/proveedores" + '/' + $rootScope.params.proveedorId + '/' + "productos" + '/' + response.data.id, $scope.data)
                            .then(function (putResponse) {
                        console.log("PUT");
                        console.log(putResponse); 
                        console.log($rootScope.params);
                        return $state.go("productosList").then(function (x) { console.log('done'); console.log(x); }).catch(function (err) { console.log(err); });
                    });
                });   
            };
        }
    ]);
}
)(window.angular);

