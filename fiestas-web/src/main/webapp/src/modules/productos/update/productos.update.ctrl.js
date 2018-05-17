(function (ng) {
    var mod = ng.module("productosModule");
    mod.controller('productoUpdateCtrl', ['$scope', '$http', '$state', '$rootScope',
        /**
         * @ngdoc controller
         * @name productos.controller:productoUpdateCtrl
         * @description
         * Definición del controlador auxiliar para actualizar Autores. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} productosContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Autores en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} booksContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Libros en el Backend.
         * @param {Object} $filter Dependencia injectada para hacer filtros sobre
         * arreglos.
         */
        function ($scope, $http, $state,  $rootScope) {
            $rootScope.edit = true;

            
            $scope.data = {};
            
            $scope.selectedItems = [];

            $scope.availableItems = [];
            
            var idProducto = $state.params.productoId;

            //Consulto el producto a editar.
            
            console.log($scope);
            
            $http.get("api/productos" + "/" + idProducto).then(function (response) {
                var producto = response.data;
                $scope.name = producto.name;
                $scope.descripcion = producto.descripcion;
                $scope.personal = producto.personal;
                $scope.incluidos = producto.incluidos;
                $scope.precio = producto.precio;
                $scope.imagen = producto.imagen;
            });
            
            console.log($scope);
            

            /**
             * @ngdoc function
             * @name updateProducto
             * @methodOf productos.controller:productoUpdateCtrl
             * @description
             * Crea un nuevo producto con los libros nuevos y la información del
             * $scope.
             */
            $scope.updateProducto = function () {
                $http.put("api/productos" + "/" + idProducto, $scope.data).then(function (response) {
                    //Producto created successfully
                    $state.go('productosList', {productoId: response.data.id}, {reload: true});
                });
            };;
        }
    ]);
}
)(window.angular);