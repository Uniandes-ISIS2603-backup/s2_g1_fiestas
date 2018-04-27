(function (ng) {
    var mod = ng.module("pagoModule");
    mod.constant("pagoContext", "pagos");
    mod.constant("eventosContext", "api/eventos");
    mod.controller('pagoCtrl', ['$scope', '$http','eventosContext','$state','pagoContext',
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
         * @param {Object} pagosContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Pagos en el Backend.
         */
        function ($scope, $http,eventosContext,$state, pagoContext) {
            $http.get(eventosContext+'/'+$state.params.eventoId+'/'+pagoContext).then(function (response) {
                $scope.pagosRecords = response.data;
            });
        }
    ]);
}
)(window.angular);