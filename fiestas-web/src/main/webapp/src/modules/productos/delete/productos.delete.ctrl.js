(function (ng) {
    var mod = ng.module("productosModule");
    mod.constant("productosContext", "api/productos");
    mod.controller('productoDeleteCtrl', ['$scope', '$http', 'productosContext', '$state',
        /**
         * @ngdoc controller
         * @name productos.controller:productoDeleteCtrl
         * @description
         * Definición del controlador auxiliar para eliminar Productos. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} authorsContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Productos en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         */
        function ($scope, $http, productosContext, $state) {
            var idProducto = $state.params.productoId;
            /**
             * @ngdoc function
             * @name deleteProducto
             * @methodOf productos.controller:productoDeleteCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para eliminar el producto.
             * @param {String} id El ID del producto a eliminar.
             */
            $scope.deleteProducto = function () {
                $http.delete(productosContext + '/' + idProducto, {}).then(function (response) {
                    $state.go('productosList', {productoId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);