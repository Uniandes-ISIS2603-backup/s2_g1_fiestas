(function (ng) {
    var mod = ng.module("pagoModule");
    mod.constant("pagoContext", "pagos");
    mod.constant("eventosContext", "eventos");
    mod.constant("clientesContext", "api/clientes");
    mod.controller('pagoCtrl', ['$scope', '$http', 'eventosContext', '$state', 'pagoContext', 'clientesContext',
        /**
         * @ngdoc controller
         * @name pagos.controller:pagosCtrl
         * @description
         * Definición del controlador de Angular del módulo Pagos. 
         * Se crea el controlador con el cual se maneja el módulo.
         * En el controlador se definen los atributos y métodos que pueden
         * ser accedidos desde el HTML utilizando el $scope.
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} eventosContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Eventos en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} pagoContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Pagos en el Backend.
         * @param {Object} clientesContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Clientes en el Backend.
         */
        function ($scope, $http, eventosContext, $state, pagoContext, clientesContext) {
             /**
             * @ngdoc function
             * @name getPagos
             * @methodOf pagos.controller:pagoCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para obtener el recurso 
             * donde se encuentran los eventos en formato JSON. El recurso
             * puede ser un archivo o un API Rest. La función se ejecuta
             * automáticamente cuando el controlador es accedido desde el
             * navegador.
             * @param {String} URL Dirección donde se encuentra el recurso
             * de los eventos o API donde se puede consultar. Se utiliza el
             * contexto definido anteriormente.
             */
            $http.get(clientesContext + '/' + $state.params.clienteId + '/' + eventosContext + '/' + $state.params.eventoId + '/' + pagoContext).then(function (response) {
                $scope.pagosRecords = response.data;
            });
        }
    ]);
}
)(window.angular);