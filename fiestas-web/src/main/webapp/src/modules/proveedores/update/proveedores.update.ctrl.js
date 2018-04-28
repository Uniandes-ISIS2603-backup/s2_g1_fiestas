(function (ng) {
    var mod = ng.module("proveedorModule");
    mod.constant("proveedoresContext", "api/proveedores");

    mod.controller('proveedorUpdateCtrl', ['$scope', '$http', 'proveedoresContext', '$state', '$rootScope',
        /**
         * @ngdoc controller
         * @name proveedores.controller:proveedorUpdateCtrl
         * @description
         * Definición del controlador auxiliar para actualizar Autores. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} proveedoresContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Autores en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} booksContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Libros en el Backend.
         * @param {Object} $filter Dependencia injectada para hacer filtros sobre
         * arreglos.
         */
        function ($scope, $http, proveedoresContext, $state, $rootScope) {
            $rootScope.edit = true;

            $scope.data = {};

            $scope.selectedItems = [];

            $scope.availableItems = [];

            var idProveedor = $state.params.proveedorId;

            //Consulto el autor a editar.
            $http.get(proveedoresContext + '/' + idProveedor).then(function (response) {
                var proveedor = response.data;
                $scope.data.imagen = proveedor.imagen;
                $scope.data.nombre = proveedor.nombre;
                $scope.data.contrasena = proveedor.contrasena;
                $scope.data.correo = proveedor.correo;
                $scope.data.direccion = proveedor.direccion;
                $scope.data.documento = proveedor.documento;
                $scope.data.login = proveedor.login;
                $scope.data.telefono = proveedor.telefono;
                $scope.data.penalizado = proveedor.penalizado;
            });
            
            /**
             * @ngdoc function
             * @name createProveedor
             * @methodOf proveedores.controller:proveedorUpdateCtrl
             * @description
             * Crea un nuevo autor con los libros nuevos y la información del
             * $scope.
             */
            $scope.updateProveedor = function () {
                $http.put(proveedoresContext + "/" + idProveedor, $scope.data).then(function (response) {
                    if ($scope.selectedItems.length >= 0) {
                        $http.put(proveedoresContext + "/" + response.data.id, $scope.selectedItems).then(function (response) {
                        });
                    }
                    //Proveedor created successfully
                    $state.go('proveedoresList', {proveedorId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);