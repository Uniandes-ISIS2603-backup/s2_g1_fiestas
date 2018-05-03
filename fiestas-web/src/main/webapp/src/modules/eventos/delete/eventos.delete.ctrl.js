(function (ng) {
    var mod = ng.module("eventoModule");
    mod.constant("eventosContext", "eventos");
    mod.constant("clientesContext", "api/clientes");
    mod.controller('eventoDeleteCtrl', ['$scope', '$http', 'eventosContext', '$state','clientesContext',
        /**
         * @ngdoc controller
         * @name eventos.controller:eventoDeleteCtrl
         * @description
         * Definición del controlador auxiliar para eliminar Eventos. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} authorsContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Eventos en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         */
        function ($scope, $http, eventosContext, $state,clientesContext) {
            var idEvento = $state.params.eventoId;
            /**
             * @ngdoc function
             * @name deleteEvento
             * @methodOf eventos.controller:eventoDeleteCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para eliminar el evento.
             * @param {String} id El ID del evento a eliminar.
             */
            $scope.deleteEvento = function () {
                $http.delete(clientesContext+ '/' + $state.params.clienteId + '/' +eventosContext + '/' + idEvento, {}).then(function (response) {
                    $state.go('eventosList', {eventoId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);