(function (ng) {
    var mod = ng.module("pagoModule");
    mod.constant("pagoContext", "pagos");
    mod.constant("eventosContext", "api/eventos");
    mod.controller('pagoNewCtrl', ['$scope', '$http', 'eventosContext', '$state', 'pagoContext', '$rootScope',
        /**
         * @ngdoc controller
         * @name eventos.controller:eventoNewCtrl
         * @description
         * Definición del controlador auxiliar para crear Eventos. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} eventosContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Eventos en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         *@param {Object} pagoContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Pago en el Backend.
         * @param {Object} $rootScope Referencia injectada al Scope definida para
         * toda la aplicación.
         */
        function ($scope, $http, eventosContext, $state, pagoContext, $rootScope) {
            $rootScope.edit = false;

            $scope.data = {};

            /**
             * @ngdoc function
             * @name createPago
             * @methodOf pagos.controller:pagoNewCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para crear el pago.
             */
            $scope.createPago = function () {
                $http.post(eventosContext+'/'+$state.params.eventoId+'/'+pagoContext, $scope.data).then(function (response) {
                    console.log($scope.data);
                    $state.go('pagosList', {pagoId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);