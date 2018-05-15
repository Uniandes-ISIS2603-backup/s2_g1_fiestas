(function (ng) {
    var mod = ng.module("pagoModule");
    mod.constant("pagoContext", "pagos");
    mod.constant("eventosContext", "eventos");
    mod.constant("clientesContext", "api/clientes");
    mod.controller('pagoDetailCtrl', ['$scope', '$http', 'eventosContext', '$state', 'pagoContext', 'clientesContext',
        function ($scope, $http, eventosContext, $state, pagoContext, clientesContext) {
            /**
             * @ngdoc controller
             * @name pagos.controller:pagoDetailCtrl
             * @description
             * Definición de un controlador auxiliar del módulo Pagos. 
             * Se crea el controlador con el cual se manejan las vistas de detalle
             * del módulo.
             * @param {Object} $scope Referencia injectada al Scope definida para este
             * controlador, el scope es el objeto que contiene las variables o 
             * funciones que se definen en este controlador y que son utilizadas 
             * desde el HTML.
             * @param {Object} $http Objeto injectado para la manejar consultas HTTP
             * @param {Object} eventoContext Constante injectada que contiene la ruta
             * donde se encuentra el API de Eventos en el Backend.
             * @param {Object} $state Dependencia injectada en la que se recibe el 
             * estado actual de la navegación definida en el módulo.
             * @param {Object} pagoContext Constante injectada que contiene la ruta
             * donde se encuentra el API de Pagos en el Backend.
             * *@param {Object} clientesContext Constante injectada que contiene la ruta
             * donde se encuentra el API de Clientes en el Backend.
             */

            if (($state.params.pagoId !== undefined) && ($state.params.pagoId !== null)) {
                /**
                 * @ngdoc function
                 * @name getPagoID
                 * @methodOf pagos.controller:pagoDetailCtrl
                 * @description
                 * Esta función utiliza el protocolo HTTP para obtener el recurso 
                 * donde se encuentra el evento por ID en formato JSON.
                 * @param {String} URL Dirección donde se encuentra el recurso
                 * del evento o API donde se puede consultar.
                 */
                $http.get(clientesContext + '/' + $state.params.clienteId + '/' + eventosContext + '/' + $state.params.eventoId + '/' + pagoContext + '/' + $state.params.pagoId).then(function (response) {
                    $scope.currentPago = response.data;
                });
            }
        }
    ]);
}
)(window.angular);