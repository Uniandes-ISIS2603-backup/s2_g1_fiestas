(function (ng) {
    var mod = ng.module("clienteModule");
    mod.constant("clientesContext", "api/clientes");

    mod.controller('clienteUpdateCtrl', ['$scope', '$http', 'clientesContext', '$state', '$rootScope',
        /**
         * @ngdoc controller
         * @name clientes.controller:clienteUpdateCtrl
         * @description
         * Definición del controlador auxiliar para actualizar Autores. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} clientesContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Autores en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} booksContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Libros en el Backend.
         * @param {Object} $filter Dependencia injectada para hacer filtros sobre
         * arreglos.
         */
        function ($scope, $http, clientesContext, $state, $rootScope) {
            $rootScope.edit = true;

            $scope.data = {};

            $scope.selectedItems = [];

            $scope.availableItems = [];

            var idCliente = $state.params.clienteId;

            //Consulto el autor a editar.
            $http.get(clientesContext + '/' + idCliente).then(function (response) {
                var cliente = response.data;
                $scope.data.imagen = cliente.imagen;
                $scope.data.nombre = cliente.nombre;
                $scope.data.contrasena = cliente.contrasena;
                $scope.data.correo = cliente.correo;
                $scope.data.direccion = cliente.direccion;
                $scope.data.documento = cliente.documento;
                $scope.data.login = cliente.login;
                $scope.data.telefono = cliente.telefono;
            });
            
            /**
             * @ngdoc function
             * @name createCliente
             * @methodOf clientes.controller:clienteUpdateCtrl
             * @description
             * Crea un nuevo autor con los libros nuevos y la información del
             * $scope.
             */
            $scope.updateCliente = function () {
                $http.put(clientesContext + "/" + idCliente, $scope.data).then(function (response) {
                    if ($scope.selectedItems.length >= 0) {
                        $http.put(clientesContext + "/" + response.data.id, $scope.selectedItems).then(function (response) {
                        });
                    }
                    //Cliente created successfully
                    $state.go('clientesList', {clienteId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);