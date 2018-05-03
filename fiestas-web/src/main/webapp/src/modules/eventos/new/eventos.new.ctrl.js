(function (ng) {
    var mod = ng.module("eventoModule");
    mod.constant("eventoContext", "eventos");
    mod.constant("clientesContext", "clientes");
    mod.controller('eventoNewCtrl', ['$scope', '$http', 'eventoContext', '$state', '$rootScope','clientesContext',
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
         * @param {Object} authorsContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Eventos en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} $rootScope Referencia injectada al Scope definida para
         * toda la aplicación.
         */
        function ($scope, $http, eventoContext, $state, $rootScope,clientesContext) {
            $rootScope.edit = false;
            
            $scope.data = {};
            
             /**
             * @ngdoc function
             * @name createEvento
             * @methodOf eventos.controller:eventoNewCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para crear el evento.
             * @param {Object} autor Objeto con el nuevo autor.
             */
            $scope.createEvento = function () {
                $http.post(clientesContext + '/' + $state.params.clienteId + '/' +eventoContext, $scope.data).then(function (response) {
                    $state.go('eventosList', {eventoId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);