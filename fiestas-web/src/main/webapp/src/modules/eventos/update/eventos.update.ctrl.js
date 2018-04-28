(function (ng) {
    var mod = ng.module("eventoModule");
    mod.constant("eventosContext", "api/eventos");
    mod.constant("pagosContext", "pagos");
    mod.controller('eventoUpdateCtrl', ['$scope', '$http', 'eventosContext', '$state', 'pagosContext', '$rootScope',
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
         * @param {Object} $filter Dependencia injectada para hacer filtros sobre
         * arreglos.
         */
        function ($scope, $http, eventosContext, $state, pagosContext, $rootScope) {
            $rootScope.edit = true;

            $scope.data = {};

            $scope.selectedItems = [];

            $scope.availableItems = [];

            var idEvento = $state.params.eventoId;

            //Consulto el evento a editar.
            $http.get(eventosContext + '/' + idEvento).then(function (response) {
                var evento = response.data;
                $scope.data.celebrado = evento.celebrado;
                $scope.data.fecha = new Date(evento.fecha);
                $scope.data.descripcion = evento.descripcion;
                $scope.data.invitados = evento.invitados;
                $scope.data.lugar = evento.lugar;
                $scope.data.nombre = evento.nombre;
                $scope.allPagosEvento = evento.pagos;
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
                $http.put(eventosContext + "/" + idEvento, $scope.data).then(function (response) {
                    if ($scope.allPagosEvento.length >= 0) {
                        for (var pago in $scope.allPagosEvento) {
                            $http.put(eventosContext + '/' + idEvento + '/' + pagosContext, pago).then(function (response) {
                            });
                        }
                    }
                    //Evento created successfully
                    $state.go('eventosList', {eventoId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);


