(function (ng) {
    var mod = ng.module("pagoModule");
    mod.constant("pagoContext", "pagos");
    mod.constant("eventosContext", "api/eventos");
    mod.controller('pagoUpdateCtrl', ['$scope', '$http', 'eventosContext', '$state', 'pagoContext','$rootScope',
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
         */
        function ($scope, $http, eventosContext, $state, pagoContext,$rootScope) {
            $rootScope.edit = true;

            $scope.data = {};

            $scope.selectedItems = [];

            $scope.availableItems = [];

            var idEvento = $state.params.eventoId;
            var idPago = $state.params.pagoId;

            //Consulto el pago a editar.
            $http.get(eventosContext + '/' + idEvento + '/' + pagoContext + '/' + idPago).then(function (response) {
                var pago = response.data;
                $scope.data.id = pago.id;
                $scope.data.realizado = pago.realizado;
                $scope.data.metodoPago = pago.metodoPago;
                $scope.data.estado = pago.estado;
                $scope.data.valor = pago.valor;
            });


            /**
             * @ngdoc function
             * @name updatePago
             * @methodOf pagos.controller:pagoUpdateCtrl
             * @description
             * Actualiza un pago
             */
            $scope.updatePago = function () {
                $http.put(eventosContext + '/' + idEvento + '/' + pagoContext + '/' + idPago, $scope.data).then(function (response) {
                    //Pago updated successfully
                    $state.go('pagosList', {pagoId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);

