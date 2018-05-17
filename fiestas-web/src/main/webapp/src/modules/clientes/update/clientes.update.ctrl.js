(function (ng) {
    var mod = ng.module("clienteModule");
    mod.constant("clientesContext", "api/clientes");
    mod.constant("eventosContext", "eventos");
    mod.constant("pagosContext", "pagos");
    mod.controller('clienteUpdateCtrl', ['$scope', '$http', 'clientesContext', '$state', '$rootScope', 'eventosContext', 'pagosContext',
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
        function ($scope, $http, clientesContext, $state, $rootScope, eventosContext, pagosContext) {
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

            //Consulto el autor a editar.
            $http.get(clientesContext + '/' + idCliente + '/' + eventosContext).then(function (response) {
                var eventos = response.data;
                $scope.eventos = eventos;
                $scope.data.eventos = eventos;
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
                    for (i = 0; i < $scope.eventos.length; i++) {
                        $scope.pagos = $scope.eventos[i].pagos;
                        console.log($scope.pagos);
                        $http.post(clientesContext + '/' + idCliente + '/' + eventosContext, $scope.eventos[i]).then(function (response) {  
                            for (j = 0; j < $scope.pagos.length; j++) {
                                $http.post(clientesContext + '/' + idCliente + '/' + eventosContext + '/' + response.data.id +  '/' + pagosContext, $scope.pagos[j]).then(function (response) {

                                });
                            }
                        });
                    }
                    //Cliente created successfully
                    if ($rootScope.currentRol === 'Admin')
                    {
                        $state.go('clientesList', {clienteId: response.data.id}, {reload: true});
                    } else
                        ($rootScope.currentRol === 'Cliente')
                    {
                        $state.go('clienteDetail', {clienteId: response.data.id}, {reload: true});
                    }

                });
            };
        }
    ]);
}
)(window.angular);