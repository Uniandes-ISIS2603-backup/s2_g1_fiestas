(function (ng) {
    var mod = ng.module("pagoModule");
    mod.constant("pagoContext", "pagos");
     mod.constant("pagosContext", "api/pagos");
    mod.constant("eventosContext", "api/eventos");
    mod.controller('eventoUpdateCtrl', ['$scope', '$http', 'eventosContext', '$state', 'pagoContext','pagosContext'
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
        function ($scope, $http, eventosContext, $state, pagoContext,pagosContext) {
            $rootScope.edit = true;

            $scope.data = {};

            $scope.selectedItems = [];

            $scope.availableItems = [];

            var idEvento = $state.params.eventoId;
            var idPago = $state.params.pagoId;

            //Consulto el evento a editar.
            $http.get(eventosContext + '/' + idEvento + '/' + pagoContext + '/' + idPago).then(function (response) {
                var pago = response.data;
                $scope.data.realizado = pago.realizado;
                $scope.data.metodoPago = pago.metodoPago;
                $scope.data.estado = pago.estado;
                $scope.data.valor = pago.valor;
            });


            /**
             * @ngdoc function
             * @name createEvento
             * @methodOf eventos.controller:eventoUpdateCtrl
             * @description
             * Crea un nuevo evento con los libros nuevos y la información del
             * $scope.
             */
            $scope.createPago = function () {
                $http.put(pagosContext, $scope.data).then(function (response) {
                   
                });
                $http.put(eventosContext + '/' + idEvento + '/' + pagoContext + '/' + idPago).then(function (response) {
                    //Evento created successfully
                    $state.go('eventosList', {eventoId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);

