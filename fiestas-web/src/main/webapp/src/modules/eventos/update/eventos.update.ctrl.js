(function (ng) {
    var mod = ng.module("eventoModule");
    mod.constant("clientesContext", "api/clientes");
    mod.constant("eventosContext", "eventos");
    mod.constant("pagosContext", "pagos");
    mod.controller('eventoUpdateCtrl', ['$scope', '$http', 'eventosContext', '$state', 'pagosContext', '$rootScope','clientesContext',
        /**
         * @ngdoc controller
         * @name eventos.controller:eventoUpdateCtrl
         * @description
         * Definición del controlador auxiliar para actualizar Eventos. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} eventosContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Eventos en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} $rootScope Referencia injectada al Scope definida para
         * toda la aplicación.
         * @param {Object} clientesContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Clientes en el Backend
         */
        function ($scope, $http, eventosContext, $state, pagosContext, $rootScope,clientesContext) {
            $rootScope.edit = true;

            $scope.data = {};
            $scope.pagos = [];

            var idEvento = $state.params.eventoId;
            var idCliente = $state.params.clienteId;

            //Consulto el evento a editar.
            $http.get(clientesContext+ '/' + idCliente + '/' +eventosContext + '/' + idEvento).then(function (response) {
                var evento = response.data;
                $scope.data.celebrado = evento.celebrado;
                $scope.data.fecha = new Date(evento.fecha);
                $scope.data.descripcion = evento.descripcion;
                $scope.data.invitados = evento.invitados;
                $scope.data.lugar = evento.lugar;
                $scope.data.nombre = evento.nombre;
                $scope.pagos = evento.pagos;
                $scope.data.cliente = evento.cliente;
                $scope.data.tematica = evento.tematica;
            });

            /**
             * @ngdoc function
             * @name updateEvento
             * @methodOf eventos.controller:eventoUpdateCtrl
             * @description
             * Actualiza un evento con la información del
             * $scope.
             */
            $scope.updateEvento = function () {
                $http.put(clientesContext+ '/' + idCliente + '/' +eventosContext + "/" + idEvento, $scope.data).then(function (response) {
                    //Evento created successfully
                    for (i = 0; i < $scope.pagos.length; i++) {
                        $http.post(clientesContext+ '/' + idCliente + '/' +eventosContext + "/" + response.data.id + "/" + pagosContext, $scope.pagos[i]).then(function (response) {
                        });
                    }
                    $state.go('eventosList', {eventoId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);


